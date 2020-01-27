/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2016-05-26
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.common.UserService;
import com.osbitools.ws.shared.service.RequestLogger;

/**
 * Request Logger
 * 
 */

public class RequestLoggerImpl implements RequestLogger {

  @Autowired
  @Qualifier("log")
  private Logger _log;

  @Autowired
  @Qualifier("user_service")
  public void setLoginUser(UserService _usrv) {
    _luser = _usrv.getLoginUser();
  }

  // Request Id
  private final long _rid;

  // Login user
  private String _luser;

  public RequestLoggerImpl() {
    _rid = System.currentTimeMillis();
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.shared.components.RequestLogger#setLogger(org.apache.log4j.Logger)
   */
  @Override
  public void setLogger(Logger logger) {
    _log = logger;
  }

  private String getReqPrefix() {
    return _rid + " " + _luser + " ";
  }

  public Long getRequestId() {
    return _rid;
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.shared.components.RequestLogger#error(java.lang.String)
   */
  @Override
  public void error(String s) {
    _log.error(getReqPrefix() + s);
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.shared.components.RequestLogger#error(java.lang.Exception)
   */
  @Override
  public void error(Exception e) {
    error(e.getMessage());
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.shared.components.RequestLogger#error(com.osbitools.ws.shared.WsSrvException)
   */
  @Override
  public void error(WsSrvException e) {
    error(e.getErrorCode(), e.getErrorInfo(), e.getDetailMsgs());
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.shared.components.RequestLogger#error(int, java.lang.String, java.lang.String[])
   */
  @Override
  public void error(int errId, String errInfo, String[] errDetails) {
    error("ERROR #" + errId + " " +
        (!StringUtils.isEmpty(errInfo) ? "; INFO: " + errInfo + ";" : ""));
    error(errDetails);
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.shared.components.RequestLogger#error(int, java.lang.String[])
   */
  @Override
  public void error(int errId, String[] errDetails) {
    error("ERROR #" + errId);
    error(errDetails);
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.shared.components.RequestLogger#error(int, int, java.lang.String, java.lang.String[])
   */
  @Override
  public void error(int httpCode, int errId, String errInfo,
      String[] errDetails) {
    error("ERROR #" + errId + "/" + httpCode +
        (!StringUtils.isEmpty(errInfo) ? "; INFO: " + errInfo + ";" : ""));
    error(errDetails);
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.shared.components.RequestLogger#error(java.lang.String[])
   */
  @Override
  public void error(String[] dmsg) {
    if (BaseUtils.isEmpty(dmsg))
      return;
    for (String s : dmsg)
      error("ERROR DETAILS:" + s);
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.shared.components.RequestLogger#error(int, java.lang.String, java.lang.String)
   */
  @Override
  public void error(int id, String msg, String dmsg) {
    error(" ERROR #" + id + (msg != null ? "; INFO: " + msg + ";" : ""));
    if (!BaseUtils.isEmpty(dmsg))
      error(new String[] { dmsg });
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.shared.components.RequestLogger#error_ex(java.lang.String, java.lang.String)
   */
  @Override
  public void error_ex(String key, String msg) {
    error(key + ":[" + msg + "]");
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.shared.components.RequestLogger#warn(java.lang.String)
   */
  @Override
  public void warn(String msg) {
    _log.warn(getReqPrefix() + msg);
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.shared.components.RequestLogger#debug(java.lang.String)
   */
  @Override
  public void debug(String msg) {
    _log.debug(getReqPrefix() + msg);
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.shared.components.RequestLogger#debug_ex(java.lang.String, java.lang.String)
   */
  @Override
  public void debug_ex(String key, String value) {
    debug(key + ":[" + value + "]");
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.shared.components.RequestLogger#debug_ex(java.lang.String, int)
   */
  @Override
  public void debug_ex(String key, int value) {
    debug(key + ":[" + value + "]");
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.shared.components.RequestLogger#debug_ex(java.lang.String, java.lang.String[])
   */
  @Override
  public void debug_ex(String key, String[] value) {
    String s = value[0];
    for (int i = 1; i < value.length; i++)
      s += " " + value[i];
    debug(key + ":[" + s + "]");
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.shared.components.RequestLogger#info(java.lang.String)
   */
  @Override
  public void info(String msg) {
    _log.info(getReqPrefix() + msg);
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.shared.components.RequestLogger#trace(java.lang.String)
   */
  @Override
  public void trace(String msg) {
    _log.trace(getReqPrefix() + msg);
  }

  @Override
  public String getLoginUser() {
    return _luser;
  }
}
