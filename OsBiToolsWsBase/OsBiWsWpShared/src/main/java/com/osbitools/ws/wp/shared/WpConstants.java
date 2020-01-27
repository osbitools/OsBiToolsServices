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
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.wp.shared;

import java.io.File;

/**
 * Default and Project specific constants
 * 
 */

public class WpConstants {
  // DataSet subdirectory
  public static final String WP_DIR = "sites";

  // Extension of Web Page Files
  public static final String BASE_EXT = "xml";

  // Supported DataSet versions
  public static final int[] WP_VER = new int[] { 1, 0 };

  // Package name with auto-generated code for Web Page
  public static final String BIND_PKG_WEB_PAGE = 
                "com.osbitools.ws.wp.shared.binding";

  // Type of Web Widgets
  public static final String[] WWG_TYPES = new String[] { "container", "chart", "control" };
  
  // Schema File Name
  public static final String WP_SCHEMA_FNAME = "wp.xsd";

  // Schema location
  public static final String WP_SCHEMA_PATH = "src" + File.separator + "main" +
      File.separator + "resources" + File.separator + "xsd" + File.separator +
          WP_SCHEMA_FNAME;

  // Schema External URL
  public static final String WP_SCHEMA_URL = 
      "http://www.osbitools.com/xsd/" + WP_SCHEMA_FNAME;
}
