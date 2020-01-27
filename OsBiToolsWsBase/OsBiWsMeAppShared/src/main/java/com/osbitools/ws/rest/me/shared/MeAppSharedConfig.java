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

package com.osbitools.ws.rest.me.shared;

import org.springframework.context.annotation.Configuration;

import com.osbitools.ws.rest.prj.rest.shared.common.AbstractPrjBaseAppConfig;
import com.osbitools.ws.rest.prj.shared.config.PrjWebConfigProperties;

/**
 * Application Configuration
 * 
 */

@Configuration
public class MeAppSharedConfig extends AbstractPrjBaseAppConfig {

  @Override
  protected String getAppName() {
    return "OsBiTools Map Editor API";
  }

  @Override
  protected PrjWebConfigProperties getWebConfigProperties() {
    return new PrjWebConfigProperties();
  }
}
