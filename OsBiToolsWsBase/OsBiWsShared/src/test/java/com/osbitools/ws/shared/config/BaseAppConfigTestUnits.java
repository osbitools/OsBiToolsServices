/*
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2016-23-06
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.shared.config;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;

import com.osbitools.ws.shared.common.TestConstants;
import com.osbitools.ws.shared.config.AbstractConfig;

/**
 * Abstract file for Application mode WebService Configuration Tests
 * 
 */

@DirtiesContext
public abstract class BaseAppConfigTestUnits<T extends AbstractConfig> {

  @Autowired
  private ApplicationContext context;
  
  protected abstract String getWsCfgName();
  
  @SuppressWarnings("unchecked")
  protected T getConfig() {
    // Retrieve bean dynamically because auto-wiring by generic class doesn't work
    Object o = context.getBean(getWsCfgName());
    return (T) o;
  }

  protected abstract void checkConfig(T wcfg);

  @BeforeClass
  public static void checkConfigDir() {
    File d = new File(TestConstants.WORK_OSBI_SHARED_DIR);
    assertTrue(d.exists() || !d.exists() && d.mkdir());
  }
  
  @AfterClass
  public static void delWsConfigFile() {
    File f = new File(TestConstants.WORK_WS_CONFIG_FILE);
    assertTrue(!f.exists() || f.exists() && f.delete());
  }
  
  @Test
  public void testWsConfig() {
    T wcfg = getConfig();
    assertNotNull(wcfg);

    checkConfig(wcfg);
  }
  
  @Test
  public void testHomeDir() throws Exception {
    T wcfg = getConfig();
    assertNotNull(wcfg);
    
    assertEquals("Home dir doesn't match", getHomeDir(), wcfg.getHomeDir());
  }
  
  protected String getHomeDir() {
    return TestConstants.WORK_OSBI_SHARED_DIR;
  }
}
