/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2014-10-11
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.shared.base.it;


/**
 * Interface for Multi Thread Test Providers
 * 
 */
public interface ITestInfoProvider {
  
  /**
   * Get class with @Test defined that will be used for multi-threaded test.
   * Please note that Test class should pass single test 
   * in order to run multi-threaded one
   * 
   * @return Class with defined test.
   */
  abstract Class<?> getTestClass();
  
  /**
   * Get waiting time for all threads to complete.
   * 
   * @return Waiting time
   */
  abstract long getWaitTime();
  
  /**
   * Get number of threads that will be used for multi-threaded test
   * 
   * @return Number of threads
   */
  int getThreadNum();
}
