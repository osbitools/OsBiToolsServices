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

package com.osbitools.ws.shared.web.common;

import java.lang.reflect.InvocationTargetException;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.osbitools.ws.shared.config.BaseAppWsConfig;
import com.osbitools.ws.shared.web.common.SharedWebConstants;
import com.osbitools.ws.shared.web.config.BaseWebConfig;
import com.osbitools.ws.shared.common.AbstractWsTestAppConfig;
import com.osbitools.ws.shared.web.config.BaseWebConfigProperties;

/**
 * Base Configuration class for Web Based Application Configuration
 * 
 */

public abstract class AbstractSharedWebTestAppConfig<T extends BaseAppWsConfig>
    extends AbstractWsTestAppConfig<T> {

  private BaseWebConfigProperties _wprops;

  public abstract BaseWebConfig getWebConfig();

  public abstract BaseWebConfigProperties getWebConfigProperties();

  @Bean("web_cfg")
  @DependsOn("web_cfg_props")
  public BaseWebConfig webCfg()
      throws NoSuchMethodException, SecurityException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    BaseWebConfig cfg = getWebConfig();
    cfg.setFileName(SharedWebConstants.WEB_CONFIG_FILE_NAME);
    cfg.setHomeDir(getHomeDir());
    cfg.setLogger(getLogger());
    cfg.readCtxHomeDir(_wprops);
    return cfg;
  }

  @Bean("web_cfg_props")
  @ConfigurationProperties("test.web.config")
  public BaseWebConfigProperties webConfigProperties() {
    _wprops = getWebConfigProperties();
    return _wprops;
  }
}
