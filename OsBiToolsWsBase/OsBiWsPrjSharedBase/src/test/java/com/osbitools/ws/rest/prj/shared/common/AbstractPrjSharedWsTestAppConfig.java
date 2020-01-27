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
 * Date: 2018-01-01
 * 
 */

package com.osbitools.ws.rest.prj.shared.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.osbitools.ws.rest.prj.shared.config.AbstractPrjWsConfig;
import com.osbitools.ws.rest.prj.shared.config.PrjWebConfig;
import com.osbitools.ws.rest.prj.shared.config.PrjWebConfigProperties;
import com.osbitools.ws.rest.prj.shared.config.TestPrjMgrWsConfig;
import com.osbitools.ws.rest.prj.shared.config.TestPrjWsConfigProperties;
import com.osbitools.ws.rest.prj.shared.service.EntityService;
import com.osbitools.ws.rest.prj.shared.service.ExFileService;
import com.osbitools.ws.shared.web.common.AbstractSharedWebTestAppConfig;
import com.osbitools.ws.shared.config.BaseWsConfigProperties;
import com.osbitools.ws.shared.web.config.BaseWebConfig;
import com.osbitools.ws.shared.web.config.BaseWebConfigProperties;

/**
 * Configuration class for Spring Boot Test Launcher
 * 
 */

public abstract class AbstractPrjSharedWsTestAppConfig
    extends AbstractSharedWebTestAppConfig<TestPrjMgrWsConfig> {

  abstract public EntityService<?> getEntityService();
  
  abstract public ExFileService<?> getExFileService();
  
  @Override
  public TestPrjMgrWsConfig getBaseWsConfig() {
    return new TestPrjMgrWsConfig();
  }

  @Override
  public BaseWsConfigProperties getWsConfigProperties() throws Exception {
    return new TestPrjWsConfigProperties();
  }
  
  @Override
  public BaseWebConfig getWebConfig() {
    return new PrjWebConfig();
  }

  @Override
  public BaseWebConfigProperties getWebConfigProperties() {
    return new PrjWebConfigProperties();
  }

  // **********Beans Definition ******************
  
  @Bean("prj_ws_cfg_props")
  @ConfigurationProperties("test.ws.config")
  public BaseWsConfigProperties wsConfigProps() throws Exception {
    return getWsConfigProps();
  }
  
  @Bean("prj_ws_cfg")
  @DependsOn({ "prj_ws_cfg_props", "log" })
  public AbstractPrjWsConfig wsCfgEx() throws Exception {
    return getWsCfg();
  }
  
  @Bean
  @DependsOn("prj_ws_cfg")
  public EntityService<?> entityService() {
    return getEntityService();
  }
  
  @Bean
  @DependsOn("prj_ws_cfg")
  public ExFileService<?> exFileService() {
    return getExFileService();
  }
}
