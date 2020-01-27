/*
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2019-08-31
 * 
 * Contributors:
 *
 */

package com.osbitools.ws.rest.core.combo.shared.test;

import org.slf4j.Logger;
import org.springframework.boot.test.web.client.TestRestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osbitools.ws.core.shared.config.CoreWsConfig;

public class WebSocketTestEx extends WebSocketTestUnit {

  private static int LOCAL_PORT;

  private static CoreWsConfig CFG;

  private static ObjectMapper MAPPER;

  private static TestRestTemplate REST_TEMPLATE;

  private static Logger LOGGER;
  
  @Override
  protected CoreWsConfig getCoreWsConfig() {
    return CFG;
  }

  @Override
  protected TestRestTemplate getTestRestTemplate() {
    return REST_TEMPLATE;
  }

  @Override
  protected ObjectMapper getObjectMapper() {
    return MAPPER;
  }

  @Override
  protected int getPort() {
    return LOCAL_PORT;
  }

  @Override
  protected Logger getLogger() {
    return LOGGER;
  }
  
}

