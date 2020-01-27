/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-01-01
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.core.combo.shared.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.osbitools.ws.core.shared.model.DataSet;
import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.core.shared.service.CoreDsProducerSevice;
import com.osbitools.ws.rest.shared.base.common.RestBaseConstants;
import com.osbitools.ws.rest.shared.base.controller.BaseController;
import com.osbitools.ws.base.WsSrvException;

/**
 * API for DataSet Producer
 * 
 */
@RestController
@RequestMapping(CommonConstants.BASE_URL)
public class CoreDsProducerController extends BaseController {

  @Autowired
  private CoreDsProducerSevice _dsps;
  
  @RequestMapping(value = "/ds"+ RestBaseConstants.PATH_NAME_PARAM, method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public DataSet getDataSet(@PathVariable(RestBaseConstants.REQ_ENTITY_PARAM_NAME) String name,
      @RequestParam(value = "lang", required = false) String lang,
      @RequestParam(value = "trace", required = false, defaultValue = "off") String trace,
      HttpServletRequest req) throws WsSrvException {
    
    return _dsps.getDataSet(name, lang, trace, req.getParameterMap());
  }
}
