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

import com.osbitools.ws.shared.config.BaseAppWsConfig;
import com.osbitools.ws.shared.config.BaseAppWsConfigProperties;
import com.osbitools.ws.shared.config.BaseWsConfigProperties;
import com.osbitools.ws.shared.web.config.BaseWebConfig;
import com.osbitools.ws.shared.web.config.BaseWebConfigProperties;
import com.osbitools.ws.shared.web.config.TestWebConfig;
import com.osbitools.ws.shared.web.config.TestWebConfigProperties;

/**
 * Shared Configuration class for Web Spring Boot Test Launcher
 * 
 */

public abstract class AbstractSharedRestWebAppConfig
    extends AbstractSharedWebTestAppConfig<BaseAppWsConfig> {

  @Override
  public BaseWebConfig getWebConfig() {
    return new TestWebConfig();
  }

  @Override
  public BaseWebConfigProperties getWebConfigProperties() {
    return new TestWebConfigProperties();
  }

  @Override
  public BaseWsConfigProperties getWsConfigProperties() {
    return new BaseAppWsConfigProperties();
  }

  @Override
  public BaseAppWsConfig getBaseWsConfig() {
    return new BaseAppWsConfig();
  }
}
