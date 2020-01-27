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

package com.osbitools.ws.me.shared;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.osbitools.ws.me.shared.config.MeWebConfig;
import com.osbitools.ws.me.shared.config.MeWsConfig;
import com.osbitools.ws.rest.prj.shared.config.PrjWebConfigProperties;
import com.osbitools.ws.rest.prj.shared.config.TestPrjWsConfigProperties;
import com.osbitools.ws.shared.web.common.AbstractSharedWebTestAppConfig;
import com.osbitools.ws.shared.config.BaseWsConfigProperties;
import com.osbitools.ws.shared.web.config.BaseWebConfig;
import com.osbitools.ws.shared.web.config.BaseWebConfigProperties;

/**
 * Configuration class for Spring Boot Test Launcher
 * 
 */

@SpringBootConfiguration
@EnableAutoConfiguration
@EnableConfigurationProperties
public class MeSharedTestAppConfig extends AbstractSharedWebTestAppConfig<MeWsConfig> {

  @Override
  protected String getTestName() {
    return "MeSharedTest";
  }
  
  @Override
  public MeWsConfig getBaseWsConfig() {
    return new MeWsConfig();
  }

  @Override
  public BaseWsConfigProperties getWsConfigProperties() {
    return new TestPrjWsConfigProperties();
  }
  
  @Override
  public BaseWebConfig getWebConfig() {
    return new MeWebConfig();
  }

  @Override
  public BaseWebConfigProperties getWebConfigProperties() {
    return new PrjWebConfigProperties();
  }
  
  //**********Beans Definition ******************
    
   @Bean("prj_ws_cfg_props")
   @ConfigurationProperties("test.ws.config")
   public BaseWsConfigProperties wsConfigProps() throws Exception {
     return getWsConfigProps();
   }
 
  @Bean("prj_ws_cfg")
  @DependsOn({ "prj_ws_cfg_props", "log" })
  public MeWsConfig wsCfg() throws Exception {
    return getWsCfg();
  }

}
