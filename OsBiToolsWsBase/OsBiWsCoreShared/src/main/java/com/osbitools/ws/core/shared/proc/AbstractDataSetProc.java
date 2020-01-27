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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.core.shared.common.*;
import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.core.shared.daemons.*;
import com.osbitools.ws.core.shared.model.*;
import com.osbitools.ws.shared.binding.ds.*;
import com.osbitools.ws.shared.service.RequestLogger;

/**
 * Abstract class to handle DataSetExt processing including sorting and
 * filtering
 * 
 */
public abstract class AbstractDataSetProc {
  // Logger
  RequestLogger _rlog;

  // DataSetExtension handle
  private DsExtResource _dse;

  // Complex flag
  private boolean _complex;

  // Extrac columns flag
  private boolean _exc;

  // Calculated columns
  private List<CalcColumn> _calc;

  // AutoInc columns
  private List<AutoIncColumn> _auto;

  // List of parsed request parameters
  private HashMap<String, Object> _rparams;

  // Configurable columns
  List<ColumnHeader> _columns;

  // Pointer on top resource file
  private DsDescrResource _dsr;

  public AbstractDataSetProc(DsExtResource dsExtResource,
      HashMap<String, Object> requestParameters, RequestLogger rlog) throws WsSrvException {
    _rlog = rlog;
    _dse = dsExtResource;
    _rparams = requestParameters;

    ExColumns exc = (_dse != null) ? _dse.getDataSetExt().getExColumns() : null;
    _exc = (exc != null);

    if (_exc) {
      CalcColumns calc = exc.getCalc();
      if (calc != null)
        _calc = calc.getColumn();

      AutoIncColumns auto = exc.getAutoInc();
      if (auto != null)
        _auto = auto.getColumn();
    }
  }

  /**
   * Read DataSet
   * 
   * @param name
   *          Name of spec file
   * @param lang
   *          Required language
   * @param trace
   *          Trace handler
   * @param warn
   *          Warn handler
   * @return DataSet
   * @throws WsSrvException
   */
  public abstract DataSet readDataSet(String name, String lang, TraceRecorder trace,
      List<String> warn, LsFilesCheck lcheck, CoreWsConfig cfg) throws WsSrvException;

  /**
   * Initialize dataset with custom objects
   * 
   * @param args Custom list of objects
   * @throws WsSrvException 
   */
  public void init(Object arg) throws WsSrvException {
    // By default do nothing
  }
  
  public void validateRequestParams(Map<String, String[]> params) throws WsSrvException {
    // Do nothing
  }

  public DataSet read(String name, Map<String, String[]> params, String lang,
      TraceRecorder trace, ArrayList<String> warn, LsFilesCheck lcheck, CoreWsConfig cfg)
          throws WsSrvException {
    trace.record(CoreConstants.TRACE_START_PROC);

    // 1. Validate request first
    validateRequestParams(params);

    // 2. Read dataset
    DataSet ds = readDataSet(name, lang, trace, warn, lcheck, cfg);
    trace.record(CoreConstants.TRACE_READ_DATA);

    if (_complex) {
      DataSetExt dse = getDataSetExt();
      ConditionFilter filter = dse.getFilter();
      SortGroup sort = dse.getSortByGrp();

      // Filter
      if (filter != null) {
        ds.filter(filter);
        trace.record(CoreConstants.TRACE_FILTER_COMPLETED);
      }

      // Sort
      if (sort != null) {
        ds.sort(sort.getSortBy());
        trace.record(CoreConstants.TRACE_SORT_COMPLETED);
      }
    }

    trace.record(CoreConstants.TRACE_PROC_END);
    return ds;
  }

  public DsExtResource getDsExtResource() {
    return _dse;
  }

  void setDsExtResource(DsExtResource dsExtRes) throws WsSrvException {
    _dse = dsExtRes;

    // Initiate complex flag check after DataSet set/changed
    initComplex();
  }

  void setColumns(List<ColumnHeader> columns) {
    _columns = columns;
  }

  public DataSetExt getDataSetExt() {
    return _dse.getDataSetExt();
  }

  boolean checkComplex() throws WsSrvException {
    if (_dse == null)
      return false;

    DataSetExt dse = _dse.getDataSetExt();
    return !(dse.getFilter() == null && dse.getSortByGrp() == null);
  }

  public boolean isComplex() {
    return _complex;
  }

  public void initComplex() throws WsSrvException {
    if (_dse != null)
      _complex = checkComplex();
  }

  public void setComplex(boolean complex) {
    _complex = complex;
  }

  LangSet getLangColumns() {
    return _dse.getLangColumns();
  }

  public boolean hasExtraColumns() {
    return _exc;
  }

  public List<AutoIncColumn> getAutoIncColumns() {
    return _auto;
  }

  public List<CalcColumn> getCalcColumns() {
    return _calc;
  }

  public HashMap<String, Object> getRequestParameters() {
    return _rparams;
  }

  public DsDescrResource getDsDescrResource() {
    return _dsr;
  }

  public void setDsDescrResource(DsDescrResource dsr) {
    _dsr = dsr;
  }

  public RequestLogger getLogger() {
    return _rlog;
  }

  public void error(int errId, String[] errDetails) {
    _rlog.error(errId, errDetails);
  }

  public void debug(String msg) {
    _rlog.debug(msg);
  }
}
