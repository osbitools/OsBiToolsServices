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

import com.osbitools.ws.shared.Constants;
import com.osbitools.ws.shared.config.BaseWsConfig;
import com.osbitools.ws.shared.config.BaseWsConfigProperties;

/**
 * Test Application Configuration
 * 
 */

public abstract class AbstractWsTestAppConfig<T extends BaseWsConfig>
    extends AbstractUserServiceTestAppConfig {

  private BaseWsConfigProperties _props;

  public abstract BaseWsConfigProperties getWsConfigProperties()
      throws Exception;

  public abstract T getBaseWsConfig();

  public BaseWsConfigProperties getWsConfigProps() throws Exception {
    return _props = getWsConfigProperties();
  }

  protected T getWsCfg() throws Exception {
    T cfg = getBaseWsConfig();
    cfg.setFileName(Constants.WS_CONFIG_FILE_NAME);
    cfg.setLogger(getLogger());
    cfg.setHomeDir(getHomeDir());
    cfg.readCtxHomeDir(_props);
    return cfg;
  }

  /**
   * Retrieve internal WsConfig Properties. Used for test.
   * 
   * @return WsConfig Properties
   */
  protected BaseWsConfigProperties getWsProperties() {
    return _props;
  }
}
