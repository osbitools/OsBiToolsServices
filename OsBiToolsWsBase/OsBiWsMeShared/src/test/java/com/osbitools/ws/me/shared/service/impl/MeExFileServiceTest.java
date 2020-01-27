/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-03-10
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.me.shared.service.impl;

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

import com.osbitools.ws.me.shared.MeSharedTestAppConfig;
import com.osbitools.ws.rest.prj.shared.service.ExFileService;
import com.osbitools.ws.shared.Constants;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.base.dto.ColumnDto;
import com.osbitools.ws.shared.base.dto.ColumnListDto;

/**
 * Test ExFile Service
 * 
 */
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@ContextConfiguration(classes = { MeSharedTestAppConfig.class, MeExFileServiceImpl.class })
public class MeExFileServiceTest {

  @Autowired
  private ExFileService<ColumnListDto> _mex;

  @Test
  public void testCsvPostCreate() throws WsSrvException {
    // Create empty params
    Map<String, String> params = new HashMap<>();

    // Parse standard csv file
    testDto(_mex.postCreate(new File("src/test/csv/test_headers.csv"), params), "HEADER", 4);

    params.put(Constants.PARAM_VAL_SUFIX + "delim", ";");
    params.put(Constants.PARAM_VAL_SUFIX + "col_first_row", "false");
    testDto(_mex.postCreate(new File("src/test/csv/test_plain.csv"), params), "COL", 4);
  }

  private void testDto(ColumnListDto dto, String prefix, int size) {
    assertNotNull(dto);
    ColumnListDto expected = new ColumnListDto(size);

    for (int i = 1; i <= size; i++)
      expected.addColumn(new ColumnDto(prefix + i, String.class.getName()));

    assertEquals(expected, dto);
  }
}
