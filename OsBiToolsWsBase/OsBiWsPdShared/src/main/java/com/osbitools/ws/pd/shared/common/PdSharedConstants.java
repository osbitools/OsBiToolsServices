/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-08-04
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.pd.shared.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Some constants that belongs to Page Designer only
 * 
 */
public class PdSharedConstants {

  // Base file extension
  public static final String BASE_EXT = "xml";
  
  //List of extension per each resource folder
  public static final Map<String, String[]> EXT_LIST = new HashMap<String, String[]>();
  static {
    EXT_LIST.put("icons", new String[] { "png", "gif", "jpeg" });
  }
}
