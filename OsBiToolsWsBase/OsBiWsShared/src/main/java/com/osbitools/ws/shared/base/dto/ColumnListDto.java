/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-02-24
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared.base.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO class for List of Column Object
 * 
 */
public class ColumnListDto {

  // List of Columns
  private List<ColumnDto> colList;
  
  /**
   * Default constructor
   */
  public ColumnListDto() {
    
  }
  
  public ColumnListDto(int initialCapacity) {
    colList = new ArrayList<>(initialCapacity);
  }
  
  public void addColumn(ColumnDto column) {
    colList.add(column);
  }
  
  public List<ColumnDto> getColList() {
    return colList;
  }
  
  public void setColList(List<ColumnDto> colList) {
    this.colList = colList;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null || !obj.getClass().equals(this.getClass()))
      return false;
    
    List<ColumnDto> list = ((ColumnListDto) obj).getColList();
    
    if (colList.size() != list.size())
      return false;
    
    for (int i = 0; i < colList.size(); i++)
      if (!colList.get(i).equals(list.get(i)))
        return false;
    
    return true;
  }
}
