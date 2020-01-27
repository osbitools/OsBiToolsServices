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

package com.osbitools.ws.rest.shared.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.osbitools.ws.rest.shared.base.controller.BaseController;
import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.shared.web.service.BaseWebService;

@RestController
@RequestMapping(CommonConstants.BASE_URL)
public class BaseConfigurationController extends BaseController {

  @Autowired
  @Qualifier("base_web_srv")
  private BaseWebService _bs;
  
  @RequestMapping(
      value = "/cfg",
      method = RequestMethod.GET, 
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Map<String, String> getConfig(@RequestParam("lst") String lst) {
    getLogger().debug("cfg request for '" + lst + "'");
    
    return _bs.getWebConfig(lst);
  }  
}