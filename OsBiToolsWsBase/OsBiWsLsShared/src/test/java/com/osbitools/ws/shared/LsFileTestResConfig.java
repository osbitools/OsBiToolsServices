/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2015-08-10
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared;

import com.osbitools.ws.shared.common.ITestResourceConfig;

/**
 * LangLables Set File Resource Configuration
 * 
 */

public class LsFileTestResConfig implements ITestResourceConfig {

  @Override
  public String getTopResDir() {
    return LsTestConstants.LANG_SET_RES_PACKAGE;
  }

  @Override
  public String getMainDemoFileName() {
    return "ll_set";
  }
}
