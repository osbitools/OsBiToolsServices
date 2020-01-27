/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-02-24
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.osbitools.ws.rest.prj.shared.config.AbstractPrjWsConfig;
import com.osbitools.ws.shared.service.RequestLogger;

/**
 * Base Service class
 * 
 */
public abstract class AbstractBaseService {

  @Autowired
  @Qualifier("prj_ws_cfg")
  private AbstractPrjWsConfig _cfg;

  @Autowired
  @Qualifier("rlog")
  private RequestLogger _rlog;

  public AbstractPrjWsConfig getWsCfg() {
    return _cfg;
  }

  public RequestLogger getReqLog() {
    return _rlog;
  }
}
