/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2016-31-05
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.config;

import org.slf4j.Logger;

import com.osbitools.ws.shared.config.PropertyItem;
import com.osbitools.ws.shared.web.config.BaseWebConfig;

/**
 * Web Client Configuration bean
 * 
 */

public class PrjWebConfig extends BaseWebConfig {

  // Serial Version UID
  private static final long serialVersionUID = 1L;

  // Minified flag
  // Controls the WebUI payload formatting that sent to server
  @PropertyItem
  private Boolean minified;

  // List of components
  @PropertyItem
  private String compList;

  // List of Data Source(s)
  @PropertyItem
  private String dsSrc;
 
  /**
   * Default constructor
   */
  public PrjWebConfig() {
    super();
  }

  /**
   * Initiate WebConfig with file name only
   * 
   * @param fileName Web Config file name
   */
  public PrjWebConfig(String fileName) {
    super(fileName);
  }

  /**
   * Bean constructor to setup all initial configuration parameter
   * 
   * @param homeDir
   *          Home Directory
   * @param minified
   *          Minified flag
   */
  public PrjWebConfig(String fileName, String homeDir, Logger log) {
    super(fileName, homeDir, log);
  }

  public String getCompList() {
    return compList;
  }

  public void setCompList(String compList) {
    this.compList = compList;
  }

  public Boolean getMinified() {
    return minified;
  }

  public void setMinified(Boolean minified) {
    this.minified = minified;
  }

  public void setMinified(String minified) {
    this.minified = Boolean.parseBoolean(minified);
  }

  public String getDsSrc() {
    return dsSrc;
  }

  public void setDsSrc(String dsSrc) {
    this.dsSrc = dsSrc;
  }
  
  @Override
  public String toString() {
    return super.toString() + (compList != null ? " comp.list=" + getCompList() + ";" : "") +
        " minified=" + getMinified() + "; ds_src=" + getDsSrc();
  }
}
