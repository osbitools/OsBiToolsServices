/*
 * Open Source Business Intelligence Tools - http://www.osgitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-01-01
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.auth.common;

/**
 * Core Security constants constants
 * 
 */
public class Constants {

  // Cookie name that being set by authentication server
  public static final String COOKIE_SESSION_NAME = "OSBI_SESSION";

  // Default cookie domain that being set by authentication server
  public static final String DEF_COOKIE_DOMAIN = "localhost";

  // Default cookie path that was set by authentication server
  public static final String DEF_COOKIE_PATH = "/";

  // Session parameter name that that was set by authentication server. It holds user name used during login
  public static final String USER_NAME_KEY = "userName";

  // Redis namespace that used keep all security session. 
  // The complete namespace will be security.session.<CUSTOM_SESSION_NAMESPACE>.sessions
  public static final String SESSION_NAMESPACE = "osbitools";

  // Username parameter
  public static final String USERNAME_PARAM = "usr";

  // Password parameter
  public static final String PASSWORD_PARAM = "pwd";

}
