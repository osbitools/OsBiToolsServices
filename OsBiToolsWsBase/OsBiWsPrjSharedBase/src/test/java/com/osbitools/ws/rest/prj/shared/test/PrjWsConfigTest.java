/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2016-30-05
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.prj.shared.test;

import static org.junit.Assert.fail;

import java.net.MalformedURLException;

import com.osbitools.ws.rest.prj.shared.config.AbstractPrjWsConfig;
import com.osbitools.ws.rest.prj.shared.config.TestPrjMgrWsConfig;

public class PrjWsConfigTest extends AbstractPrjWsConfigTest<AbstractPrjWsConfig> {

  public PrjWsConfigTest() throws MalformedURLException {
    super();
  }

  @Override
  protected AbstractPrjWsConfig getNewConfigBean(String fileName) {
    return new TestPrjMgrWsConfig(fileName, getLogger());
  }

  @Override
  protected AbstractPrjWsConfig getExternalConfigBean(String fileName, String dir) {
    AbstractPrjWsConfig conf = null;
    try {
      conf = new TestPrjMgrWsConfig(fileName, dir, debug1.toString(), lang1, 
          minified1.toString(), maxUploadFileSize1.toString(),
          gitRemoteName1, url1.toString(), getLogger());
    } catch (MalformedURLException e) {
      fail("Error creating ExternalConfigBean - " + e.getMessage());
    }

    return conf;
  }

}
