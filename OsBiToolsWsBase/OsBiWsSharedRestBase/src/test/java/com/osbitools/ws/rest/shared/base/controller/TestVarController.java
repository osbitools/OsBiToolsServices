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

package com.osbitools.ws.rest.shared.base.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.rest.shared.base.common.RestBaseConstants;
import com.osbitools.ws.rest.shared.config.common.TestRestConstants;

@RestController
@RequestMapping(CommonConstants.BASE_URL)
public class TestVarController {

  @RequestMapping(value = "/test_mix", method = RequestMethod.GET,
      produces = MediaType.TEXT_PLAIN_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public String testMixAll() {
    return "ALL_OK";
  }

  @RequestMapping(
      value = "/test_mix" + RestBaseConstants.PATH_NAME_PARAM, method = { RequestMethod.GET,
          RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE },
      produces = MediaType.TEXT_PLAIN_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public String
      testMixOne(@PathVariable(RestBaseConstants.REQ_ENTITY_PARAM_NAME) String name) {
    return !name.equals(TestRestConstants.TEST_ENTITY_NAME)
        ? "Invalid name parameter: " + name
        : "ok";
  }

  @RequestMapping(
      value = "/test" + RestBaseConstants.PATH_NAME_PARAM, method = { RequestMethod.GET,
          RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE },
      produces = MediaType.TEXT_PLAIN_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public String testOne(@PathVariable(RestBaseConstants.REQ_ENTITY_PARAM_NAME) String name) {
    return !name.equals(TestRestConstants.TEST_ENTITY_NAME)
        ? "Invalid name parameter: " + name
        : "ok";
  }
}
