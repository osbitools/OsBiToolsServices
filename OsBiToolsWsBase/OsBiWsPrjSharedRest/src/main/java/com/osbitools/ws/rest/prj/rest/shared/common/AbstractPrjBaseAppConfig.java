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

package com.osbitools.ws.rest.prj.rest.shared.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.context.annotation.SessionScope;

import com.osbitools.ws.rest.prj.shared.config.PrjWebConfigProperties;
import com.osbitools.ws.rest.prj.shared.config.PrjWsConfigProperties;
import com.osbitools.ws.rest.shared.config.common.AbstractBaseAppConfig;
import com.osbitools.ws.shared.service.RequestLogger;
import com.osbitools.ws.shared.service.impl.RequestLoggerImpl;

/**
 * Base Application Configuration for Project Management based services
 * 
 */

public abstract class AbstractPrjBaseAppConfig extends AbstractBaseAppConfig {

  protected abstract PrjWebConfigProperties getWebConfigProperties();
  
  @Bean("prj_ws_cfg_props")
  @ConfigurationProperties("ws.config")
  public PrjWsConfigProperties prjWsConfigProperties() {
    return new PrjWsConfigProperties();
  }

  @Bean("web_cfg_props")
  @ConfigurationProperties("web.config")
  public PrjWebConfigProperties prjWebConfigProperties() {
    return getWebConfigProperties();
  }
  
  @Bean("rlog")
  @SessionScope
  @DependsOn({"log", "user_service"})
  public RequestLogger requestLogger() {
    return new RequestLoggerImpl();
  }
}
