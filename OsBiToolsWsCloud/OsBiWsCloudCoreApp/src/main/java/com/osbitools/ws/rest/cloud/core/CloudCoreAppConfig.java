/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-01-01
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.cloud.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.osbitools.ws.shared.service.impl.AnonymousUserServiceImpl;
import com.osbitools.ws.shared.common.UserService;


/**
 * Non-secure application Configuration
 * 
 */

@Configuration
public class CloudCoreAppConfig {
  
  @Bean
  public UserService anonymousUserService() {
    return new AnonymousUserServiceImpl();
  }
}
