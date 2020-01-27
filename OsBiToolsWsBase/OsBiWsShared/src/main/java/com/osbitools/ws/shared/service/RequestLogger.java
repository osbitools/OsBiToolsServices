/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2016-06-07
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared.service;

import org.slf4j.Logger;

import com.osbitools.ws.base.WsSrvException;

public interface RequestLogger {

  /**
   * Get Request Id
   * 
   * @return the request id
   */
  public Long getRequestId();
  
  void setLogger(Logger logger);

  void error(String s);

  void error(Exception e);

  void error(WsSrvException e);

  void error(int errId, String errInfo, String[] errDetails);

  void error(int errId, String[] errDetails);

  void error(int httpCode, int errId, String errInfo, String[] errDetails);

  void error(String[] dmsg);

  void error(int id, String msg, String dmsg);

  void error_ex(String key, String msg);

  void warn(String msg);

  void debug(String msg);

  void debug_ex(String key, String value);

  void debug_ex(String key, int value);

  void debug_ex(String key, String[] value);

  void info(String msg);
  
  void trace(String msg);

  /**
   * Get Login User
   * @return
   */
  public String getLoginUser();
}