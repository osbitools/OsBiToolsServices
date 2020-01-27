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

package com.osbitools.ws.core.shared.config;

import java.util.List;

import com.osbitools.ws.shared.config.BaseAppWsConfigProperties;

/**
 * Default configuration properties. These properties used if config.properties
 * file doesn't exists and needs to be initialized with some values
 * 
 */

public class CoreWsConfigProperties extends BaseAppWsConfigProperties {

  private Integer rescan;

  private Boolean trace;
  
  private List<String> rtBaskets;
  
  /**
   * @return the rescan
   */
  public Integer getRescan() {
    return rescan;
  }

  /**
   * @param rescan the rescan to set
   */
  public void setRescan(Integer rescan) {
    this.rescan = rescan;
  }

  /**
   * @return the trace
   */
  public Boolean getTrace() {
    return trace;
  }

  /**
   * @param trace
   *          the trace to set
   */
  public void setTrace(Boolean trace) {
    this.trace = trace;
  }

  /**
   * @return the rtBaskets
   */
  public List<String> getRtBaskets() {
    return rtBaskets;
  }

  /**
   * @param rtBaskets the rtBaskets to set
   */
  public void setRtBaskets(List<String> rtBaskets) {
    this.rtBaskets = rtBaskets;
  }

}
