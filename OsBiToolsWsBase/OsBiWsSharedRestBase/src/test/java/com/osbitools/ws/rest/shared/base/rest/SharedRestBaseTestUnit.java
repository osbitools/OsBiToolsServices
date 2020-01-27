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

package com.osbitools.ws.rest.shared.base.rest;

import java.util.HashMap;

import org.springframework.http.HttpStatus;

import com.osbitools.ws.rest.shared.base.utils.GenericRestTestUnit;

/**
 * Test Units
 * 
 */
public abstract class SharedRestBaseTestUnit extends GenericRestTestUnit {

  @Override
  protected String[] getWepAppPath() {
    return new String[] { "test_mix", "test", "version" };
  }

  @Override
  protected byte getPenTestPathParamMask(String url, String method) {
    return (byte) (url.endsWith("test_mix") ? 0b11 : (url.endsWith("test") ? 0b10 : 0b00));
  }

  @Override
  protected int getNoPenTestPathParamCode(String url, String method, boolean hasPathParam) {
    return url.endsWith("test_mix")
        ? getMixNoPenTestPathParamCode(method, hasPathParam)
        : (url.endsWith("test")
            ? (hasPathParam ? HttpStatus.NOT_FOUND.value() : HttpStatus.NOT_FOUND.value())
            : 0);
  }

  @Override
  protected boolean hasPenTestQueryParam(String url, String method) {
    return false;
  }

  @Override
  protected HashMap<String, Integer> getExpectedAuthPenTestRes(String url) {
    return url.endsWith("version") || url.endsWith("test_mix") || url.endsWith("test")
        ? ONLY_GET_ALLOW_TEST_RES
        : null;
  }

}
