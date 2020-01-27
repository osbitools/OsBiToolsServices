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
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Class for single entry in dataset
 * 
 */
public class DataSetRecord implements Serializable {

  // Default Serial Version UID
  private static final long serialVersionUID = 1L;

  @JsonIgnore
  private HashMap<String, Object> _dmap = new HashMap<>();

  private ArrayList<Object> _rec = new ArrayList<>();

  public void addValue(String colName, Object value) {
    _rec.add(value);
    _dmap.put(colName, value);
  }

  public Object getValue(String colName) {
    return _dmap.get(colName);
  }

  @JsonValue
  public ArrayList<Object> getRecord() {
    return _rec;
  }
}
