/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-02-24
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.core.shared.TestCoreSharedAppConfig;
import com.osbitools.ws.core.shared.common.CoreSharedTestConstants;
import com.osbitools.ws.core.shared.service.SqlInfoSevice;
import com.osbitools.ws.core.shared.service.impl.SqlInfoSeviceImpl;
import com.osbitools.ws.base.WsSrvException;

@JdbcTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@ContextConfiguration(
    classes = { TestCoreSharedAppConfig.class, SqlInfoSeviceImpl.class })
public class SqlInfoServiceTest {

  @Autowired
  SqlInfoSevice _sis;

  @Test
  public void testInfoService() throws WsSrvException {
    assertEquals(CoreSharedTestConstants.SQL_INFO_COL_LIST,
        _sis.getColumnList(CoreSharedTestConstants.SQL_COL_LIST));
  }
}
