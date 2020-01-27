/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-01-01
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.shared.web.it;

import static org.junit.Assert.assertEquals;

import com.osbitools.ws.rest.shared.base.it.SharedRestBaseTestUnit;
import com.osbitools.ws.rest.shared.base.it.WebResponse;

/**
 * Base class for web test unit
 * 
 */

public abstract class AbstractSharedRestWebTestUnit extends SharedRestBaseTestUnit {

  public static final String CONFIG_APP_NAME = "cfg";

  // TODO Delete obsoleted
  // public static String CFG_PATH = TestRestBaseConstants.SRV_BASE_URL + CONFIG_APP_NAME;

  abstract String[][] getConfigTest();
  
  public String getCfgPath() {
    return getSrvUrl() + CONFIG_APP_NAME;
  }
  
  public void doTestConfigBadEx() throws Exception {
    assertEquals("Empty Auth Cfg", HTTP_RESP_BAD_REQUEST.getCode(),
        readGet(getCfgPath()).getCode());
  }

  public void doTestConfigGood() throws Exception {
    String[][] ctest = getConfigTest();
    int len = ctest[0].length;
    for (int i = 0; i < len; i++) {
      String params = ctest[0][i];
      String url = getCfgPath() + "?lst=" + params;

      testWebResponse(ctest[0][i] + " parameters", new WebResponse(ctest[1][i]), readGet(url));
    }
  }
  
}
