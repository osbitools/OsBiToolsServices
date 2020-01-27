/*
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-01-01
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.shared.config.app;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.junit.BeforeClass;

import com.osbitools.ws.shared.Constants;
import com.osbitools.ws.shared.common.TestConstants;
import com.osbitools.ws.shared.config.BaseAppConfigTestUnits;
import com.osbitools.ws.shared.config.BaseAppWsConfig;

/**
 * Basic Web Tests for SharedRestBaseWsConfig. Recreate configuration.properties file before test start
 * to test correct load from external configuration.properties file
 * 
 */

public abstract class AbstractAppWsConfigTestExist<T extends BaseAppWsConfig>
    extends BaseAppConfigTestUnits<T> {

  @BeforeClass
  public static void clearExistingPropFile() throws IOException {
    // Copy file from src folder. Rewrite if exist
    Files.copy(
        new File(TestConstants.SRC_TEST_DIR + TestConstants.CONFIG_DIR +
            Constants.WS_CONFIG_FILE_NAME).toPath(),
        new File(TestConstants.WORK_WS_CONFIG_FILE).toPath(),
        StandardCopyOption.REPLACE_EXISTING);

  }

  @Override
  protected void checkConfig(BaseAppWsConfig wcfg) {
    assertFalse(wcfg.getDebug());
    assertEquals("en-CA", wcfg.getLang());
  }

}