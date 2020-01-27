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

package com.osbitools.ws.shared;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * AutoConfiguration class for shared library
 * 
 */
@Configuration
@ComponentScan("com.osbitools.ws.shared")
public class SharedAutoConfiguration {

  @Autowired
  @Qualifier("log")
  private Logger _log;
  
  @Bean
  public String initSharedErrorList() throws ClassNotFoundException {
    String cname = CustErrorList.class.getName();
    Class.forName(cname);
    _log.info("Initialized error list from: " + cname);
    
    return "ERROR_LIST: " + cname;
  }
}
