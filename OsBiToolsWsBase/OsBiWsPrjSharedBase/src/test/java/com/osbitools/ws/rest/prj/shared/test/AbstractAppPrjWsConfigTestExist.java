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

package com.osbitools.ws.rest.prj.shared.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.osbitools.ws.rest.prj.shared.config.AbstractPrjWsConfig;
import com.osbitools.ws.shared.config.BaseAppWsConfig;
import com.osbitools.ws.shared.config.app.AbstractAppWsConfigTestExist;

/**
 * Basic Web Tests
 * 
 */

public abstract class AbstractAppPrjWsConfigTestExist<T extends AbstractPrjWsConfig>
    extends AbstractAppWsConfigTestExist<T> {

  protected abstract String getWorkDir();

  @Override
  protected void checkConfig(AbstractPrjWsConfig wcfg) {
    // Cast to ancestor class to avoid compiler complain
    // abstract method cannot be accessed directly
    super.checkConfig((BaseAppWsConfig) wcfg);

    assertTrue(wcfg.getMinified());
    assertEquals("prod_local", wcfg.getGitRemoteName());
    assertEquals("file:/tmp", wcfg.getGitRemoteUrl().toString());
    assertEquals(5, wcfg.getMaxUploadFileSize().intValue());
    assertEquals(5 * 1024 * 1024, wcfg.getMaxUploadFileSizeBytes().intValue());

    assertEquals(getWorkDir(), wcfg.getBaseDir());
  }

  @Override
  protected String getWsCfgName() {
    return "prj_ws_cfg";
  }
}