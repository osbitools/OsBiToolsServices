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

package com.osbitools.ws.rest.prj.shared.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.shared.common.TestConstants;
import com.osbitools.ws.shared.config.BaseAppWsConfig;

/**
 * Basic Web Tests
 * 
 */

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE,
    value = { "spring.config.name=test" })
public class AppWsConfigTest {

  @Autowired
  BaseAppWsConfig _wcfg;

  @Before
  public void clearConfigDir() throws IOException {
    File dconfig = new File(TestConstants.WORK_OSBI_SHARED_DIR);
    if (dconfig.exists())
      BaseUtils.delDirRecurse(dconfig);

    assertFalse(dconfig.exists());
  }

  @Test
  public void testWsConfig() {
    assertNotNull(_wcfg);

    assertTrue(_wcfg.getDebug());
    assertEquals("en", _wcfg.getLang());
  }
}