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
 * Date: 2018-08-04
 * 
 */

package com.osbitools.ws.pd.shared;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.osbitools.ws.pd.shared.config.PdWebConfig;
import com.osbitools.ws.pd.shared.config.PdWebConfigProperties;
import com.osbitools.ws.pd.shared.config.PdWsConfig;
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
public class PdSharedTestAppConfig extends AbstractSharedWebTestAppConfig<PdWsConfig> {

  @Override
  protected String getTestName() {
    return "PdSharedTest";
  }
  
  @Override
  public PdWsConfig getBaseWsConfig() {
    return new PdWsConfig();
  }

  @Override
  public BaseWsConfigProperties getWsConfigProperties() {
    return new TestPrjWsConfigProperties();
  }
  
  @Override
  public BaseWebConfig getWebConfig() {
    return new PdWebConfig();
  }

  @Override
  public BaseWebConfigProperties getWebConfigProperties() {
    return new PdWebConfigProperties();
  }
  
  //**********Beans Definition ******************
    
   @Bean("prj_ws_cfg_props")
   @ConfigurationProperties("test.ws.config")
   public BaseWsConfigProperties wsConfigProps() throws Exception {
     return getWsConfigProps();
   }
 
  @Bean("prj_ws_cfg")
  @DependsOn({ "prj_ws_cfg_props", "log" })
  public PdWsConfig wsCfg() throws Exception {
    return getWsCfg();
  }

}
