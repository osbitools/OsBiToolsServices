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

package com.osbitools.ws.rest.auth.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * Configuration class for RedisHttpSession Define special namespace to store
 * session data
 *
 */
@Configuration
@EnableRedisHttpSession(redisNamespace = Constants.SESSION_NAMESPACE)
public class Config {

  @Value("${cookie.domain:" + Constants.DEF_COOKIE_DOMAIN + "}")
  private String cookieDomain;

  @Value("${cookie.path:" + Constants.DEF_COOKIE_PATH + "}")
  private String cookiePath;

  @Bean
  public CookieSerializer cookieSerializer() {
    DefaultCookieSerializer s = new DefaultCookieSerializer();
    s.setCookieName(Constants.COOKIE_SESSION_NAME);
    s.setDomainName(cookieDomain);
    s.setCookiePath(cookiePath);

    return s;
  }

}
