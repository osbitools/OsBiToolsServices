/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-02-10
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.me.shared.config;

import java.util.Map;

import org.slf4j.Logger;

import com.osbitools.ws.me.shared.common.MeSharedConstants;
import com.osbitools.ws.rest.prj.shared.config.AbstractPrjWsConfig;
import com.osbitools.ws.shared.DsConstants;

/**
 * Ws Configuration class for Map Editor REST Service
 * 
 */

public class MeWsConfig extends AbstractPrjWsConfig {

  // Serial Version UID
  private static final long serialVersionUID = 1L;

  public MeWsConfig() {
    super();
  }
  
  public MeWsConfig(String fileName) {
    super(fileName);
  }
  
  public MeWsConfig(String fileName, String homeDir, Logger log) {
    super(fileName, homeDir, log);
  }

  @Override
  public String getHomeSubDir() {
    return DsConstants.DS_DIR;
  }

  @Override
  public Map<String, String[]> getSubDirExtList() {
    return MeSharedConstants.EXT_LIST;
  }

  @Override
  public String getPrefix() {
    return "prj_ws_cfg";
  }
}
