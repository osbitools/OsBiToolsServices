/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2019-08-31
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.rt;

import java.time.LocalDateTime;

/**
 * Data Message to pass to web subscriber
 * 
 */

public class DataMsg {

  private final String date;
  
  private final Object value;
  
  public DataMsg(String date, Object value) {
    this.date = date;
    this.value = value;
  }

  public DataMsg(Object value) {
    this(LocalDateTime.now().toString(), value);
  }

  /**
   * @return the date
   */
  public String getDate() {
    return date;
  }

  /**
   * @return the value
   */
  public Object getValue() {
    return value;
  }
  
  @Override
  public String toString() {
    return date + "=" + value;
  }

}
