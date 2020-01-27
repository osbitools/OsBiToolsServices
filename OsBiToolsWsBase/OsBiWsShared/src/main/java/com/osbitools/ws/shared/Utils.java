/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2016-26-05
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import com.osbitools.ws.base.BaseUtils;

/**
 * Shared Utilities
 * 
 */
public class Utils {
	
  public static String escJsonStr(String s) {
    return s.replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t")
          .replaceAll("\"", "\\\"").replaceAll("\\\\", "\\\\\\\\");
  }
  
  public static OffsetDateTime getOffsetTimeFromSeconds(int seconds) {
    return OffsetDateTime.ofInstant(
        Instant.ofEpochSecond(seconds), ZoneId.systemDefault());
  }
  
  /**
   * Join string list into comma delimited list.
   * 
   * @param list Input array
   * @return comma delimited list if array in JSON format is not null 
   * or empty and empty string otherwise
   * 
   */
  public static final String join(String[] list) {
    // Quick check
    if (BaseUtils.isEmpty(list))
      return "[]";
    
    String res = "\"" + list[0] + "\"";
    for (int i = 1; i < list.length; i++)
      res += ",\"" + list[i] + "\"";
    
    return "[" + res + "]";
  }
}
