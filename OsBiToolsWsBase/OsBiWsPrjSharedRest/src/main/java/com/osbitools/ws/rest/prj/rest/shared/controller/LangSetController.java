/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2015-08-08
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.rest.shared.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.osbitools.ws.rest.prj.shared.service.LangSetService;
import com.osbitools.ws.rest.shared.base.common.RestBaseConstants;
import com.osbitools.ws.rest.shared.base.controller.BaseController;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.binding.ll_set.LangLabelsSet;
import com.osbitools.ws.shared.common.CommonConstants;

/**
 * 
 * LangLabels File Manager. Implements next CRUD spec
 * 
 * doGet - Read Project ll_set
 * doPut - Save and automatically commit new file
 * 
 */

@RestController
@RequestMapping(value = CommonConstants.BASE_URL + "/ll_set" +
    RestBaseConstants.PATH_SHORT_NAME_PARAM)
public class LangSetController extends BaseController {

  @Autowired
  private LangSetService _lsrv;

  @RequestMapping(method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public LangLabelsSet readLangSet(@PathVariable("name") String name)
      throws WsSrvException {
    return _lsrv.read(name);
  }

  @RequestMapping(method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public void saveLangSet(@PathVariable("name") String name,
      @RequestBody LangLabelsSet lls,
      @RequestParam(name = "comment", required = true) String comment) throws WsSrvException {

    _lsrv.save(name, lls, comment);
  }
}
