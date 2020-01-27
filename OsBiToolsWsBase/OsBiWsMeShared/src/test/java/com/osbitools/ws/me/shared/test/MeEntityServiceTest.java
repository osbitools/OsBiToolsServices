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
import com.osbitools.ws.rest.prj.shared.test.AbstractEntityServiceTest;
import com.osbitools.ws.shared.binding.ds.DataSetDescr;
import com.osbitools.ws.shared.binding.ds.DataSetExtList;

public class MeEntityServiceTest
    extends AbstractEntityServiceTest<DataSetDescr> {

  @BeforeClass
  public static void initMapper() {
    SimpleModule module = new SimpleModule();
    module.addSerializer(DataSetExtList.class, new DataSetExtListSerializer());
    module.addDeserializer(DataSetExtList.class,
        new DataSetExtListDeSerializer());
    MAPPER.registerModule(module);
  }
  
  @Override
  protected String[][] getDemoEntityList() {
    return MeSharedTestConstants.DS_DEMO_MAPS;
  }
  
  @Override
  protected String[][] getTestEntityList() {
    return MeSharedTestConstants.DS_TEST_MAPS;
  }
}
