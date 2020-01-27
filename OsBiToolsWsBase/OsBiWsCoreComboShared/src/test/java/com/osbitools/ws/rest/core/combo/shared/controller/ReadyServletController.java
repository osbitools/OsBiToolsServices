/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-11-27
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.core.combo.shared.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.core.shared.daemons.DsFilesCheck;
import com.osbitools.ws.core.shared.daemons.LsFilesCheck;
import com.osbitools.ws.rest.core.combo.shared.common.CoreRestTestConstants;
import com.osbitools.ws.rest.shared.base.controller.BaseController;

/**
 * 
 * Used solely for testing purposes Return daemon ready status
 *
 */

@RestController
@RequestMapping(CommonConstants.BASE_URL)
public class ReadyServletController extends BaseController {

  @Autowired
  private DsFilesCheck _dcheck;

  @Autowired
  private LsFilesCheck _lcheck;

  @RequestMapping(value = CoreRestTestConstants.RDY_URL, method = RequestMethod.GET,
      produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<?> checkReady() {
    return _dcheck.isInit() && _lcheck.isInit() ? 
        new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
  }

}
