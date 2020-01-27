/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-01-01
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared.web.config;

import org.slf4j.Logger;

import com.osbitools.ws.shared.config.AbstractConfig;

/**
 * Base Web Service Configuration file
 * 
 */
public class BaseWebConfig extends AbstractConfig {

  // Serial Version UID
  private static final long serialVersionUID = 1L;
 
  /**
   * Default constructor
   */
  public BaseWebConfig() {
    super();
  }

  /**
   * Class Constructor with external property file only
   * 
   * @param fileName Name of external file
   */
  public BaseWebConfig(String fileName) {
    super(fileName);
  }

  /**
   * Simplified constructor
   * 
   * @param fileName
   * @param logger
   */
  public BaseWebConfig(String fileName, Logger logger) {
    super(fileName, logger);
  }

  public BaseWebConfig(String fileName, String homeDir, Logger logger) {
    super(fileName, homeDir, logger);
  }

  @Override
  public String getPrefix() {
    return "web_cfg";
  }
}
