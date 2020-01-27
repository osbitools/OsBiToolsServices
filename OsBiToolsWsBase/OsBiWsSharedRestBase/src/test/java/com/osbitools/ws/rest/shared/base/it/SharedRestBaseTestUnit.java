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

package com.osbitools.ws.rest.shared.base.it;

import java.util.HashMap;

import org.springframework.http.HttpStatus;

/**
 * Test Units
 * 
 */
public class SharedRestBaseTestUnit extends AbstractSharedRestBaseTestUnit {

  @Override
  protected String[] getWepAppPath() {
    return new String[] { "test", "test_mix", "version" };
  }
  
  @Override
  protected byte getPenTestPathParamMask(String url, String method) {
    return (byte) (url.endsWith("test_mix") ? 0b11 : (url.endsWith("test") ? 0b10 : 0b00));
  }

  @Override
  protected int getNoPenTestPathParamCode(String url, String method, boolean hasPathParam) {
    return url.endsWith("test_mix")
        ? (method.equals("GET")
            ? (hasPathParam ? HttpStatus.NOT_FOUND.value() : HttpStatus.OK.value())
            : (hasPathParam ? HttpStatus.NOT_FOUND.value() : HttpStatus.METHOD_NOT_ALLOWED.value()))
        : (url.endsWith("test")
            ? (hasPathParam ? HttpStatus.NOT_FOUND.value() : HttpStatus.NOT_FOUND.value())
            : 0);
  }
  
  @Override
  protected boolean hasPenTestQueryParam(String url, String method) {
    return false;
  }

  @Override
  protected HashMap<String, WebResponse> getExpectedAuthPenTestRes(String url) {
    return url.endsWith("version") || url.endsWith("test") ? AUTH_GET_TEST_RES : null;
  }

}
