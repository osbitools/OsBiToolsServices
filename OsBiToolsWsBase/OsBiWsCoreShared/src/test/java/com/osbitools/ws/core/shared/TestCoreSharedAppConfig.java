/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 * Date: 2017-04-19
 * 
 */

package com.osbitools.ws.core.shared;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.core.shared.config.CoreWsConfigProperties;
import com.osbitools.ws.core.shared.config.TestCoreWsConfig;
import com.osbitools.ws.shared.common.AbstractWsTestAppConfig;
import com.osbitools.ws.shared.config.BaseWsConfigProperties;

/**
 * Configuration class for Spring Boot Test Launcher
 * 
 */

@TestConfiguration
@EnableConfigurationProperties
public class TestCoreSharedAppConfig
    extends AbstractWsTestAppConfig<CoreWsConfig> {

  @Override
  protected String getTestName() {
    return "CoreShared";
  }

  @Override
  public BaseWsConfigProperties getWsConfigProperties() throws Exception {
    return new CoreWsConfigProperties();
  }

  @Override
  public CoreWsConfig getBaseWsConfig() {
    return new TestCoreWsConfig();
  }

  // ********** Beans Definition ******************
  
  @Bean("core_ws_cfg_props")
  @ConfigurationProperties("test.ws.config")
  public BaseWsConfigProperties wsConfigProps() throws Exception {
    return getWsConfigProps();
  }
  
  @Bean("core_ws_cfg")
  @DependsOn({ "core_ws_cfg_props", "log" })
  public CoreWsConfig wsCfg() throws Exception {
    return getWsCfg();
  }
}
