/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2016-06-09
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.service;

import java.util.Map;

import com.osbitools.ws.core.shared.model.DataSet;
import com.osbitools.ws.base.WsSrvException;

public interface CoreDsProducerSevice {

  /**
   * Retrieve DataSet
   * 
   * @param mname DataSetDescription file name
   * @param lang Required language
   * @param trace Trace flag
   * @param params List of raw HTTP parameters
   * @return DataSet Object
   * 
   * @throws WsSrvException
   */
  public DataSet getDataSet(String mname, String lang, String trace,
      Map<String, String[]> params) throws WsSrvException;

}
