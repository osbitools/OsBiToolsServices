/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-06-19
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.config;

import java.net.MalformedURLException;
import java.util.HashMap;

import org.slf4j.Logger;

import com.osbitools.ws.rest.prj.shared.common.TestPrjMgrBaseConstants;
import com.osbitools.ws.rest.prj.shared.config.AbstractPrjWsConfig;

public class TestPrjMgrWsConfig extends AbstractPrjWsConfig {

  // Serial Version UID
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor
   */
  public TestPrjMgrWsConfig() {
  }
  
  public TestPrjMgrWsConfig(String fileName, Logger log) {
    super(fileName, log);
  }

  public TestPrjMgrWsConfig(String fileName, String homeDir, String debug, String lang,
      String minified, String maxUploadFileSize, String gitRemoteName, String gitRemoteUrl,
      Logger logger) throws MalformedURLException {
    super(fileName, homeDir, debug, lang, minified, maxUploadFileSize, gitRemoteName,
        gitRemoteUrl, logger);
  }

  @Override
  public String getHomeSubDir() {
    return TestPrjMgrBaseConstants.PRJ_DIR;
  }

  @Override
  public String getBaseExt() {
    return TestPrjMgrBaseConstants.PRJ_EXT;
  }

  @Override
  public HashMap<String, String[]> getSubDirExtList() {
    return TestPrjMgrBaseConstants.EXT_LIST;
  }

}
