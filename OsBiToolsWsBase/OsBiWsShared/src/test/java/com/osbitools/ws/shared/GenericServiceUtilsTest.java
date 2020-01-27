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

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Unit tests for GenericServiceUtils
 * 
 */
public class GenericServiceUtilsTest {

  @Test
  public void testNullParams() {
    checkEmptyParams(GenericServiceUtils.getReqParamValues(null));
  }

  @Test
  public void testEmptyParams() {
    checkEmptyParams(GenericServiceUtils.getReqParamValues(new HashMap<>()));
  }

  @Test
  public void testNonMatchingParams() {
    Map<String, String[]> params = new HashMap<>();
    params.put("dummy", new String[] {});
    checkEmptyParams(GenericServiceUtils.getReqParamValues(params));
  }

  @Test
  public void testMatchingNullParam() {
    Map<String, String[]> params = new HashMap<>();
    params.put(Constants.PARAM_VAL_SUFIX, null);
    checkNonEmptyParams(GenericServiceUtils.getReqParamValues(params), 1, "", null);
  }

  @Test
  public void testMatchingEmptyParam() {
    Map<String, String[]> params = new HashMap<>();
    params.put(Constants.PARAM_VAL_SUFIX, new String[] {});
    checkNonEmptyParams(GenericServiceUtils.getReqParamValues(params), 1, "", null);
  }

  @Test
  public void testMatchingSingleValueParam() {
    String key = "hello";
    String value = "OK";

    Map<String, String[]> params = new HashMap<>();
    params.put(Constants.PARAM_VAL_SUFIX + key, new String[] { value });

    checkNonEmptyParams(GenericServiceUtils.getReqParamValues(params), 1, key, value);
  }

  @Test
  public void testMatchingMultiValueParam() {
    String key = "hello";
    String value = "OK";

    Map<String, String[]> params = new HashMap<>();
    params.put(Constants.PARAM_VAL_SUFIX + key, new String[] { "GOOD BYE", value });

    checkNonEmptyParams(GenericServiceUtils.getReqParamValues(params), 1, key, value);
  }

  @Test
  public void testMatchingMultiValueParams() {
    final int len = 5;
    final String key = "test";

    Map<String, String[]> params = new HashMap<>();
    params.put("some", new String[] {"value"});
    
    for (int i = 0; i < len; i++)
      params.put(Constants.PARAM_VAL_SUFIX + key + i,
          new String[] { key.toUpperCase(), key.toUpperCase() + "_" + i + i });

    Map<String, String> map = GenericServiceUtils.getReqParamValues(params);

    assertNotNull(map);
    assertEquals(len, map.size());

    for (int i = 0; i < len; i++)
      assertEquals(key.toUpperCase() + "_" + i + i,
          map.get(Constants.PARAM_VAL_SUFIX + key + i));
  }

  private void checkEmptyParams(Map<String, String> params) {
    assertNotNull(params);
    assertEquals(0, params.size());
  }

  private void checkNonEmptyParams(Map<String, String> params, int size, String key,
      String value) {
    assertNotNull(params);
    assertEquals(size, params.size());
    assertEquals(value, params.get(Constants.PARAM_VAL_SUFIX + key));
  }
}
