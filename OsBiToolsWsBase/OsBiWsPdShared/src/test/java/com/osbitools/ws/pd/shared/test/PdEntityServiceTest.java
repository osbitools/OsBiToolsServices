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

package com.osbitools.ws.pd.shared.test;

import org.junit.BeforeClass;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.osbitools.ws.pd.shared.common.PdSharedTestConstants;
import com.osbitools.ws.wp.shared.mapper.WebWidgetsSerializer;
import com.osbitools.ws.rest.prj.shared.test.AbstractEntityServiceTest;
import com.osbitools.ws.wp.shared.binding.WebPage;
import com.osbitools.ws.wp.shared.binding.WebWidgets;

public class PdEntityServiceTest
    extends AbstractEntityServiceTest<WebPage> {

  @BeforeClass
  public static void initMapper() {
    SimpleModule module = new SimpleModule();
    module.addSerializer(WebWidgets.class, new WebWidgetsSerializer());
    // module.addDeserializer(WebWidget.class, new WebWidgetDeSerializer());
    MAPPER.registerModule(module);
  }
  
  @Override
  protected String[][] getDemoEntityList() {
    return PdSharedTestConstants.DEMO_WEB_PAGES;
  }
  
  @Override
  protected String[][] getTestEntityList() {
    return PdSharedTestConstants.TEST_WEB_PAGES;
  }
}
