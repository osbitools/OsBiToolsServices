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

import org.springframework.stereotype.Component;

import com.osbitools.ws.base.ErrorList;

/**
 * List of Errors for OsBiTools Core Web Service Reserved error in range
 * [100-149]
 * 
 */

@Component("core_err_list")
public class CustErrorList {

  static final String ERR_PROC_DS = "Fatal error processing dataset";
  static final String ERR_BAD_VERSION = "Data Map version is not supported";
  static final String ERR_IVALID_INPUT_PARAM = "Invalid input parameter";
  static final String ERR_GET_SQL_DATA = "Fatal error retrieving data from database";
  static final String ERR_PROC_INFO_REQ = "Error processing info request";
  
  static {
    // TODO Check all errors
    ErrorList.addError(100, "Data Map is not found");

    ErrorList.addError(101, ERR_BAD_VERSION);
    ErrorList.addError(102, ERR_BAD_VERSION);

    // Fatal error processing dataset
    ErrorList.addError(103, ERR_PROC_DS);
    ErrorList.addError(104, ERR_PROC_DS);
    ErrorList.addError(105, ERR_PROC_DS);
    ErrorList.addError(106, ERR_PROC_DS);
    ErrorList.addError(107, ERR_PROC_DS);
    ErrorList.addError(108, ERR_PROC_DS);
    ErrorList.addError(109, ERR_PROC_DS);
    ErrorList.addError(110, ERR_PROC_DS);
    ErrorList.addError(111, ERR_PROC_DS);
    ErrorList.addError(112, ERR_PROC_DS);
    ErrorList.addError(114, ERR_PROC_DS);
    ErrorList.addError(115, ERR_PROC_DS);

    ErrorList.addError(116, ERR_PROC_DS);
    ErrorList.addError(117, ERR_PROC_DS);
    ErrorList.addError(118, ERR_PROC_DS);
    ErrorList.addError(119, ERR_PROC_DS);
    ErrorList.addError(120, ERR_PROC_DS);
    ErrorList.addError(121, ERR_PROC_DS);

    ErrorList.addError(122, ERR_PROC_DS);
    ErrorList.addError(123, ERR_PROC_DS);
    ErrorList.addError(124, ERR_PROC_DS);
    ErrorList.addError(125, ERR_PROC_DS);
    
    ErrorList.addError(126, ERR_PROC_INFO_REQ);
    ErrorList.addError(127, ERR_PROC_INFO_REQ);
    ErrorList.addError(128, ERR_PROC_INFO_REQ);
    // ErrorList.addError(129, ERR_PROC_DS);

    ErrorList.addError(130, ERR_IVALID_INPUT_PARAM);
    ErrorList.addError(131, ERR_IVALID_INPUT_PARAM);

    ErrorList.addError(132, "Database connection not configured");
    ErrorList.addError(133, ERR_GET_SQL_DATA);
    ErrorList.addError(134, ERR_GET_SQL_DATA);
    ErrorList.addError(135, ERR_GET_SQL_DATA);
    ErrorList.addError(136, ERR_PROC_DS);
    ErrorList.addError(137, ERR_PROC_DS);
    ErrorList.addError(138, ERR_PROC_DS);
    ErrorList.addError(139, ERR_IVALID_INPUT_PARAM);
    
    ErrorList.addError(140, "Real Time Basket not found");

    // ErrorList.addError(, "");
    
  }
}
