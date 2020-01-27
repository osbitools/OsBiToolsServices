/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2016-26-05
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared.config;

import org.slf4j.Logger;

/**
 * Base Web Service Configuration file
 * 
 */
public class BaseWsConfig extends AbstractConfig {

  // Serial Version UID
  private static final long serialVersionUID = 1L;
 
  // Debug flag. If it set and client supplies "debug" parameter as well
  //  than produce extra debug information in error payload.
  // Default: false
  @PropertyItem
  private Boolean debug = false;

  /**
   * Default constructor
   */
  public BaseWsConfig() {
    super();
  }

  /**
   * Class Constructor with external property file only
   * 
   * @param fileName Name of external file
   */
  public BaseWsConfig(String fileName) {
    super(fileName);
  }

  /**
   * Simplified constructor
   * 
   * @param fileName
   * @param logger
   */
  public BaseWsConfig(String fileName, Logger logger) {
    super(fileName, logger);
  }

  public BaseWsConfig(String fileName, String homeDir, Logger logger) {
    super(fileName, homeDir, logger);
  }

  /**
   * Bean constructor that use when configuration setup outside, for example for test
   * Only field setters must be used that takes String as input parameter
   * 
   * @param homeDir Home Directory
   * @param debug Debug flag
   * @param defLang Default Language
   * @param log Pointer on Logger instance
   */
  public BaseWsConfig(String fileName, String homeDir, String debug, String lang,
      Logger logger) {
    super(fileName, homeDir, logger);

    setDebug(debug);
  }

  @Override
  public String getPrefix() {
    return "ws_cfg";
  }

  public Boolean getDebug() {
    return debug;
  }

  public void setDebug(Boolean debug) {
    this.debug = debug;
  }

  public void setDebug(String debug) {
    setDebug(Boolean.parseBoolean(debug));
  }

  @Override
  public String toString() {
    return super.toString() + " debug=" + debug + ";";
  }
}
