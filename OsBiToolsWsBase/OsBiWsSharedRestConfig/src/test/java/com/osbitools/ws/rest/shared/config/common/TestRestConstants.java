/*
 * Open Source Business Intelligence Tools - http://www.osgitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2016-26-05
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.shared.config.common;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.shared.common.TestConstants;

/**
 * Constants used solely by unit and/or integration test
 * 
 */
public class TestRestConstants {

  // target/rest
  public static final String WORK_WEBAPP_PATH = TestConstants.TARGET_PATH +
      "rest";

  // target/rest/
  public static final String WORK_WEBAPP_DIR = WORK_WEBAPP_PATH +
      File.separator;

  // target/rest/version
  public static final String WEBAPP_VERSION_PATH = WORK_WEBAPP_DIR + "version";

  /* ############################ WEB PARAMETERS ############################ */

  // Default Port
  public static final int HTTP_PORT = 8090;

  // Default Web Context
  public static final String HTTP_WEB_CTX = "web_test";

  //Default Host
  public static final String HTTP_HOST = "localhost";

  // Base Server URL for Web Service Test
  public static final String HTTP_SRV_BASE = "http://" + HTTP_HOST + ":" +
      HTTP_PORT + "/";

  // Default Path for Web Service Test
  public static final String HTTP_PATH = HTTP_WEB_CTX +
      CommonConstants.BASE_URL + "/";
 
  // TODO Delete obsoleted. Default Server URL for Web Service Test
  // public static final String SRV_BASE_URL_ = HTTP_SRV_BASE + HTTP_PATH;
  
  // TODO Temp solution. Replace with dynamic path
  public static final String HTTP_SRV_URL = HTTP_SRV_BASE + HTTP_WEB_CTX;
 
  // Sync lock
  public static Object lock = new Object();

  //Synchronization counters
  public static final CountDownLatch start = new CountDownLatch(1);

  // Test entity name
  public static final Object TEST_ENTITY_NAME = "qwe.rty";

  public static CountDownLatch done;

  //Number of errors
  public static int errCount = 0;

}
