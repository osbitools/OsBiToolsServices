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
 * Date: 2017-04-19
 * 
 */

package com.osbitools.ws.rest.shared.web.rest;

import java.util.HashMap;

import com.osbitools.ws.rest.shared.base.utils.GenericRestTestUnit;
import com.osbitools.ws.rest.shared.web.utils.AbstractSharedRestWebTestUnit;

/**
 * Test Units
 * 
 */
public abstract class SharedRestWebTestUnit extends AbstractSharedRestWebTestUnit {

  @Override
  protected String[] getWepAppPath() {
    String [] list = super.getWepAppPath();
    
    // Copy super list and add extra
    String[] res = new String[list.length + 1];
    for (int i = 0; i < list.length; i++)
      res[i] = list[i];
    
    res[list.length] = "cfg";
    return res;
  }

  @Override
  protected boolean hasPenTestQueryParam(String url, String method) {
    return super.hasPenTestQueryParam(url, method) || 
                    method.equals("get") && url.endsWith("cfg");
  }

  @Override
  protected HashMap<String, Integer> getExpectedAuthPenTestRes(String url) {
    HashMap<String, Integer> map = super.getExpectedAuthPenTestRes(url);
    return map != null ? map : url.endsWith("cfg")  ? GenericRestTestUnit.ONLY_GET_ALLOW_TEST_RES : null;
  }

}
