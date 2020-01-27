/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-11-16
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.common;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Shared constants for Shared Base Project Manager
 * 
 */

public class PrjMgrBaseConstants {

  // XML Extension that shared among all WebUI supporting services
  public static final String WEBUI_BASE_EXT = "xml";
  
  // Default remote git name
  public static final String DEFAULT_REMOTE_NAME = "origin";

  // Default Max size of upload file in Mb
  public static final String DEFAULT_MAX_FILE_UPLOAD_SIZE = "10";

  public static final HashMap<String, Pattern> EXT_MASK = new HashMap<String, Pattern>();

  public static final Pattern EXT_FILE_LIST_MASK = Pattern.compile("^.*\\.\\*$");
}
