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

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osbitools.ws.core.shared.config.CoreWsConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,
    value = { "spring.config.name=test" })
public class WebSocketTest extends WebSocketTestUnit {

  @LocalServerPort
  private int port;

  @Autowired
  private CoreWsConfig _cfg;

  @Autowired
  private ObjectMapper _mapper;

  @Autowired
  private TestRestTemplate _template;

  @Autowired
  private Logger _log;
  
  @Override
  protected CoreWsConfig getCoreWsConfig() {
    return _cfg;
  }

  @Override
  protected TestRestTemplate getTestRestTemplate() {
    return _template;
  }

  @Override
  protected ObjectMapper getObjectMapper() {
    return _mapper;
  }

  @Override
  protected int getPort() {
    return port;
  }

  @Override
  protected Logger getLogger() {
    return _log;
  }

}

