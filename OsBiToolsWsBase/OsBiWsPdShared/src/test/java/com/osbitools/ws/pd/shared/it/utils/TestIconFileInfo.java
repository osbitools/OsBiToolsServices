/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-08-04
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.pd.shared.it.utils;

import java.io.File;
import java.util.Base64;
import java.util.Map;

import com.osbitools.ws.rest.prj.shared.service.IExFileInfo;
import com.osbitools.ws.shared.GenericUtils;
import com.osbitools.ws.base.WsSrvException;

/**
 * Image Files processing
 * 
 */
public class TestIconFileInfo implements IExFileInfo<String> {

  @Override
  public String getSaveInfo(File f, Map<String, String> params) throws WsSrvException {
    return "{\"base64\":\"" +
        new String(Base64.getEncoder().encode(GenericUtils.readFile(f))) + "\"}";
  }
}
