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
import java.util.Map;

import javax.sql.DataSource;

import com.osbitools.ws.shared.binding.ds.*;
import com.osbitools.ws.core.shared.daemons.DsExtResource;
import com.osbitools.ws.core.shared.producers.SqlDataSetProducer;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.service.RequestLogger;

/**
 * Sql Data Set Processor
 * 
 */
public class SqlDataSetProc
    extends RealDataSetProc<SqlDataSetProducer, SqlDataSetDescr, SqlData> {

  // PooledConnection 
  // private JdbcTemplate _jdbc;
  private DataSource _ds;
  
  // List of values to bind with sql
  private ArrayList<Object> _params = new ArrayList<Object>();

  public SqlDataSetProc(DsExtResource dsExtResource, HashMap<String, Object> requestParameters,
      RequestLogger log) throws WsSrvException {
    super(SqlDataSetProducer.class, dsExtResource, requestParameters, log);
  }

  @Override
  public SqlData getDataSetSpec() {
    DataSetExt dse = getDataSetExt();
    return (dse.getClass().equals(DataSetDescr.class)) ? ((DataSetDescr) dse).getSqlData()
        : ((SqlDataSetDescr) dse).getSqlData();
  }

  @Override
  public void validateRequestParams(Map<String, String[]> params) throws WsSrvException {
    super.validateRequestParams(params);

    // Get connection and check if it initialized
    if (_ds == null)
        //-- 132
        throw new WsSrvException(132, "Database not initialized.");

    // Get parameters if any
    SqlParameters sparams = getDataSetSpec().getSql().getSqlParams();
    if (sparams != null) {
      for (SqlParameter sparam : sparams.getParam())
        _params.add(
            getRequestParameters().get(((RequestParameter) sparam.getReqParam()).getName()));
    }
  }

  public ArrayList<Object> getParameters() {
    return _params;
  }

  public DataSource getDataSource() {
    return _ds;
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.core.shared.proc.AbstractDataSetProc#init(java.lang.Object)
   */
  @Override
  public void init(Object arg) throws WsSrvException {
    if (arg instanceof DataSource)
      _ds = (DataSource) arg;
  }

}
