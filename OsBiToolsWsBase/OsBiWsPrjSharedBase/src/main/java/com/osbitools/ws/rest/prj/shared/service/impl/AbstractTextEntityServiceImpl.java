/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-01-01
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.base.WsSrvException;

/**
 * Basic XML service implementation for Entity Service interface
 * 
 */
public abstract class AbstractTextEntityServiceImpl extends AbstractEntityServiceImpl<String> {

  @Override
  public String get(InputStream in) throws WsSrvException {
    try {
      return get(BaseUtils.readInputStream(in));
    } catch (IOException e) {
      //-- 224
      throw new WsSrvException(224, e);
    }
  }
  
  @Override
  public String get(byte[] data) throws WsSrvException {
    String res = "";
    
    try {
      res = new String(data, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      // Ignore
    }
    
    return res;
  }
  
}
