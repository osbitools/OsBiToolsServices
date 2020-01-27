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


import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.osbitools.ws.shared.service.WsInfo;
import com.osbitools.ws.shared.service.impl.AppWsInfo;

/**
 * Test Application Configuration
 * 
 */

public abstract class BaseTestAppConfig {

  protected abstract String getTestName();
  
  @Bean("log")
  public Logger logger() {
    TestConstants.LOG.info("Poehali ...");
    return TestConstants.LOG;
  }

  @Bean("ws_info")
  @DependsOn("log")
  protected WsInfo appInfo() {
    return new AppWsInfo(getTestName() + "Test");
  }
  
  protected Logger getLogger() {
    return TestConstants.LOG;
  }
  
}
