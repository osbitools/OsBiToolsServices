/*
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2016-23-06
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.shared.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.shared.SharedTestAppConfig;
import com.osbitools.ws.shared.config.BaseAppWsConfig;
import com.osbitools.ws.shared.config.app.AbstractAppWsConfigTestNew;

/**
 * Basic Web Tests
 * 
 */

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@ContextConfiguration(classes = { SharedTestAppConfig.class })
public class AppNewWsConfigTest
    extends AbstractAppWsConfigTestNew<BaseAppWsConfig> {

  @Override
  protected String getWsCfgName() {
    return "ws_cfg";
  }

}