/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-10-01
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
public class BaseAppWsConfig extends BaseWsConfig {

  // Serial Version UID
  private static final long serialVersionUID = 1L;
 
  // Default language
  @PropertyItem
  private String lang;

  /**
   * Default constructor
   */
  public BaseAppWsConfig() {
    super();
  }

  /**
   * Class Constructor with external property file only
   * 
   * @param fileName Name of external file
   */
  public BaseAppWsConfig(String fileName) {
    super(fileName);
  }

  /**
   * Simplified constructor
   * 
   * @param fileName
   * @param logger
   */
  public BaseAppWsConfig(String fileName, Logger logger) {
    super(fileName, logger);
  }

  public BaseAppWsConfig(String fileName, String homeDir, Logger logger) {
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
  public BaseAppWsConfig(String fileName, String homeDir, String debug, String lang,
      Logger logger) {
    super(fileName, homeDir, logger);

    setDebug(debug);
    setLang(lang);
  }

  @Override
  public String getPrefix() {
    return "ws_cfg";
  }

  public void setDebug(String debug) {
    setDebug(Boolean.parseBoolean(debug));
  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

  @Override
  public String toString() {
    return super.toString() + " lang=" + lang + ";";
  }
}
