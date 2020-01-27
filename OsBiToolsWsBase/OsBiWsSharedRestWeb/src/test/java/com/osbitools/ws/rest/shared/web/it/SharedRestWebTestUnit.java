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

package com.osbitools.ws.rest.shared.web.it;

import java.util.HashMap;

import com.osbitools.ws.rest.shared.base.it.WebResponse;

/**
 * Test Units
 * 
 */
public class SharedRestWebTestUnit extends AbstractSharedRestWebTestUnit {

  private static final String[][] GONFIG_TEST =
      new String[][] { new String[] { "param1", "param1,param2", "param1,param2,paramxx" },
          new String[] { "{\"param1\":\"one\"}", "{\"param1\":\"one\",\"param2\":\"two\"}",
              "{\"param1\":\"one\",\"param2\":\"two\"}", } };
              
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
                        method.equals("GET") && url.endsWith("cfg");
  }

  @Override
  protected HashMap<String, WebResponse> getExpectedAuthPenTestRes(String url) {
    HashMap<String, WebResponse> map = super.getExpectedAuthPenTestRes(url);
    return map != null ? map : url.endsWith("cfg") ? 
        AUTH_GET_TEST_RES : null;
  }

  @Override
  protected String[][] getConfigTest() {
    return GONFIG_TEST;
  }
  
}
