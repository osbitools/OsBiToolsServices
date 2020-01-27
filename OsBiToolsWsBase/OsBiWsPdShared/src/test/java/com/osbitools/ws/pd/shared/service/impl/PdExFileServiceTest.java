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

package com.osbitools.ws.pd.shared.service.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.pd.shared.PdSharedTestAppConfig;
import com.osbitools.ws.pd.shared.common.PdSharedTestConstants;
import com.osbitools.ws.pd.shared.dto.IconFileDto;
import com.osbitools.ws.pd.shared.service.impl.PdExFileServiceImpl;
import com.osbitools.ws.rest.prj.shared.service.ExFileService;
import com.osbitools.ws.base.WsSrvException;

/**
 * Test ExFile Service
 * 
 */
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@ContextConfiguration(classes = { PdSharedTestAppConfig.class, PdExFileServiceImpl.class })
public class PdExFileServiceTest {

  @Autowired
  private ExFileService<IconFileDto> _efs;

  @Test
  public void testPngPostCreate() throws WsSrvException {
    // Create empty params
    Map<String, String> params = new HashMap<>();

    String fname = "gear.png";
    IconFileDto dto = _efs.postCreate(new File("src/test/images/" + fname), params);
    assertEquals(fname + " file doesn't match", PdSharedTestConstants.GEAR_PNG, 
        "{\"base64\":\"" + dto.getBase64() + "\"}");
  }
}
