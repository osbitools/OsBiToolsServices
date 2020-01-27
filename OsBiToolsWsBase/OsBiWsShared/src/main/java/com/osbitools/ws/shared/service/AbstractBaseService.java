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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

import com.osbitools.ws.shared.config.BaseWsConfig;

public abstract class AbstractBaseService<T extends BaseWsConfig>
    implements BaseService<T> {

  @Autowired
  @Qualifier("ws_info")
  private WsInfo _info;

  @Autowired
  @Qualifier("log")
  private Logger _log;

  @Override
  public String getWsVersion() {
    return StringUtils.isEmpty(
        getWsInfo().getWsVersion()) ? "local" : 
          getWsInfo().getWsVersion();
  }

  @Override
  public WsInfo getWsInfo() {
    return _info;
  }

  @Override
  public Logger getLogger() {
    return _log;
  }

  @Override
  public String getAppVersion() {
    return getWsInfo().getBuildVersion();
  }
}
