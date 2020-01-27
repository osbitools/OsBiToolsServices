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

package com.osbitools.ws.rest.auth.shared;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;

import com.osbitools.ws.rest.auth.common.Constants;

/**
 * AutoConfiguration file for shared library
 * 
 */
@Configuration
@ComponentScan("com.osbitools.ws.rest.auth.shared")
public class AutoConfiguration {

  @Bean
  public CookieHttpSessionIdResolver cookieHttpSessionIdResolver() {
    CookieHttpSessionIdResolver resolver = new CookieHttpSessionIdResolver();
    DefaultCookieSerializer serializer = new DefaultCookieSerializer();
    serializer.setCookieName(Constants.COOKIE_SESSION_NAME);
    serializer.setUseBase64Encoding(false);
    resolver.setCookieSerializer(serializer);
    
    return resolver;
  }
}
