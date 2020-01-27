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

import org.springframework.beans.factory.annotation.Value;

/**
 * Test Application Configuration
 * 
 */

public abstract class AbstractHomeDirTestAppConfig extends BaseTestAppConfig {

  @Value("${home.dir}")
  private String _hdir;
  
  protected String getHomeDir() {
    return _hdir;
  }
  
}
