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

package com.osbitools.ws.wp.shared.mapper;

import com.osbitools.ws.rest.prj.shared.mapper.AbstracttListSerializer;
import com.osbitools.ws.wp.shared.binding.WebWidget;
import com.osbitools.ws.wp.shared.binding.WebWidgets;

/**
 * JSON Serializer for ComponentPanel class
 * 
 */

public class WebWidgetsSerializer
    extends AbstracttListSerializer<WebWidgets, WebWidget> {

  // Default Serial Version UID
  private static final long serialVersionUID = 1L;

  private static final int PREFIX_LEN = "WebWidget".length();

  public WebWidgetsSerializer() {
    super(WebWidgets.class, true);
  }

  @Override
  protected String getListFieldName() {
    return "wwgContainerOrWwgChartOrWwgControl";
  }

  @Override
  protected String getFieldName(WebWidget item) {
    String sname = item.getClass().getSimpleName();
    return "wwg_" + sname.substring(PREFIX_LEN,
        sname.length()).toLowerCase();
  }
}
