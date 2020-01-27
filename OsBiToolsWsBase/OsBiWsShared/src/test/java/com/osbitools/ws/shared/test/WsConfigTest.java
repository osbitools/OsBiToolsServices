/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2016-30-05
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.shared.test;

import com.osbitools.ws.shared.config.AbstractWsConfigTest;
import com.osbitools.ws.shared.config.BaseAppWsConfig;

public class WsConfigTest extends AbstractWsConfigTest<BaseAppWsConfig> {

  @Override
  protected BaseAppWsConfig getNewConfigBean(String fileName) {
    return new BaseAppWsConfig(fileName, getLogger());
  }

  @Override
  protected BaseAppWsConfig getExternalConfigBean(String fileName, String dir) {
    return new BaseAppWsConfig(fileName, dir, debug1.toString(), lang1, getLogger());
  }

}
