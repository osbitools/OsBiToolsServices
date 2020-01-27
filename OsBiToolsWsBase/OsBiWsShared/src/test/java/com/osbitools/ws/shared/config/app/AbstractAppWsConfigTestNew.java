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

package com.osbitools.ws.shared.config.app;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;

import com.osbitools.ws.shared.config.BaseAppConfigTestUnits;
import com.osbitools.ws.shared.config.BaseAppWsConfig;

/**
 * Basic Web Tests for SharedRestBaseWsConfig. Clear all existing configuration before test start
 * to test correct load from application.properties file
 * 
 */

public abstract class AbstractAppWsConfigTestNew<T extends BaseAppWsConfig>
    extends BaseAppConfigTestUnits<T> {

  @BeforeClass
  public static void clearConfigFile() throws IOException {
    delWsConfigFile();
  }

  @Override
  protected void checkConfig(BaseAppWsConfig wcfg) {
    assertTrue(wcfg.getDebug());
    assertEquals("en", wcfg.getLang());
  }

}
