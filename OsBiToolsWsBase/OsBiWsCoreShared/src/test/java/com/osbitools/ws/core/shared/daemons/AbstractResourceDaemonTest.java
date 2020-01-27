/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2015-08-10
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.core.shared.daemons;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.osbitools.ws.shared.common.ITestResourceConfig;
import com.osbitools.ws.shared.common.JarTestResourceUtils;
import com.osbitools.ws.shared.common.TestConstants;
import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.core.shared.bindings.BindingTest;
import com.osbitools.ws.core.shared.common.CoreSharedTestConstants;
import com.osbitools.ws.core.shared.daemons.AbstractResourceCheck;
import com.osbitools.ws.core.shared.daemons.ResourceInfo;

/**
 * Abstract daemon for resource file check
 * 
 */

public abstract class AbstractResourceDaemonTest<T1, T2 extends ResourceInfo<T1>>
    implements Runnable {

  static CountDownLatch ss1;
  static CountDownLatch ss2;
  static CountDownLatch ss3;
  static CountDownLatch ds1;
  static CountDownLatch ds2;
  static CountDownLatch ds3;

  // Base directory
  static final String BASE_DIR = CoreSharedTestConstants.WORK_DS_DIR;

  // Test directory
  static File td;

  // Number of errors
  static int ecnt = 0;

  // Daemon check
  private AbstractResourceCheck<T1, T2> _check;

  // Demo key used in reload test
  private final String _dkey;

  // Test class
  private final Class<T1> _tclass;

  // Destination file used by reload tests
  private final String _dst;

  // Resource configuration
  private final ITestResourceConfig _cfg;

  // Source files used by reload test
  private final String[] _src;

  public AbstractResourceDaemonTest(String key, Class<T1> tclass, String[] src, String dst,
      ITestResourceConfig cfg) {

    _dkey = key;
    _tclass = tclass;

    _src = src;
    _dst = dst;
    _cfg = cfg;
  }

  public static void init() throws Exception {
    // Check if target/ds dir exists
    File d = new File(BASE_DIR);
    if (!d.exists() && !d.mkdirs())
      fail("Unable create " + BASE_DIR + " directory.");

    // Clean directory
    BaseUtils.delDirRecurse(d, false);

    // Create subdirectory test
    createSubDir(CoreSharedTestConstants.WORK_TEST_PROJ);
  }

  static void createSubDir(String path) {
    td = new File(path);
    if (!td.exists() && !td.mkdir())
      fail("Unable create " + path + " directory.");
  }

  T2 checkResourceStatus(AbstractResourceCheck<T1, T2> check, String key, boolean floaded)
      throws InterruptedException {
    T2 res = null;
    long dts = System.currentTimeMillis();
    while (true && (System.currentTimeMillis() - dts) < TestConstants.WAIT_TIME) {

      res = check.getResource(key);

      if (res != null && floaded || res == null && !floaded)
        break;

      Thread.sleep(TestConstants.RESULT_CHECK_TIME);
    }

    if (floaded)
      assertNotNull("'" + key + "' is not loaded " + "after " + TestConstants.WAIT_TIME +
          " msec of waiting.", res);
    else
      assertNull("'" + key + "' is not removed " + "after " + TestConstants.WAIT_TIME +
          " msec of waiting.", res);

    return res;
  }

  protected synchronized T2 checkPartialTestResourceLoaded() throws Exception {
    return checkTestResourceLoaded("partial");
  }

  protected synchronized T2 checkFullTestResourceLoaded() throws Exception {
    return checkTestResourceLoaded("full");
  }

  protected T2 checkTestResourceLoaded(String stype) throws Exception {
    String sname = _tclass.getSimpleName();
    T2 res = checkResourceStatus(_check, _dkey, true);
    Method m =
        BindingTest.class.getMethod("check" + sname + BaseUtils.ucFirstChar(stype), _tclass);
    m.invoke(null, res.getResource());

    return res;
  }

  public AbstractResourceCheck<T1, T2> getCheckRef() {
    return _check;
  }

  protected void testSingleResourceReload() throws Exception {

    // Test bad file

    // Check partial file loaded
    JarTestResourceUtils.copyDemoFileToFile(_src[0], _dst, _cfg);
    checkPartialTestResourceLoaded();

    // Check full file loaded
    // Wait 1 sec to detect file time change
    Thread.sleep(1000);
    JarTestResourceUtils.copyDemoFileToFile(_src[1], _dst, _cfg);
    // Wait 1 sec for file to reload
    Thread.sleep(1000);
    // Test for resource reloaded
    checkFullTestResourceLoaded();

    // Remove file
    removeDstFile();

    // Check full ds file removed
    checkResourceStatus(_check, _dkey, false);
  }

  private void removeDstFile() {
    File f = new File(_dst);
    if (!f.delete())
      fail("Unable delete " + _dst + " file.");
  }

  public void initCheckRef(AbstractResourceCheck<T1, T2> check) {
    _check = check;
    check.clear();
  }

  protected void multiTestResourceReload(String cname) throws Exception {

    // Instantiate test class
    @SuppressWarnings("unchecked")
    Class<AbstractResourceDaemonTest<T1, T2>> tclass =
        (Class<AbstractResourceDaemonTest<T1, T2>>) Class.forName(cname);
    AbstractResourceDaemonTest<T1, T2> test = tclass.getConstructor().newInstance();
    test.initCheckRef(_check);

    // Activate countdowns
    ss1 = new CountDownLatch(1);
    ss2 = new CountDownLatch(1);
    ss3 = new CountDownLatch(1);
    ds1 = new CountDownLatch(TestConstants.THREAD_NUM);
    ds2 = new CountDownLatch(TestConstants.THREAD_NUM);
    ds3 = new CountDownLatch(TestConstants.THREAD_NUM);

    // Create test threads
    for (int i = 0; i < TestConstants.THREAD_NUM; i++)
      new Thread(test).start();

    // Copy partial file
    JarTestResourceUtils.copyDemoFileToFile(_src[0], _dst, _cfg);

    // Test partial file reloaded
    testThreadsCompleted(ss1, ds1, "partial file load");

    Thread.sleep(1000);
    JarTestResourceUtils.copyDemoFileToFile(_src[1], _dst, _cfg);
    Thread.sleep(1000);

    // Test full ds file reloaded
    testThreadsCompleted(ss2, ds2, "full file reloaded");

    // Remove ds file
    removeDstFile();

    // Test ds file removed
    testThreadsCompleted(ss3, ds3, "full file removed");
  }

  private void testThreadsCompleted(CountDownLatch start, CountDownLatch done, String msg)
      throws InterruptedException {
    start.countDown();
    if (!done.await(TestConstants.WAIT_TIME, TimeUnit.MILLISECONDS))
      assertEquals(done.getCount() + " thread(s) hasn't been " + "completed with " + msg, 0,
          done.getCount());
    assertEquals(ecnt + " thread(s) completed with errors", 0, ecnt);
  }

  @Override
  public void run() {
    try {
      ss1.await();
    } catch (InterruptedException e) {
      fail(e.getMessage());
    }

    // Check loaded
    try {
      checkPartialTestResourceLoaded();
    } catch (Exception e) {
      fail(e.getMessage());
    }

    ds1.countDown();

    try {
      ss2.await();
    } catch (InterruptedException e) {
      fail(e.getMessage());
    }

    // Check reloaded
    try {
      checkFullTestResourceLoaded();
    } catch (Exception e) {
      fail(e.getMessage());
    }

    ds2.countDown();

    try {
      ss3.await();
    } catch (InterruptedException e) {
      fail(e.getMessage());
    }

    // Check removed
    try {
      checkResourceStatus(_check, _dkey, false);
    } catch (InterruptedException e1) {
      fail(e1.getMessage());
    }

    ds3.countDown();
  }
}
