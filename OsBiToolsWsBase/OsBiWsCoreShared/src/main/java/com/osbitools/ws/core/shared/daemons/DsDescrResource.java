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

package com.osbitools.ws.core.shared.daemons;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.binding.ds.*;

/**
 * Implementation of ResourceInfo for DataSet Description Wrapper around
 * DataSetDescr with indexed columns and Language Set
 *
 */
public class DsDescrResource extends ResourceInfo<DataSetDescr> {

  // Hash set with indexed language columns
  private LangSet _lset;

  // Pointer on first included DataSet Spec
  private DataSetSpec _dss = null;

  // Combined list of DataSet columns. Set null if not defined
  List<ColumnHeader> _columns;

  public DsDescrResource(DataSetDescr resHandle, File f, String prjName) {
    super(resHandle, f, prjName);
  }

  public DsDescrResource(ResourceInfo<DataSetDescr> info) throws WsSrvException {
    super(info);
  }

  public void setLangSet() {
    _lset = new LangSet(getResource());
  }

  public LangSet getLangColumns() {
    return _lset;
  }

  public DataSetSpec getDataSetSpec() throws WsSrvException {
    return _dss;
  }

  public void setDataSetSpec() throws WsSrvException {
    DataSetDescr dsd = getResource();

    if (dsd.getGroupData() != null) {
      _dss = dsd.getGroupData();
    } else if (dsd.getStaticData() != null) {
      _dss = dsd.getStaticData();
    } else if (dsd.getCsvData() != null) {
      _dss = dsd.getCsvData();
    } else if (dsd.getSqlData() != null) {
      _dss = dsd.getSqlData();
    } else if (dsd.getXmlData() != null) {
      _dss = dsd.getXmlData();
    }

    if (_dss == null)
      //-- 136
      throw new WsSrvException(136, "Unknown DataSet Data Description");

    // Set column set after dataset spec defined
    setColumnSet();
  }

  public void setColumnSet() throws WsSrvException {
    Columns cset = _dss.getColumns();
    List<ColumnHeader> columns = new ArrayList<ColumnHeader>();

    if (cset != null)
      for (ColumnHeader ch : cset.getColumn())
        columns.add(ch);

    ExColumns exc = getResource().getExColumns();

    if (exc != null) {
      // Check if auto_inc columns needs to be added
      AutoIncColumns aics = exc.getAutoInc();
      if (aics != null)
        for (AutoIncColumn aic : aics.getColumn())
          columns.add(getColumnHeader(aic));

      // Add calc
      CalcColumns ccs = exc.getCalc();
      if (ccs != null)
        for (CalcColumn cc : ccs.getColumn())
          columns.add(getColumnHeader(cc));
    }

    if (columns.size() > 0)
      _columns = columns;
  }

  public List<ColumnHeader> getColumnSet() {
    return _columns;
  }

  /**
   * Factory method to create ColumnHeader object
   * 
   * @param name
   *          Column name
   * @param jtype
   *          Column Java Type
   * @param onerror
   *          OnError value
   * @return
   */
  private static ColumnHeader getColumnHeader(String name, String jtype, String onerror) {
    ColumnHeader ch = new ColumnHeader();
    ch.setName(name);
    ch.setJavaType(jtype);
    ch.setOnError(onerror);

    return ch;
  }

  /**
   * Factory method to create ColumnHeader object
   * 
   * @param aic
   *          Auto Incremented Column
   * 
   * @return ColumnHeader object
   */
  private static ColumnHeader getColumnHeader(AutoIncColumn aic) {
    return getColumnHeader(aic.getName(), "java.lang.Integer", "");
  }

  /**
   * Factory method to create ColumnHeader object
   * 
   * @param aic
   *          Calculated Column
   * 
   * @return ColumnHeader object
   */
  private static ColumnHeader getColumnHeader(CalcColumn cc) {
    return getColumnHeader(cc.getName(), cc.getJavaType(), cc.getErrorValue());
  }

  @Override
  public void init() throws WsSrvException {
    setLangSet();
    setDataSetSpec();
  }
}
