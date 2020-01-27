/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-01-01
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.shared.SharedTestAppConfig;
import com.osbitools.ws.shared.config.BaseWsConfig;
import com.osbitools.ws.shared.service.BaseService;
import com.osbitools.ws.shared.service.impl.AppWsInfo;
import com.osbitools.ws.shared.service.impl.BaseServiceImpl;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@ContextConfiguration(classes = { SharedTestAppConfig.class, BaseServiceImpl.class })
public class BaseServiceTest {

  @Autowired
  BaseService<BaseWsConfig> _bs;
  
  @Test
  public void testGetWebAppVersion() {
    AppWsInfo info = (AppWsInfo) _bs.getWsInfo();
    assertFalse(info.hasMavenVersion());
    assertNull(info.getWsVersion());
  }
}
