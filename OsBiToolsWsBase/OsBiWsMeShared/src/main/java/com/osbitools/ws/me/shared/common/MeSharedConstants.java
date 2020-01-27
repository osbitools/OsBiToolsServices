/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-10-02
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.me.shared.common;

import java.util.HashMap;

import com.osbitools.ws.shared.Utils;

/**
 * Some constants that belongs to Map Editor only
 * 
 */
public class MeSharedConstants {

  // Base file extension
  public static final String BASE_EXT = "xml";
  
  //List of extension per each resource folder
  public static final HashMap<String, String[]> EXT_LIST = new HashMap<String, String[]>();
  static {
    EXT_LIST.put("csv", new String[] { "csv" });
  }

  // List of implemented dataset containers
  public static String[] AVAIL_CONTAINERS = new String[] { "group" };

  // List of implemented dataset types
  public static String[] AVAIL_DS_TYPES = new String[] { "csv", "static", "sql" };

  // Static Component List
  public static final String COMP_LIST =
      "{\"ll_containers\":" + Utils.join(AVAIL_CONTAINERS) + "," +
          "\"ll_avail_ds_types\":" + Utils.join(AVAIL_DS_TYPES) + "}";
}
