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

package com.osbitools.ws.rest.pd.shared.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.osbitools.ws.pd.shared.config.PdWsConfig;

/**
 * Ws Configuration for OsBiTools Page Designer Microservice
 * 
 */
@Component("prj_ws_cfg")
public class PdAppWsConfig extends PdWsConfig {

  // Serial Version UID
  private static final long serialVersionUID = 1L;

  @Value("${home.dir}")
  public void initHomeDir(String homeDir) {
    setHomeDir(homeDir);
  }
  
  @PostConstruct
  public void initConfig() throws Exception {
    initAppConfig();
  }
}
