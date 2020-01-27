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

package com.osbitools.ws.rest.core.combo.shared;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.osbitools.ws.core.shared.config.CoreWsConfigProperties;
import com.osbitools.ws.rest.shared.config.common.AbstractBaseAppConfig;

/**
 * Class with shared combo configuration
 * 
 */

@Configuration
public class CoreComboSharedConfig extends AbstractBaseAppConfig {

  @Bean("core_ws_cfg_props")
  @ConfigurationProperties("ws.config")
  public CoreWsConfigProperties coreWsConfigProperties() {
    return new CoreWsConfigProperties();
  }

  @Override
  protected String getAppName() {
    return "OsBiTools Core App";
  }
}
