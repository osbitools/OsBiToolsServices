/*
 * Open Source Business Intelligence Tools - http://www.osgitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-01-01
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.auth.server.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Boot Launcher
 * 
 */
@Configuration
@ComponentScan("com.osbitools.ws.rest.auth.server.demo")
public class AutoConfiguration {

  @Bean("log)")
  public Logger getLogger() {
    return LoggerFactory.getLogger("com.osbitools.ws.rest.auth.server.demo");
  }

}
