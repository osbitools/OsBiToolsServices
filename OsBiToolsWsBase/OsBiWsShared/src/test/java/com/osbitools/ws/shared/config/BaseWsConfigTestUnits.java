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

package com.osbitools.ws.shared.config;

import com.osbitools.ws.shared.common.TestConstants;
import com.osbitools.ws.shared.config.AbstractConfig;

/**
 * Abstract file for Application mode WebService Configuration Tests
 * 
 */

public abstract class BaseWsConfigTestUnits<T extends AbstractConfig>
    extends BaseConfigTestUnits<T> {

  @Override
  protected String getConfigFileName() {
    return TestConstants.WORK_WS_CONFIG_FILE;
  }

}
