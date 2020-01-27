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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.service.RequestLogger;

@ControllerAdvice
public abstract class BaseExceptionHandler {

  @Autowired
  @Qualifier("rlog")
  private RequestLogger _log;
  
  @ExceptionHandler(WsSrvException.class)
  public  ResponseEntity<WsSrvException> 
                  handleWsSrvException(WsSrvException e) {
    // Set request id
    e.setRequestId(_log.getRequestId());
    return new ResponseEntity<WsSrvException>(e, HttpStatus.OK);
  }
  
}
