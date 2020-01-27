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

package com.osbitools.ws.wp.shared.mapping;

import org.junit.BeforeClass;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.osbitools.ws.rest.prj.shared.test.AbstractJsonMappingTest;
import com.osbitools.ws.wp.shared.WebPageTestUtils;
import com.osbitools.ws.wp.shared.WpConstants;
import com.osbitools.ws.wp.shared.WpTestConstants;
import com.osbitools.ws.wp.shared.binding.*;
import com.osbitools.ws.wp.shared.mapper.WebWidgetsDeSerializer;
import com.osbitools.ws.wp.shared.mapper.WebWidgetsSerializer;

/**
 * Test DataSetDescr mapping from JSON to Object
 * 
 */

public class WpJsonMappingTest extends AbstractJsonMappingTest<WebPage> {

  public static final String WP_MAIN_DEMO_JSON = "{" +
      "\"panels\":{" +
        "\"panel\":[" +
          "{\"wwg_list\":[" +
            "{\"wwg_container\":{\"props\":{\"prop\":[{\"name\":\"id\",\"value\":\"wp_test\"}]}," +
              "\"uid\":1,\"idx\":0,\"class_name\":\"com.osbitools.containers.tab_box\"," +
              "\"wwg_list\":[" +
                "{\"wwg_chart\":{" +
                  "\"props\":{" +
                    "\"prop\":[" +
                      "{\"name\":\"height\",\"value\":\"200\"}," +
                      "{\"name\":\"width\",\"value\":\"200\"}" +
                    "]" +
                  "}," +
                  "\"uid\":2,\"idx\":0,\"class_name\":\"\"}" +
                "}" +
              "]}" +
            "}" +
          "]}" +
        "]" +
      "}," +
      "\"descr\":\"Test Web Page #1\"," +
      "\"copyright\":\"OsBiTools\"," +
      "\"ver_max\":1," +
      "\"ver_min\":0," +
      "\"inc\":2" +
    "}";
  
  public WpJsonMappingTest() throws Exception {
    super(WebPage.class);
  }

  @BeforeClass
  public static void initMapper() {
    SimpleModule module = new SimpleModule();
    module.addSerializer(WebWidgets.class, new WebWidgetsSerializer());
    module.addDeserializer(WebWidgets.class, new WebWidgetsDeSerializer());
    MAPPER.registerModule(module);
  }

  protected String getDemoMapJson() {
    return WP_MAIN_DEMO_JSON;
  }

  @Override
  protected String getBindPkg() {
    return WpConstants.BIND_PKG_WEB_PAGE;
  }

  @Override
  protected void checkDemoEntity(WebPage entity) {
    WebPageTestUtils.checkDemoWebPage(entity);
  }

  @Override
  protected String getMainDemoPath() {
    return WpTestConstants.WEB_PAGES_PACKAGE + "/" +
        WpTestConstants.WP_DEMO_FNAME;
  }
}
