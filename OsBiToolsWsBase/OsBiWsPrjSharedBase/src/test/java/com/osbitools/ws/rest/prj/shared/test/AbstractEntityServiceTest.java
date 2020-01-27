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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.osbitools.ws.rest.prj.shared.config.AbstractPrjWsConfig;
import com.osbitools.ws.rest.prj.shared.dto.EntityDto;
import com.osbitools.ws.rest.prj.shared.service.EntityService;
import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.base.WsSrvException;

/**
 * Basic Web Tests
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest (value = { "spring.config.name=test" })
public abstract class AbstractEntityServiceTest<T> {

  @Autowired
  private Logger _log;

  @Autowired
  @Qualifier("prj_ws_cfg")
  private AbstractPrjWsConfig _cfg;
  
  protected final static ObjectMapper MAPPER = new ObjectMapper();

  static {
    // Basic mapper configuration
    MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    MAPPER.setSerializationInclusion(Include.NON_NULL);
  }

  @Autowired
  private EntityService<T> _es;

  protected abstract String[][] getDemoEntityList();

  protected abstract String[][] getTestEntityList();

  @Test
  public void testJsonDemoEntityMapper()
      throws WsSrvException, JsonProcessingException {
    for (String[] fspec : getDemoEntityList())
      testEntity(fspec);
  }

  @Test
  public void testJsonTestEntityMapper()
      throws WsSrvException, JsonProcessingException {
    for (String[] fspec : getTestEntityList())
      testEntity(fspec);
  }

  @Test
  public void testEntityUploadDownload() throws Exception {
    // Create temp upload folder if doesn't exists
    final String dname = "tmp";
    String tdir = _cfg.getBaseDir() + File.separator + dname;
    File f = new File(tdir);
    if (f.exists())
      BaseUtils.delDirRecurse(f, false);
    else {
      if (!f.mkdir())
        fail("Unable create temp upload directory " + f.getAbsolutePath());
    }
    
    final String fname = dname + ".test." + _cfg.getBaseExt();
    
    // Upload first demo file
    String[] fspec = getDemoEntityList()[0];
    testEntity(fname, _es.createEntity(fname, _es.read(fspec[0]), false), fspec[1]);
    
    // Upload second demo file on same name
    fspec = getDemoEntityList()[1];
    testEntity(fname, _es.createEntity(fname, _es.read(fspec[0]), true), fspec[1]);
    
    // Delete test folder after test
    BaseUtils.delDirRecurse(f, true);
  }
  
  private void testEntity(String[] fspec)
      throws WsSrvException, JsonProcessingException {
    String fname = fspec[0];
    _log.debug("Testing " + fname);

    testEntity(fname, _es.getEntity(fname), fspec[1]);
  }
  
  private void testEntity(String fname, EntityDto<T> dto, String text)
      throws WsSrvException, JsonProcessingException {

    // Convert Object -> Json and compare
    String json = MAPPER.writeValueAsString(dto.getEntity());
    assertEquals(fname + " converting XML -> JSON doesn't match", text,
        json);
  }
}
