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

package com.osbitools.ws.shared;

import java.io.File;

import com.osbitools.ws.shared.DsConstants;
import com.osbitools.ws.shared.common.TestConstants;

/**
 * DataSet Maps Test Contstants
 * 
 */

public class TestDsConstants {

  // Relative Path for demo maps
  public static final String DS_MAPS_PACKAGE =
      "com/osbitools/ws/shared/demo/ds";

  // Test Relative path to demo maps
  //- src/test/resources/com/osbitools/ws/shared/demo/ds/
  public static final String DS_MAPS_PATH =
      TestConstants.SRC_TEST_DIR + File.separator + "resources" +
          File.separator + DS_MAPS_PACKAGE + File.separator;

  // Working test directory with DataSet maps
  // Used during integration test
  // - target/osbi_shared/ds
  public static final String WORK_DS_DIR =
      TestConstants.WORK_OSBI_SHARED_PATH + DsConstants.DS_DIR;

  // Used during integration test
  //- target/osbi_shared/ds
  public static final String WORK_DS_DIR_PATH = WORK_DS_DIR + File.separator;

  // Name of main ds demo file
  // - ds.xml
  public static final String DS_DEMO_FNAME = "ds." + DsConstants.BASE_EXT;
}
