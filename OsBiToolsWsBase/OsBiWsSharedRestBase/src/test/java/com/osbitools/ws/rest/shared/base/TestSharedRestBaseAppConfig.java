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

package com.osbitools.ws.rest.shared.base;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import com.osbitools.ws.shared.common.AbstractSharedAppConfig;

/**
 * Configuration class for Spring Boot Test Launcher
 * 
 */

@SpringBootConfiguration
@EnableAutoConfiguration
public class TestSharedRestBaseAppConfig
    extends AbstractSharedAppConfig {

  @Override
  protected String getTestName() {
    return "SharedRestBase";
  }
}
