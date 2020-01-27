/*
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-06-19
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.core.shared.config;

import java.lang.reflect.InvocationTargetException;

import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.shared.common.TestConstants;

public class TestCoreWsConfig extends CoreWsConfig {

  // Serial Version UID
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor
   */
  public TestCoreWsConfig() {
  }

  @Override
  public void readCtxHomeDir() throws NoSuchMethodException, SecurityException,
      IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    setHomeDir(TestConstants.TARGET_DIR);
  }
  
}
