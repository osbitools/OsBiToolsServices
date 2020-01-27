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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.rest.shared.base.utils.GenericRestMultiTest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,
    value = { "spring.config.name=test" })
public class WebSocketIT extends GenericRestMultiTest {

  @Autowired
  private void setCoreWsConfig(CoreWsConfig cfg) throws Exception {
    setStaticValue("CFG", cfg);
  }

  @Autowired
  private void setObjectMapper(ObjectMapper mapper) throws Exception {
    setStaticValue("MAPPER", mapper);
  }

  @Override
  public Class<?> getTestClass() {
    return WebSocketTestEx.class;
  }
}
