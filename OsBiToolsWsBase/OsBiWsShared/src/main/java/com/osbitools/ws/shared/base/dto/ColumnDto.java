/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-02-10
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared.base.dto;

/**
 * DTO class for Column Object
 * 
 */
public class ColumnDto {

  // Column name
  private String name;
  
  // Java Type
  private String javaType;
  
  /**
   * Default constructor
   */
  public ColumnDto() {
  }
  
  public ColumnDto(String name, String javaType) {
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
  
  @Override
  public String toString() {
    return name + ":" + javaType;
  }
  
  @Override
  public boolean equals(Object obj) {
    return obj.getClass().equals(this.getClass()) && 
        name.equals(((ColumnDto) obj).getName()) && 
            javaType.equals(((ColumnDto) obj).getJavaType());
  }
}
