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

package com.osbitools.ws.shared.web.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

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
import com.osbitools.ws.shared.web.service.BaseWebService;

/**
 * Basic Web Tests
 *
 */

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE,
    value = { "spring.config.name=test" })
public class BaseWebServiceTest {

  @Autowired
  private BaseWebService _wsrv;
  
  @Before
  public void clearConfigDir() throws IOException {
    File dconfig = new File(TestConstants.WORK_OSBI_SHARED_DIR);
    if (dconfig.exists())
      BaseUtils.delDirRecurse(dconfig);

    assertFalse(dconfig.exists());
  }

  @Test
  public void testBaseWebService() {
    Map<String, String> config = _wsrv.getWebConfig("param1,param2,param3");
    
    assertNotNull(config);

    assertEquals("one", config.get("param1"));
    assertEquals("two", config.get("param2"));
    assertEquals("three", config.get("param3"));
  }

}