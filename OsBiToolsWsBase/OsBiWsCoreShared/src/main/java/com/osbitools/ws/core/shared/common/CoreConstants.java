/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-10-02
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.common;

import com.osbitools.ws.shared.DsConstants;

/**
 * Default and Project specific constants
 * 
 */

public class CoreConstants {
  // Length of extension of DataSet Map Files (with dot included)
  public static final int BASE_EXT_LEN = DsConstants.BASE_EXT.length() + 1;
 
  // Rescan time in msec
  public static final Integer DEFAULT_RESCAN_TIME = 500;

  // Trace breakpoints
  public static final String TRACE_START_PROC = "Start processing";
  public static final String TRACE_CONNECTED_TO_DB = "Connected to database";
  public static final String TRACE_SQL_PREP = "SQL Prepared";
  public static final String TRACE_SQL_EXEC = "SQL Executed";
  public static final String TRACE_READ_DATA = "Data Read";
  public static final String TRACE_DS_PROC_START = "DataSet Proc Start";
  public static final String TRACE_DS_READ_DATA = "DataSet Data Read";
  public static final String TRACE_DS_PROC_END = "DataSet Proc End";
  public static final String TRACE_PROC_END = "End processing";
  public static final String TRACE_FILTER_COMPLETED = "Completed Filtering";
  public static final String TRACE_SORT_COMPLETED = "Completed Sorting";

}
