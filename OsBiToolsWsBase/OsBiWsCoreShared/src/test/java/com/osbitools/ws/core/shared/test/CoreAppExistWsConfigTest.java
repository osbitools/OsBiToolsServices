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

package com.osbitools.ws.core.shared.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.core.shared.TestCoreSharedAppConfig;
import com.osbitools.ws.core.shared.common.CoreSharedTestConstants;
import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.shared.DsConstants;
import com.osbitools.ws.shared.config.BaseAppWsConfig;
import com.osbitools.ws.shared.config.app.AbstractAppWsConfigTestExist;

/**
 * Basic Web Tests
 * 
 */

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@ContextConfiguration(classes = { TestCoreSharedAppConfig.class })
public class CoreAppExistWsConfigTest
    extends AbstractAppWsConfigTestExist<CoreWsConfig> {

  @Autowired
  CoreWsConfig _wcfg;

  @Override
  protected CoreWsConfig getConfig() {
    return _wcfg;
  }

  @Override
  protected void checkConfig(CoreWsConfig wcfg) {
    // Cast to ancestor class to avoid compiler complain
    // abstract method cannot be accessed directly
    super.checkConfig((BaseAppWsConfig) wcfg);

    assertFalse(wcfg.getTrace());
    assertEquals(100, wcfg.getRescan().intValue());
    assertEquals(wcfg.getHomeDir() + File.separator + DsConstants.DS_DIR,
        wcfg.getDsDir());
    assertEquals("Ds dir doesn't match", CoreSharedTestConstants.WORK_DS_DIR,
        _wcfg.getDsDir());
    assertArrayEquals("SubDir List doesn't match", DsConstants.DS_DIR_LIST,
        _wcfg.getHomeSubDirList());
  }

  @Override
  protected String getWsCfgName() {
    return "core_ws_cfg";
  }

}