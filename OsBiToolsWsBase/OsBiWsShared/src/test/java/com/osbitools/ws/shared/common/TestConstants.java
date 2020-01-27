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

package com.osbitools.ws.shared.common;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.osbitools.ws.shared.Constants;

/**
 * Constants used solely by unit and/or integration test
 * 
 */
public class TestConstants {

  // - target
  public static final String TARGET_DIR = "target";

  // - target/
  public static final String TARGET_PATH = TARGET_DIR + File.separator;

  // - src/
  public static final String SRC_DIR = "src" + File.separator;

  // - src/test/
  public static final String SRC_TEST_DIR = SRC_DIR + "test" + File.separator;

  // - config
  public static final String CONFIG_DNAME = "config";

  // - config/
  public static final String CONFIG_DIR = CONFIG_DNAME + File.separator;

  public static final String OSBI_SHARED = "osbi_shared";

  // - target/osbi_shared
  public static final String WORK_OSBI_SHARED_DIR = TARGET_PATH + OSBI_SHARED;

  // - target/osbi_shared/
  public static final String WORK_OSBI_SHARED_PATH =
      WORK_OSBI_SHARED_DIR + File.separator;

  //- target/osbi_shared/config.properties
  public static final String WORK_WS_CONFIG_FILE =
      TestConstants.WORK_OSBI_SHARED_PATH + Constants.WS_CONFIG_FILE_NAME;

  // - /target/logs
  public static final String LOG_DIR = TARGET_PATH + "logs";

  public static final Logger LOG = LoggerFactory.getLogger("Test");

  // Multi-thread parameters
  
  public static final int RESULT_CHECK_TIME = 10; // msec

  // Default wait time
  public static final int WAIT_TIME = 5000;

  // Doubled wait time
  public static final int WAIT_TIME2 = WAIT_TIME * 2;
 
  public static final int THREAD_NUM = 100;
  
}
