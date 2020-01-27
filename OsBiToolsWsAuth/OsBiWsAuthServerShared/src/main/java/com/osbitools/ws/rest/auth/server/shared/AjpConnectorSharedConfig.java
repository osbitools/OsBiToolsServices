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

package com.osbitools.ws.rest.auth.server.shared;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

/**
 * Spring Boot Launcher
 * 
 */
public class AjpConnectorSharedConfig {

  @Value("${tomcat.ajp.port:8009}")
  int ajpPort;

  @Value("${tomcat.ajp.enabled:false}")
  boolean tomcatAjpEnabled;
  
  @Bean
  public TomcatServletWebServerFactory servletContainer() {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();

    if (tomcatAjpEnabled) {
      Connector ajpConnector = new Connector("AJP/1.3");
      ajpConnector.setPort(ajpPort);
      ajpConnector.setSecure(false);
      ajpConnector.setAllowTrace(false);
      ajpConnector.setScheme("http");
      tomcat.addAdditionalTomcatConnectors(ajpConnector);
    }

    return tomcat;
  }

}
