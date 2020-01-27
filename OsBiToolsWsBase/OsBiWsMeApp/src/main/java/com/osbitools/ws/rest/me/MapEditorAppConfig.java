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

package com.osbitools.ws.rest.me;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.osbitools.ws.rest.shared.config.common.AbstractBaseRestAjpConfig;
import com.osbitools.ws.shared.common.UserService;
import com.osbitools.ws.shared.service.impl.AnonymousUserServiceImpl;

/**
 * Application Configuration
 * 
 */

@Configuration
public class MapEditorAppConfig extends AbstractBaseRestAjpConfig {
  
  @Bean("user_service")
  public UserService anonymousUserService() {
    return new AnonymousUserServiceImpl();
  }
}
