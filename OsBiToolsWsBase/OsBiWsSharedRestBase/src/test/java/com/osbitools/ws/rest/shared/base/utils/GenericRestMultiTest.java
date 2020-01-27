/*
 * Copyright 2014-2018 IvaLab Inc. and contributors below
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2014-10-02
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.shared.base.utils;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.osbitools.ws.rest.shared.base.common.RestBaseConstants;
import com.osbitools.ws.rest.shared.config.common.TestRestConstants;
import com.osbitools.ws.shared.common.TestConstants;

/**
 * Abstract class for MultiThread Web Test
 * 
 */
public abstract class GenericRestMultiTest implements ITestInfoProvider {

  @Test
  public void testConcurrentLoad() throws InterruptedException {
    int tnum = getThreadNum();
    TestRestConstants.done = new CountDownLatch(tnum);
    System.out.println("Concurrent " + getTestClass().getSimpleName() +
        " test for " + tnum + " Threads");

    long wtime = getWaitTime();

    // Create threads
    for (int i = 0; i < tnum; i++)
      new Thread(new GenericRestMultiTestRunner(this)).start();

    Thread.sleep(1000);
    TestRestConstants.start.countDown();

    if (!TestRestConstants.done.await(wtime, TimeUnit.MILLISECONDS))
      assertEquals(
          TestRestConstants.done.getCount() + " thread(s) hasn't been " +
              "completed during " + wtime + " " + TimeUnit.MILLISECONDS,
          0, TestRestConstants.done.getCount());

    assertEquals(
        "Total " + TestRestConstants.errCount +
            " thread(s) completed with errors",
        0, TestRestConstants.errCount);
  }

  @Override
  public int getThreadNum() {
    return TestConstants.THREAD_NUM;
  }

  @Override
  public long getWaitTime() {
    return TestConstants.WAIT_TIME * 10;
  }

  @Autowired
  public void setTestRestTemplate(TestRestTemplate restTemplate)
      throws NoSuchFieldException, SecurityException, IllegalArgumentException,
      IllegalAccessException {
    setStaticValue(RestBaseConstants.REST_TEMPLATE_FIELD_NAME, restTemplate);
  }

  @Autowired
  public void setLogger(Logger log) throws NoSuchFieldException,
      SecurityException, IllegalArgumentException, IllegalAccessException {
    setStaticValue(RestBaseConstants.LOG_FIELD_NAME, log);
  }

  @LocalServerPort
  public void setLogger(int port) throws NoSuchFieldException,
      SecurityException, IllegalArgumentException, IllegalAccessException {
    setStaticValue(RestBaseConstants.LOCAL_PORT_FIELD_NAME, port);
  }

  protected void setStaticValue(String name, Object value)
      throws NoSuchFieldException, SecurityException, IllegalArgumentException,
      IllegalAccessException {
    Class<?> clazz = getTestClass();

    // Find static parameter inside the class
    Field field = clazz.getDeclaredField(name);
    // Assign it
    field.setAccessible(true);
    field.set(clazz, value);
  }
}
