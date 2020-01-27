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

package com.osbitools.ws.me.shared.test;

import org.junit.BeforeClass;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.osbitools.ws.me.shared.common.MeSharedTestConstants;
import com.osbitools.ws.me.shared.mapper.DataSetExtListDeSerializer;
import com.osbitools.ws.me.shared.mapper.DataSetExtListSerializer;
import com.osbitools.ws.rest.prj.shared.test.AbstractJsonMappingTest;
import com.osbitools.ws.shared.DsConstants;
import com.osbitools.ws.shared.DsMapsTestUtils;
import com.osbitools.ws.shared.TestDsConstants;
import com.osbitools.ws.shared.binding.ds.DataSetDescr;
import com.osbitools.ws.shared.binding.ds.DataSetExtList;

/**
 * Test DataSetDescr mapping from JSON to Object
 * 
 */

public class MeJsonMappingTest extends AbstractJsonMappingTest<DataSetDescr> {

  public MeJsonMappingTest() throws Exception {
    super(DataSetDescr.class);
  }

  @BeforeClass
  public static void initMapper() {
    SimpleModule module = new SimpleModule();
    module.addSerializer(DataSetExtList.class,
        new DataSetExtListSerializer());
    module.addDeserializer(DataSetExtList.class,
        new DataSetExtListDeSerializer());
    MAPPER.registerModule(module);
  }

  protected String getDemoMapJson() {
    return MeSharedTestConstants.MAIN_DEMO_DS;
  }

  @Override
  protected String getBindPkg() {
    return DsConstants.BIND_PKG_DS_MAP;
  }

  @Override
  protected void checkDemoEntity(DataSetDescr entity) {
    DsMapsTestUtils.checkDemoDataSetMap(entity);
  }

  @Override
  protected String getMainDemoPath() {
    return TestDsConstants.DS_MAPS_PACKAGE + "/" +
        TestDsConstants.DS_DEMO_FNAME;
  }
}
