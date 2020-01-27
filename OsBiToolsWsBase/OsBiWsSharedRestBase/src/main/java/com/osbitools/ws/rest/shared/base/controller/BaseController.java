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

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.IServiceErrorCode;
import com.osbitools.ws.shared.service.RequestLogger;

public class BaseController {

  @Autowired
  @Qualifier("rlog")
  private RequestLogger _rlog;
  
  public RequestLogger getLogger() {
    return _rlog;
  }

  @ExceptionHandler(WsSrvException.class)
  public WsSrvException handleWsSrvException(WsSrvException e, HttpServletResponse resp) {
    e.setRequestId(_rlog.getRequestId());
    _rlog.error(e);
    
    if (e instanceof IServiceErrorCode)
      resp.setStatus(((IServiceErrorCode) e).getServiceErrorCode(e.getErrorCode()));
    
    return e;
  }
}
