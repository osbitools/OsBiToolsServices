/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-08-04
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.wp.shared.mapper;

import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.rest.prj.shared.mapper.AbstractListDeSerializer;
import com.osbitools.ws.wp.shared.WpConstants;
import com.osbitools.ws.wp.shared.binding.WebWidgets;

/**
 * JSON Deserializer for ComponentPanel class
 * 
 */

public class WebWidgetsDeSerializer
    extends AbstractListDeSerializer<WebWidgets> {

  // Default Serial Version UID
  private static final long serialVersionUID = 1L;
  
  public WebWidgetsDeSerializer() {
    super(WebWidgets.class);
  }

  @Override
  protected String[] getListTypes() {
    return WpConstants.WWG_TYPES;
  }
  
  @Override
  protected String composeClassName(String listType) {
    return WpConstants.BIND_PKG_WEB_PAGE + ".WebWidget" +
                                BaseUtils.ucFirstChar(listType);
  }

  @Override
  protected String getListFieldName() {
    return "wwgContainerOrWwgChartOrWwgControl";
  }

  @Override
  protected String getListElType(String listType) {
    return "wwg_" + listType;
  }
}