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

package com.osbitools.ws.core.shared.model;

import java.io.Serializable;

/**
 * Class for single trace record
 * 
 */
public class TraceRecord implements Serializable {

  //Default Serial Version UID
  private static final long serialVersionUID = 1L;

  private String key;

  private Long duration;

  public TraceRecord() {}

  public TraceRecord(String key, Long duration) {
    this.key = key;
    this.duration = duration;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Long getDuration() {
    return duration;
  }

  public void setDuration(Long duration) {
    this.duration = duration;
  }

}
