/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2015-05-04T00:30Z
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.wp.shared;

import com.osbitools.ws.shared.common.ITestResourceConfig;

/**
 * Web Page Resource Configuration
 * 
 */
public class WebPageTestResConfig implements ITestResourceConfig {

  @Override
  public String getTopResDir() {
    return WpTestConstants.WEB_PAGES_PACKAGE;
  }

  @Override
  public String getMainDemoFileName() {
    return "wp.xml";
  }
}
