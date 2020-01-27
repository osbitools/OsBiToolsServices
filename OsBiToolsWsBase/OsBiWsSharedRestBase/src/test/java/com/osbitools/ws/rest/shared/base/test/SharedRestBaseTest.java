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

package com.osbitools.ws.rest.shared.base.test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.rest.shared.base.rest.SharedRestBaseTestUnit;

/**
 * Top launcher for single threaded test
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,
    value = { "spring.config.name=test" })
public class SharedRestBaseTest extends SharedRestBaseTestUnit {

  @Autowired
  private TestRestTemplate _rest;

  @Autowired
  private Logger _log;

  @LocalServerPort
  private int _port;

  @Override
  protected TestRestTemplate getRestTemplate() {
    return _rest;
  }

  @Override
  protected int getPort() {
    return _port;
  }

  @Override
  protected Logger getLogger() {
    return _log;
  }
}
