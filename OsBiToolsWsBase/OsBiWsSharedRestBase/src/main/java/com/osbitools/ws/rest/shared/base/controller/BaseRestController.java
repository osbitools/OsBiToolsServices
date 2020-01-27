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

import com.osbitools.ws.shared.config.AbstractConfig;
import com.osbitools.ws.shared.service.BaseService;

/**
 * Same as BaseController but added mapping for version
 * 
 *
 */

public abstract class BaseRestController<T extends BaseService<T1>, 
                  T1 extends AbstractConfig> extends BaseController {

  @Autowired
  @Qualifier("base_srv")
  private BaseService<T1> _bs;
  
  protected BaseService<T1> getBaseService() {
    return _bs;
  }
  
  protected String getVersion() {
    getLogger().trace("Version request");
    return _bs.getWsVersion();
  }
}
