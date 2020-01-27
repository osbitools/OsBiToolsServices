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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import com.osbitools.ws.core.shared.common.CoreConstants;
import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.core.shared.daemons.DsExtResource;
import com.osbitools.ws.core.shared.daemons.LsFilesCheck;
import com.osbitools.ws.core.shared.model.*;
import com.osbitools.ws.core.shared.producers.AbstractDataSetProducer;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.service.RequestLogger;
import com.osbitools.ws.shared.binding.ds.*;

/**
 * Abstract class for DataSet that reads real data (not group based)
 * 
 */
public abstract class RealDataSetProc<T1 extends AbstractDataSetProducer<T3>, T2 extends DataSetExt, T3 extends DataSetSpec>
    extends AbstractDataSetProc {

  private Class<T1> _pt1;

  public RealDataSetProc(Class<T1> dsProducerType, DsExtResource dsExtResource,
      HashMap<String, Object> requestParameters, RequestLogger log) throws WsSrvException {
    super(dsExtResource, requestParameters, log);
    _pt1 = dsProducerType;
  }

  @Override
  public DataSet readDataSet(String name, String lang, TraceRecorder trace, List<String> warn,
      LsFilesCheck lcheck, CoreWsConfig cfg) throws WsSrvException {

    DataSet ds = null;
    T1 producer = getProducer(lcheck, cfg);

    try {
      trace.record(CoreConstants.TRACE_DS_PROC_START);

      ds = producer.read(name, lang, trace, warn);
      trace.record(CoreConstants.TRACE_DS_READ_DATA);

      if (isComplex()) {
        DataSetExt dse = getDataSetExt();
        ConditionFilter filter = dse.getFilter();
        SortGroup sort = dse.getSortByGrp();
        if (filter != null) {
          ds.filter(filter);
          trace.record(CoreConstants.TRACE_FILTER_COMPLETED);
        }

        if (sort != null) {
          ds.sort(sort.getSortBy());
          trace.record(CoreConstants.TRACE_SORT_COMPLETED);
        }

        trace.record(CoreConstants.TRACE_DS_PROC_END);
      }

      return ds;
    } catch (WsSrvException e) {

      // On Error processing. 
      Columns columns = getDataSetSpec().getColumns();
      if (columns == null)
        throw e;

      ds = producer.makeNewDataSet(lang, trace, warn);
      ds.startData();

      for (ColumnHeader col : columns.getColumn())
        ds.addColumn(col.getName(), col.getJavaType());

      ds.endColumn();

      ds.startRecord();

      for (ColumnHeader col : columns.getColumn()) {
        Object value = ds.createValue(col.getName(), col.getOnError());
        ds.addValue(col.getName(), value);
      }

      ds.endRecordError();
      ds.endData();

      e.setJsonObj(ds);

      throw e;
    }
  }

  abstract public T3 getDataSetSpec();

  @SuppressWarnings("unchecked")
  public T1 getProducer(LsFilesCheck lcheck, CoreWsConfig cfg) throws WsSrvException {
    Constructor<T1> c = (Constructor<T1>) _pt1.getConstructors()[0];
    T1 res = null;
    try {
      res = c.newInstance(this, lcheck, cfg);
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException e) {
      //-- 104
      throw new WsSrvException(104,
          "Unable instantinate DataSet Producer of type: " + _pt1.getName(), e);
    }

    return res;
  }
}
