/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-02-17
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.config;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.osbitools.ws.rest.prj.shared.common.TestPrjMgrBaseConstants;

/**
 * Test wrapper for confiruation properties file
 * 
 */
public class TestPrjWsConfigProperties extends PrjWsConfigProperties {

  @Override
  public URL getGitRemoteUrl() {

    URL url = null;
    try {
      url = new File(TestPrjMgrBaseConstants.REMOTE_REPO_PATH).toURI().toURL();
    } catch (MalformedURLException e) {
      // Do nothing
    }

    return url;
  }
}
