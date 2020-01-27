/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2015-03-02
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.proc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.osbitools.ws.core.shared.daemons.DsExtResource;
import com.osbitools.ws.core.shared.producers.GroupDataSetProducer;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.service.RequestLogger;
import com.osbitools.ws.shared.binding.ds.*;

/**
 * Group DataSet Processor
 * 
 */
public class GroupDataSetProc
    extends RealDataSetProc<GroupDataSetProducer, GroupDataSetDescr, GroupData> {

  // List of included resources
  ArrayList<DsExtResource> _list = new ArrayList<DsExtResource>();

  public GroupDataSetProc(DsExtResource dsExtResource,
      HashMap<String, Object> requestParameters, RequestLogger log) throws WsSrvException {
    super(GroupDataSetProducer.class, dsExtResource, requestParameters, log);

    for (DataSetExt ds : getDataSetSpec().getDsList().getGroupDsOrStaticDsOrCsvDs())
      _list.add(new DsExtResource(ds));
  }

  @Override
  public GroupData getDataSetSpec() {
    DataSetExt dse = getDataSetExt();
    return (dse.getClass().equals(DataSetDescr.class)) ? ((DataSetDescr) dse).getGroupData()
        : ((GroupDataSetDescr) dse).getGroupData();
  }

  /**
   * Traverse down and rebuild DataSetProc tree for each DataSet Spec
   */
  @Override
      boolean checkComplex() throws WsSrvException {
    boolean res = super.checkComplex();

    // Traverse down
    for (DsExtResource dser : _list) {
      AbstractDataSetProc dsp = null;

      DataSetExt dse = dser.getDataSetExt();

      if (dse.getClass().equals(GroupDataSetDescr.class)) {
        dsp = new GroupDataSetProc(dser, getRequestParameters(), getLogger());
      } else if (dse.getClass().equals(StaticDataSetDescr.class)) {
        dsp = new StaticDataSetProc(dser, getRequestParameters(), getLogger());
      } else if (dse.getClass().equals(SqlDataSetDescr.class)) {
        dsp = new SqlDataSetProc(dser, getRequestParameters(), getLogger());
      } else if (dse.getClass().equals(CsvDataSetDescr.class)) {
        dsp = new CsvDataSetProc(dser, getRequestParameters(), getLogger());
      }

      if (dsp == null)
        //-- 103
        throw new WsSrvException(103, "Unknown dataset processor",
            "Unknown DataSetExt type " + dse.getClass().toString());

      // Order is important
      // 1.
      dsp.setDsDescrResource(getDsDescrResource());

      // 2.
      dsp.initComplex();
      dser.setDataSetProc(dsp);

      res |= dsp.isComplex();
    }

    return res;
  }

  @Override
  public void setComplex(boolean complex) {
    super.setComplex(complex);

    // Traverse down
    for (DsExtResource dser : _list)
      dser.getDataSetProc().setComplex(complex);
  }

  @Override
  public void validateRequestParams(Map<String, String[]> params) throws WsSrvException {
    super.validateRequestParams(params);

    // Traverse down
    for (DsExtResource dser : _list)
      dser.getDataSetProc().validateRequestParams(params);
  }

  public ArrayList<DsExtResource> getDsExtList() {
    return _list;
  }
  
  /* (non-Javadoc)
   * @see com.osbitools.ws.core.shared.proc.RealDataSetProc#init(java.lang.Object)
   */
  @Override
  public void init(Object arg) throws WsSrvException {
    super.init(arg);
    
    // Traverse down
    for (DsExtResource dser : _list)
      dser.getDataSetProc().init(arg);;
  }
}
