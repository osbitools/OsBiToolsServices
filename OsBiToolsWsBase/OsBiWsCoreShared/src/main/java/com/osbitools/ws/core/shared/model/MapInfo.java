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

package com.osbitools.ws.core.shared.model;

import java.util.ArrayList;
import java.util.List;

import com.osbitools.ws.shared.binding.ds.ColumnHeader;
import com.osbitools.ws.shared.binding.ds.RequestParameters;

/**
 * Container for Map Info DTO object
 * 
 */
public class MapInfo {

  // List of columns
  private List<ColumnHeader> columns;

  // Request Parameters
  private RequestParameters params;

  /**
   * Default constructor
   * 
   * @param columns
   *          Array with columns or null if empty.
   * @param params
   *          Request parameters or null if empty.
   */
  public MapInfo(List<ColumnHeader> columns, RequestParameters params) {
    // Avoid sending null
    this.columns = columns == null ? new ArrayList<ColumnHeader>() : columns;
    this.params = params == null ? new RequestParameters() : params;
  }

  /**
   * @return the columns
   */
  public List<ColumnHeader> getColumns() {
    return columns;
  }

  /**
   * @param columns
   *          the columns to set
   */
  public void setColumns(List<ColumnHeader> columns) {
    this.columns = columns;
  }

  /**
   * @return the params
   */
  public RequestParameters getParams() {
    return params;
  }

  /**
   * @param params
   *          the params to set
   */
  public void setParams(RequestParameters params) {
    this.params = params;
  }
}
