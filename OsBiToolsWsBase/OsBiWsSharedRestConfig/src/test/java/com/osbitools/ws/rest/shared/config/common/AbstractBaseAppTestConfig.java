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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.context.annotation.SessionScope;

import com.osbitools.ws.shared.common.AbstractAnonynmousUserTestAppConfig;
import com.osbitools.ws.shared.service.RequestLogger;
import com.osbitools.ws.shared.service.impl.RequestLoggerImpl;

/**
 * Test Application Configuration for Anonymous User Service
 * 
 */

public abstract class AbstractBaseAppTestConfig
    extends AbstractAnonynmousUserTestAppConfig {

  @Bean("rlog")
  @SessionScope
  @DependsOn({ "log", "user_service" })
  public RequestLogger requestLogger() {
    return new RequestLoggerImpl();
  }
}
