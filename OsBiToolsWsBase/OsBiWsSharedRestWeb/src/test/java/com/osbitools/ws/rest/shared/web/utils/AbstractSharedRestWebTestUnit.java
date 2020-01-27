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

package com.osbitools.ws.rest.shared.web.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.osbitools.ws.rest.shared.base.rest.SharedRestBaseTestUnit;
import com.osbitools.ws.shared.common.CommonConstants;

/**
 * Base class for web test unit
 * 
 */
public abstract class AbstractSharedRestWebTestUnit extends SharedRestBaseTestUnit {

  public static final String CONFIG_APP_NAME = "cfg";

  public static String CFG_PATH = "/" + CONFIG_APP_NAME;


  /**
   * Get data for public configuration test
   * 
   * @return 2D Array
   */
  protected abstract String[][] getConfigTest();

  @Test
  public void testConfigBadEx() throws Exception {
    // Test same but login

    assertEquals(HttpStatus.BAD_REQUEST.value(),
        getRestTemplate().getForEntity(CommonConstants.BASE_URL + CFG_PATH, String.class)
            .getStatusCodeValue());
  }

  @Test
  public void testConfigGood() throws Exception {
    for (String[] ctest : getConfigTest())
      checkWebResponse(CFG_PATH + "?lst=" + ctest[0], ctest[1]);
  }
}
