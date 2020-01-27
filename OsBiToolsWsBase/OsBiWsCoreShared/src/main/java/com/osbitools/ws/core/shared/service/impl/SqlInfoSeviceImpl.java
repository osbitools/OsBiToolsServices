/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-02-24
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.service.impl;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import com.osbitools.ws.core.shared.service.SqlInfoSevice;
import com.osbitools.ws.shared.service.RequestLogger;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.base.dto.ColumnDto;
import com.osbitools.ws.shared.base.dto.ColumnListDto;

/**
 * SQL Info Service Implementation
 * 
 */

@Service
public class SqlInfoSeviceImpl implements SqlInfoSevice {

  @Autowired
  @Qualifier("rlog")
  private RequestLogger _rlog;

  @Lazy
  @Autowired(required = false)
  private DataSource _ds;

  /* (non-Javadoc)
   * @see com.osbitools.ws.core.shared.service#getColumnList(java.util.Map)
   */
  @Override
  public ColumnListDto getColumnList(String sql)
      throws WsSrvException {
    
    // Quick check
    if (_ds == null)
      //-- 126
      throw new WsSrvException(126, "Database connection not configured.");
    
    Connection conn;
    ColumnListDto res;
    
    try {
      conn = DataSourceUtils.getConnection(_ds);
    } catch (CannotGetJdbcConnectionException  e) {
      //-- 127
      throw new WsSrvException(127, e,
          "Error connection to configured database.");
    }
    
    try {
      ResultSetMetaData mdata = conn.prepareStatement(sql).getMetaData();

      int cnt = mdata.getColumnCount();
      res = new ColumnListDto(cnt);

      for (int i = 0; i < cnt; i++) {
        String ctype = mdata.getColumnClassName(i + 1);
        // Replace sql.Date with util.Date
        res.addColumn(new ColumnDto(mdata.getColumnName(i + 1).toUpperCase(),
            ((ctype == "java.sql.Date") ? "java.util.Date" : ctype)));
      }
    } catch (SQLException e) {
      //-- 128
      throw new WsSrvException(128, sql, e);
    } finally {
      DataSourceUtils.releaseConnection(conn, _ds);
    }

    return res;
  }
}
