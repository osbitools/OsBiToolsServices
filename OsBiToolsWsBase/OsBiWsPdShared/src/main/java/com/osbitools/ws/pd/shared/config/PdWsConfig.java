/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-08-04
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.pd.shared.config;

import java.util.Map;

import org.slf4j.Logger;

import com.osbitools.ws.pd.shared.common.PdSharedConstants;
import com.osbitools.ws.rest.prj.shared.config.AbstractPrjWsConfig;
import com.osbitools.ws.wp.shared.WpConstants;

/**
 * Ws Configuration class for Page Designer REST Service
 * 
 */

public class PdWsConfig extends AbstractPrjWsConfig {

  // Serial Version UID
  private static final long serialVersionUID = 1L;

  public PdWsConfig() {
    super();
  }
  
  public PdWsConfig(String fileName) {
    super(fileName);
  }
  
  public PdWsConfig(String fileName, String homeDir, Logger log) {
    super(fileName, homeDir, log);
  }

  @Override
  public String getHomeSubDir() {
    return WpConstants.WP_DIR;
  }

  @Override
  public Map<String, String[]> getSubDirExtList() {
    return PdSharedConstants.EXT_LIST;
  }

  @Override
  public String getPrefix() {
    return "prj_ws_cfg";
  }
}
