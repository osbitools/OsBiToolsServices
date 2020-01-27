/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2014-10-02
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.wp.shared;

import java.io.File;

import com.osbitools.ws.shared.common.TestConstants;
import com.osbitools.ws.wp.shared.WpConstants;

/**
 * Web Page Export constants
 * 
 */
public class WpTestConstants {

  //Path for demo web pages
  public static final String WEB_PAGES_PACKAGE = "com/osbitools/ws/shared/demo/sites";

  // Working test directory with Web Page files
  // Used during integration test
  // - target/osbi_shared/sites
  public static final String WORK_WP_DIR =
      TestConstants.WORK_OSBI_SHARED_PATH + WpConstants.WP_DIR;

  // Used during integration test
  //- target/osbi_shared/sites
  public static final String WORK_WP_DIR_PATH = WORK_WP_DIR + File.separator;

  // Test Relative path to demo maps
  //- src/test/resources/com/osbitools/ws/shared/demo/ds/
  public static final String WEB_PAGES_PATH = TestConstants.SRC_TEST_DIR + File.separator +
      "resources" + File.separator + WEB_PAGES_PACKAGE + File.separator;

  // Name of main web page demo file
  // - wp.xml
  public static final String WP_DEMO_FNAME = "wp." + WpConstants.BASE_EXT;
}
