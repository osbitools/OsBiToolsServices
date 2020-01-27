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
 * Date: 2016-26-05
 * 
 */

package com.osbitools.ws.shared;

import java.util.regex.Pattern;

/**
 * Default and Project specific constants
 * 
 */

public class Constants {

  public static final String ID_REGEX = "[A-Za-z0-9_-]{1,256}";

  // Pattern for Project/Map names
  public static final Pattern ID_PATTERN = Pattern.compile("^" + ID_REGEX + "$");

  // Patter for query parameter names
  public static String PARAM_VAL_SUFIX = "osbi_";
  public static Pattern PARAM_VAL = Pattern.compile(PARAM_VAL_SUFIX + ".*");

  // Max supported project level
  public static final int MAX_PROJ_LVL = 1;

  //Extension of configuration files.
  public static final String CONFIG_FIILE_EXT = "properties";

  // Default configuration file name as confi.properties
  public static final String WS_CONFIG_FILE_NAME = "config." + CONFIG_FIILE_EXT;

}
