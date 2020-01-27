/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2015-03-15T00:30Z
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.shared;

import com.osbitools.ws.shared.common.ITestResourceConfig;

/**
 * DataSet Maps Resource Configuration
 * 
 */

public class DsMapTestResConfig implements ITestResourceConfig {

  @Override
  public String getTopResDir() {
    return TestDsConstants.DS_MAPS_PACKAGE;
  }

  @Override
  public String getMainDemoFileName() {
    return TestDsConstants.DS_DEMO_FNAME;
  }
}
