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

package com.osbitools.ws.rest.shared.config.common;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * Abstract REST Web App Configuration
 * 
 */
public abstract class AbstractBaseRestAjpConfig {

  @Value("${tomcat.ajp.port:8009}")
  int ajpPort;

  @Value("${tomcat.ajp.enabled:false}")
  boolean tomcatAjpEnabled;

  @Bean
  public WebServerFactoryCustomizer<TomcatServletWebServerFactory>
      initAjpConnector() {
    return new WebServerFactoryCustomizer<TomcatServletWebServerFactory>() {

      @Override
      public void customize(TomcatServletWebServerFactory factory) {
        if (tomcatAjpEnabled) {
          Connector ajpConnector = new Connector("AJP/1.3");
          ajpConnector.setPort(ajpPort);
          ajpConnector.setSecure(false);
          ajpConnector.setAllowTrace(false);
          ajpConnector.setScheme("http");
          factory.addAdditionalTomcatConnectors(ajpConnector);
        }
      }
    };
  }

}
