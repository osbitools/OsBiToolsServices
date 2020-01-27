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
 * Class for dataset column
 * 
 */
public class DataSetColumn implements Serializable {

  //Default Serial Version UID
  private static final long serialVersionUID = 1L;

  private String name;

  private String javaType;

  public DataSetColumn(String name, String javaType) {
    this.name = name;
    this.javaType = javaType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getJavaType() {
    return javaType;
  }

  public void setJavaType(String javaType) {
    this.javaType = javaType;
  }

}
