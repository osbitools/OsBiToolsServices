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

package com.osbitools.ws.shared.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.osbitools.ws.shared.config.BaseWsConfig;
import com.osbitools.ws.shared.service.AbstractBaseService;

@Service("base_srv")
public class BaseServiceImpl extends AbstractBaseService<BaseWsConfig> {

  @Autowired(required = false)
  @Qualifier("ws_cfg")
  private BaseWsConfig _wcfg;
  
  @Override
  public BaseWsConfig getWsConfig() {
    return _wcfg;
  }
  
}
