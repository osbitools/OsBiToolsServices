/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-05-05
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared.service;

/**
 * Web App Info class
 * 
 */

public interface WsInfo {

  public static final String MANIFEST_PATH = "/META-INF/MANIFEST.MF";

  /**
   * Get full Build Version, for ex 1.0.0-RELEASE
   * @return Full Build Version
   */
  String getBuildVersion();
  
  /**
   * Get version of embedded Web Service, for ex 1.0.0
   * @return Version of embedded Web Service
   */
  String getWsVersion();
}
