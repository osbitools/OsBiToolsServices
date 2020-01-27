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

package com.osbitools.ws.core.shared.producers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.core.shared.common.CoreConstants;
import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.core.shared.daemons.LsFilesCheck;
import com.osbitools.ws.core.shared.model.*;
import com.osbitools.ws.core.shared.proc.SqlDataSetProc;
import com.osbitools.ws.shared.binding.ds.SqlData;

/**
 * Sql DataSet Producer
 * 
 */
public class SqlDataSetProducer extends AbstractDataSetProducer<SqlData> {

  public SqlDataSetProducer(SqlDataSetProc dsProc, LsFilesCheck lcheck, CoreWsConfig cfg) {
    super(dsProc, lcheck, cfg);
  }

  @Override
  public DataSet read(String name, String lang, TraceRecorder trace, List<String> warn)
      throws WsSrvException {
    DataSet d = null;
    SqlDataSetProc dsp = (SqlDataSetProc) getDataSetProc();

    DataSource ds = dsp.getDataSource();
    
    trace.record(CoreConstants.TRACE_CONNECTED_TO_DB);

    String sql = getDataSetSpec().getSql().getSqlText();

    // Strip front and back special characters
    sql = sql.replaceAll("^[\\n|\\t]*", "").replaceAll("[\\n|\\t]*$", "")
        .replaceAll("\\n*", "").replaceAll("\\s{2,}", " ");
    debug("Executing SQL: " + sql);
    ResultSet res = null;
    PreparedStatement stmt = null;
    Connection conn;
    try {
      conn = DataSourceUtils.getConnection(ds);
    } catch (CannotGetJdbcConnectionException e) {
      //-- 125
      throw new WsSrvException(125, "Error retrieving database connection", e);
    } 
    
    try {     
      // Prepare statement
      int pnum;
      ArrayList<Integer> ptypes = new ArrayList<Integer>();

      try {
        stmt = conn.prepareStatement(sql);
        pnum = stmt.getParameterMetaData().getParameterCount();
        for (int i = 0; i < pnum; i++)
          ptypes.add(stmt.getParameterMetaData().getParameterType(i + 1));
      } catch (SQLException e) {
        //-- 122
        throw new WsSrvException(122, sql, e);
      }

      // Check if all parameters in place
      ArrayList<Object> params = dsp.getParameters();
      int psize = params.size();
      if (psize != pnum)
        //-- 123
        throw new WsSrvException(123, "", "Number of expected sql parameters " + pnum +
            " not equal the number of xml configured parameters " + psize);

      // Prepare sql
      for (int i = 0; i < pnum; i++) {
        int idx = i + 1;
        Object value = params.get(i);
        boolean fnull = value == null;
        debug("P #" + idx + ": " + (fnull ? "null" : value.toString()));

        try {
          if (fnull)
            stmt.setNull(idx, ptypes.get(idx - 1));
          else
            stmt.setObject(idx, value);
        } catch (SQLException e) {
          //-- 124
          throw new WsSrvException(124,
              "Error preparing #" + idx + " sql parameter with value " + value.toString());
        }
      }

      trace.record(CoreConstants.TRACE_SQL_PREP);

      // Execute sql
      try {
        res = stmt.executeQuery();
      } catch (SQLException e) {
        // Collect parameters
        String[] err = new String[params.size() + 2];
        err[0] = sql;
        err[1] = e.getMessage();
        for (int i = 0; i < psize; i++) {
          Object pval = params.get(i);
          err[i + 2] = "PARAM #" + (i + 1) + ":" + (pval != null ? pval.toString() : pval);
        }
        //-- 133
        throw new WsSrvException(133, err);
      }

      trace.record(CoreConstants.TRACE_SQL_EXEC);

      d = makeNewDataSet(lang, trace, warn);
      d.startData();

      // Get columns
      int clen;
      ResultSetMetaData rmdata;
      String[] columns;
      try {
        rmdata = res.getMetaData();
        clen = rmdata.getColumnCount();
        columns = new String[clen];

        for (int i = 0; i < clen; i++) {
          String column = rmdata.getColumnName(i + 1).toUpperCase();
          columns[i] = column;
          String cname = rmdata.getColumnClassName(i + 1);
          // Replace sql.Date with util.Date
          cname = (cname == "java.sql.Date") ? "java.util.Date" : cname;
          d.addColumn(column, cname);
        }

        d.endColumn();
      } catch (SQLException e) {
        //-- 134
        throw new WsSrvException(134, "Unable retrieve result metadata", e);
      }

      try {
        while (res.next()) {
          d.startRecord();

          for (int i = 0; i < clen; i++)
            d.addValue(columns[i], res.getObject(i + 1));

          // Finish record
          d.endRecordSuccess();
        }
      } catch (SQLException e) {
        //-- 135
        throw new WsSrvException(135, "Unable retrieve result data set", e);
      }

      d.endData();
    } finally {
      //TODO Delete obsoleted
      // JdbcUtils.closeStatement(stmt);
      // JdbcUtils.closeConnection(conn);
      DataSourceUtils.releaseConnection(conn, ds);
    }

    return d;
  }

  @Override
  SqlData getDataSetSpec() {
    return ((SqlDataSetProc) getDataSetProc()).getDataSetSpec();
  }

}
