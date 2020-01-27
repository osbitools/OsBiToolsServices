/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-11-07
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.proc;

import java.util.*;

import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.core.shared.daemons.DsDescrResource;
import com.osbitools.ws.core.shared.daemons.DsExtResource;
import com.osbitools.ws.core.shared.daemons.LsFilesCheck;
import com.osbitools.ws.core.shared.model.*;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.service.RequestLogger;
import com.osbitools.ws.shared.binding.ds.DataSetDescr;

/**
 * Abstract JSON DataSet producer
 * 
 */
public class DataSetProcessor extends AbstractDataSetProc {

  // Handle for active DataSet producer
  AbstractDataSetProc _dsp;

  public DataSetProcessor(DsDescrResource dsr, HashMap<String, Object> requestParameters,
      RequestLogger log) throws WsSrvException {
    super(null, requestParameters, log);

    setDsDescrResource(dsr);
    DataSetDescr dsd = dsr.getResource();

    if (dsd.getGroupData() != null) {
      _dsp = new GroupDataSetProc(new DsExtResource(dsd), requestParameters, getLogger());
    } else if (dsd.getStaticData() != null) {
      _dsp = new StaticDataSetProc(new DsExtResource(dsd), requestParameters, getLogger());
    } else if (dsd.getCsvData() != null) {
      _dsp = new CsvDataSetProc(new DsExtResource(dsd), requestParameters, getLogger());
    } else if (dsd.getSqlData() != null) {
      _dsp = new SqlDataSetProc(new DsExtResource(dsd), requestParameters, getLogger());
    } else if (dsd.getXmlData() != null) {
      _dsp = new XmlDataSetProc(new DsExtResource(dsd), requestParameters, getLogger());
    }

    if (_dsp == null)
      //-- 105
      throw new WsSrvException(105, "DataSet processor is not defined");

    _dsp.setDsDescrResource(dsr);
    setDsExtResource(new DsExtResource(dsd));
  }

  @Override
      boolean checkComplex() throws WsSrvException {
    return _dsp.checkComplex() || super.checkComplex();
  }

  @Override
  public void initComplex() throws WsSrvException {
    super.initComplex();

    if (isComplex())
      // Propagate positive complex flag
      _dsp.setComplex(true);
  }

  @Override
  public void validateRequestParams(Map<String, String[]> params) throws WsSrvException {
    _dsp.validateRequestParams(params);
  }

  @Override
  public DataSet readDataSet(String name, String lang, TraceRecorder trace, List<String> warn,
      LsFilesCheck lcheck, CoreWsConfig cfg) throws WsSrvException {
    return _dsp.readDataSet(name, lang, trace, warn, lcheck, cfg);
  }

  public AbstractDataSetProc getDataSetProc() {
    return _dsp;
  }

}
