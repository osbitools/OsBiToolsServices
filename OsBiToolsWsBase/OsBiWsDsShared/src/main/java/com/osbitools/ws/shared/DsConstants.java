/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 * Date: 2014-10-02
 * 
 */

package com.osbitools.ws.shared;

import java.io.File;

/**
 * Default and Project specific constants
 * 
 */

public class DsConstants {
  // DataSet subdirectory
  public static final String DS_DIR = "ds";

  // Extension of DataSet Map Files
  public static final String BASE_EXT = "xml";

  // Supported DataSet versions
  public static final int[] DS_VER = new int[] { 1, 0 };

  // List of directories for DataSet Web Services
  public static String[] DS_DIR_LIST = new String[] { "ds" };

  // Package name with auto-generated code for DataSet Map
  public static final String BIND_PKG_DS_MAP =
      "com.osbitools.ws.shared.binding.ds";

  // List of custom dataset types
  public static final String[] DS_MAP_TYPES =
      new String[] { "group", "sql", "static", "csv", "xml" };
 
  // Schema File Name
  public static final String DS_SCHEMA_FNAME = DS_DIR + ".xsd";
  
  // Schema location
  public static final String DS_SCHEMA_PATH =
      "src" + File.separator + "main" + File.separator + "resources" +
          File.separator + "xsd" + File.separator + DS_SCHEMA_FNAME;

  // Schema External URL
  public static final String DS_SCHEMA_URL =
      "http://www.osbitools.com/xsd/" + DS_SCHEMA_FNAME;
}
