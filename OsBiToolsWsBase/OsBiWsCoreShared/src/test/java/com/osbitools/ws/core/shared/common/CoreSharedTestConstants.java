/*
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-01-01
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.core.shared.common;

import java.io.File;
import java.util.Date;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.base.dto.ColumnDto;
import com.osbitools.ws.shared.base.dto.ColumnListDto;
import com.osbitools.ws.shared.common.TestConstants;

/**
 * Constants used solely by unit and/or integration test
 * 
 */
public class CoreSharedTestConstants {

  // src/test/
  public static final String TEST_DIR =
      "src" + File.separator + "test" + File.separator;

  // src/test/config
  // public static final String TEST_CONFIG_DIR_SHORT = TEST_DIR + "config";

  // src/test/config/
  // public static final String TEST_CONFIG_DIR = TEST_CONFIG_DIR_SHORT + File.separator;

  public static final String TEST_PRJ_NAME = "test";

  public static final String BAD_PRJ_NAME = "bad";

  // target/osbi_shared/ds
  public static final String WORK_DS_DIR =
      TestConstants.WORK_OSBI_SHARED_PATH + DsConstants.DS_DIR;

  // target/osbi_shared/ds/
  public static final String WORK_DS_PATH = WORK_DS_DIR + File.separator;

  // target/osbi_shared/ds/test
  public static final String WORK_TEST_PROJ = WORK_DS_PATH + TEST_PRJ_NAME;

  // target/ds/bad
  public static final String WORK_BAD_PROJ = WORK_DS_PATH + BAD_PRJ_NAME;

  // target/csv
  public static final String WORK_CSV_DIR =
      TestConstants.TARGET_PATH + File.separator + "csv";

  // test.
  public static final String TEST_PRJ_PREFIX = TEST_PRJ_NAME + ".";

  // bad.
  public static final String BAD_PRJ_PREFIX = BAD_PRJ_NAME + ".";

  public static final String DS_EMPTY_XML = "ds_empty.xml";

  // target/ds/test/ll_set
  public static final String LS_SET_TEST_FILE_PATH =
      WORK_TEST_PROJ + File.separator + LsConstants.LANG_SET_FILE;

  // test.ls_set
  public static final String TEST_LS_FILE_KEY =
      CoreSharedTestConstants.TEST_PRJ_PREFIX + LsConstants.LANG_SET_FILE;

  // Has to be same value as in test.properties file
  public static final int DAEMON_SCAN_TIME = 500; // msec

  public static final int RESULT_CHECK_TIME = 10; // msec

  // Wait time to file change
  public static final int CHANGE_DETECT_TIME = DAEMON_SCAN_TIME * 2;

  // Wait time to load/reload file
  public static final int WAIT_TIME = DAEMON_SCAN_TIME * 5;

  public static final String TEST_USER = "user";

  public static final String TEST_PASSWORD = "test";

  // Number of lang labels for test
  public static final int LL_NUM = 3;

  // Sql for SqlInfo test
  public static String SQL_COL_LIST =
      "SELECT CSTR,CINT,CNUM,CDATE FROM TEST_DATA;";

  // Column Dto array for SqlInfo test
  public static ColumnListDto SQL_INFO_COL_LIST = new ColumnListDto(4);

  static {
    SQL_INFO_COL_LIST.addColumn(new ColumnDto("CSTR", String.class.getName()));
    SQL_INFO_COL_LIST.addColumn(new ColumnDto("CINT", Integer.class.getName()));
    SQL_INFO_COL_LIST.addColumn(new ColumnDto("CNUM", Double.class.getName()));
    SQL_INFO_COL_LIST.addColumn(new ColumnDto("CDATE", Date.class.getName()));
  }
}
