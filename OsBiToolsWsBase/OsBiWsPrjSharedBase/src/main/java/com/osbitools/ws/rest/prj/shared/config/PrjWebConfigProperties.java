/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2016-06-09
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.config;

import com.osbitools.ws.shared.web.config.BaseWebConfigProperties;

/**
 * Default configuration properties. These properties used if config.properties file doesn't
 * exists and needs to be initialized with some values
 * 
 */
public class PrjWebConfigProperties extends BaseWebConfigProperties {

  private Boolean minified;

  private String compList;

  private String dsSrc;
  
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
  
  public String getDsSrc() {
    return dsSrc;
  }

  public void setDsSrc(String dsSrc) {
    this.dsSrc = dsSrc;
  }
}
