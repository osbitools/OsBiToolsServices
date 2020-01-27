package com.osbitools.ws.rest.auth.prov.tomcat;

import org.slf4j.Logger;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import com.osbitools.ws.shared.common.TestConstants;

@SpringBootConfiguration
public class TomcatAuthProviderConfig  {

  @Bean
  public Logger getLogger() {
    return TestConstants.LOG;
  }
}
