/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2016-06-09
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.shared.config.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.osbitools.ws.shared.service.WsInfo;
import com.osbitools.ws.shared.service.impl.AppWsInfo;

/**
 * Base minimal OsBiTools Microservice application Configuration
 * 
 */

public abstract class AbstractBaseAppConfig {

  protected abstract String getAppName();
  
  @Bean("log")
  public Logger getLogger() {
    Logger _log = LoggerFactory.getLogger(getAppName());
    _log.info("Poehali ...");
    return _log;
  }
  
  @Bean("ws_info")
  @DependsOn("log")
  protected WsInfo wsInfo() {
    return new AppWsInfo(getAppName());
  }

}
