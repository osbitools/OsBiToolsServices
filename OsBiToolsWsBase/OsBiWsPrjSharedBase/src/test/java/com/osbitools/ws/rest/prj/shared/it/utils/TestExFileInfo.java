/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2015-07-02
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.it.utils;

import java.io.File;
import java.util.Map;

import com.osbitools.ws.rest.prj.shared.service.IExFileInfo;
import com.osbitools.ws.base.WsSrvException;

/**
 * Implements IExFileInfo interface for test purposes
 * 
 */

public class TestExFileInfo implements IExFileInfo<String> {

  @Override
  public String getSaveInfo(File f, Map<String, String> params) throws WsSrvException {
    return "";
  }
}
