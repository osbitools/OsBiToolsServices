/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-06-16
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared;

import java.util.HashMap;
import java.util.Map;

/**
 * Common Service Utilities
 * 
 */

public class GenericServiceUtils {

  /**
   * Read from input map all parameters started with "${PARAM_VAL_SUFIX}_" 
   *      and return corresponded values
   *      
   * @param params Map with raw parameters from Http Request
   * 
   * @return Map with striped parameter names and values
   *  In case if multiple values assigned to the same parameter name
   *  the last one is returned
   */
  public static Map<String, String> getReqParamValues(
                                    Map<String, String[]> params) {
    Map<String, String> res = new HashMap<String, String>();

    // Quick check
    if (params == null)
      return res;
    
    for (String pname: params.keySet()) {
      String[] values = params.get(pname);
      
      if (Constants.PARAM_VAL.matcher(pname).matches())
        // Take last element from values array
        res.put(pname, values != null && values.length > 0
            ? values[values.length - 1] 
            : null);
    }
    
    return res;
  }
}
