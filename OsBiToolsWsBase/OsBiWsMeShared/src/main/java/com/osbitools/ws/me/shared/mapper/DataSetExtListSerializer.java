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

package com.osbitools.ws.me.shared.mapper;

import com.osbitools.ws.rest.prj.shared.mapper.AbstracttListSerializer;
import com.osbitools.ws.shared.binding.ds.DataSetExt;
import com.osbitools.ws.shared.binding.ds.DataSetExtList;

/**
 * JSON Serializer for DataSetExtList class
 * 
 */

public class DataSetExtListSerializer
    extends AbstracttListSerializer<DataSetExtList, DataSetExt> {

  // Default Serial Version UID
  private static final long serialVersionUID = 1L;

  private static final int SUFFIX_LEN = "DataSetDescr".length();

  public DataSetExtListSerializer() {
    super(DataSetExtList.class);
  }

  protected DataSetExtListSerializer(Class<DataSetExtList> t, boolean dummy) {
    super(t, dummy);
  }

  @Override
  protected String getListFieldName() {
    return "groupDsOrStaticDsOrCsvDs";
  }

  @Override
  protected String getFieldName(DataSetExt item) {
    String sname = item.getClass().getSimpleName();
    return sname.substring(0, sname.length() - SUFFIX_LEN).toLowerCase() + "_ds";
  }
}
