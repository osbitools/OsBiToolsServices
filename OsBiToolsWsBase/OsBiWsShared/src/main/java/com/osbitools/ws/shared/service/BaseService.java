/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2016-26-05
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared.service;

import org.slf4j.Logger;

import com.osbitools.ws.shared.config.AbstractConfig;

public interface BaseService<T extends AbstractConfig> {

  public String getAppVersion();
  
  T getWsConfig();
  
  public WsInfo getWsInfo();

  public Logger getLogger();
  
  public String getWsVersion();
}
