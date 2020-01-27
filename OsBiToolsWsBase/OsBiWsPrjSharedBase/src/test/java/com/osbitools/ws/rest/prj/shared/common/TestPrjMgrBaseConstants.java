/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-11-09
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.prj.shared.common;

import java.io.File;
import java.util.HashMap;

import com.osbitools.ws.shared.common.TestConstants;

/**
 * Constants used solely by unit and/or integration test
 * 
 */

public class TestPrjMgrBaseConstants {

  // Name of directory with projects
  public static final String PRJ_DIR = "projects";

  // Extension of project files
  public static final String PRJ_EXT = "txt";

  // List of extension per each resource folder
  public static final HashMap<String, String[]> EXT_LIST =
      new HashMap<String, String[]>();
  static {
    EXT_LIST.put("dat", new String[] { "num", "int", "txt" });
  }

  // - target/projects
  public static final String TEMP_PRJ_DIR_SHORT =
      TestConstants.TARGET_PATH + PRJ_DIR;

  // - target/projects/
  public static final String TEMP_PRJ_DIR = TEMP_PRJ_DIR_SHORT + File.separator;

  // web directory with test files: 
  // - /target/osbi_shared/projects
  public static final String WORK_PRJ_DIR =
      TestConstants.WORK_OSBI_SHARED_PATH + PRJ_DIR;

  //-- /target/osbi_shared/projects/
  public static final String WORK_PRJ_PATH = WORK_PRJ_DIR + File.separator;

  // - target/demo
  public static final String DEMO_PRJ_DIR_S = TestConstants.TARGET_PATH + "demo";

  //- target/demo/
  public static final String DEMO_PRJ_DIR = DEMO_PRJ_DIR_S + "/";

  //- /src/test/projects
  public static final String SRC_PRJ_DIR = TestConstants.SRC_TEST_DIR + PRJ_DIR;

  // Number of concurrent threads to test project management
  public static int THREAD_NUM = 10;

  // Name of remote repository directory. 
  // Must be the same as value for git_remote_name parameter in test.properties
  public static final String REMOTE_REPO_NAME = "test_remote";

  // Name of remote repository directory
  public static final String REMOTE_REPO_DIR_NAME = "remote";

  // Test project name
  public static final String TEST_PRJ_NAME = "test";

  // Path to remote repository
  // - /target/remote
  public static final String REMOTE_REPO_PATH =
      TestConstants.TARGET_PATH + REMOTE_REPO_DIR_NAME;
  
  // Entity prefix for JSON
  public static final String ENTYTY_PREFIX = "\"entity\":";
  
  // Pattern on last date modified. Seconds are optional
  public static final String ISO_8601_FMT =
     "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}(:\\d{2})?";
}
