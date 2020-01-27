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

package com.osbitools.ws.rest.shared.base.it;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

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
      new Thread(new MultiSharedRestBaseRunner(this)).start();

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

}
