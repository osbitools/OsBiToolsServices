/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-03-10
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.me.shared.mapper;

import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.rest.prj.shared.mapper.AbstractListDeSerializer;
import com.osbitools.ws.shared.DsConstants;
import com.osbitools.ws.shared.binding.ds.DataSetExtList;

/**
 * JSON Deserializer for DataSetExtList class
 * 
 */

public class DataSetExtListDeSerializer
    extends AbstractListDeSerializer<DataSetExtList> {

  // Default Serial Version UID
  private static final long serialVersionUID = 1L;
 
  public DataSetExtListDeSerializer() {
    super(DataSetExtList.class);
  }

  @Override
  protected String[] getListTypes() {
    return DsConstants.DS_MAP_TYPES;
  }

  @Override
  protected String composeClassName(String listType) {
    return DsConstants.BIND_PKG_DS_MAP +
        "." + BaseUtils.ucFirstChar(listType) + "DataSetDescr";
  }

  @Override
  protected String getListFieldName() {
    return "groupDsOrStaticDsOrCsvDs";
  }
  
  @Override
  protected String getListElType(String listType) {
    return listType + "_ds";
  }
}