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

package com.osbitools.ws.shared.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.osbitools.ws.shared.config.BaseAppWsConfig;
import com.osbitools.ws.shared.config.BaseWsConfigProperties;
import com.osbitools.ws.shared.config.BaseAppWsConfigProperties;

/**
 * Test Application Configuration
 * 
 */

public abstract class AbstractSharedAppConfig
    extends AbstractWsTestAppConfig<BaseAppWsConfig> {

  @Override
  public BaseWsConfigProperties getWsConfigProperties() {
    return new BaseAppWsConfigProperties();
  }

  @Override
  public BaseAppWsConfig getBaseWsConfig() {
    return new BaseAppWsConfig();
  }

  @Bean("ws_cfg_props")
  @ConfigurationProperties("test.ws.config")
  public BaseWsConfigProperties wsConfigProps() throws Exception {
    return getWsConfigProps();
  }
  
  @Bean("ws_cfg")
  @DependsOn({ "ws_cfg_props", "log" })
  public BaseAppWsConfig wsCfg() throws Exception {
    return getWsCfg();
  }
}
