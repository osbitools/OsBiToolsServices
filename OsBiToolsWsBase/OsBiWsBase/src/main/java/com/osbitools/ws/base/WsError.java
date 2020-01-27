/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2016-01-06
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO class for Web Service Error
 * 
 */
@JsonInclude(value=Include.NON_EMPTY, content=Include.NON_NULL)
public class WsError {

  //Error code
  private int id;
 
  // Error message
  private String message;
  
  // Error Info
  private String info;

  // Detail message(s)
  private String[] details;

  // Extra Json Object
  @JsonProperty("error_ds")
  private Object jsonObj;
  
  public WsError(int id) {
    this.id = id;
  }
  
  public WsError(int id, String message) {
    this(id);
    this.message = message;
  }
  
  public WsError(int id, String message, String info) {
    this(id, message);
    this.info = info;
  }
  
  public WsError(int id, String message, String info, String[] details) {
    this(id, message, info);
    this.details = details;
  }
  
  public int getId() {
    return id;
  }

  public String getMessage() {
    return message;
  }

  public String getInfo() {
    return info;
  }

  public String[] getDetails() {
    return details;
  }
  
  public Object getJsonObj() {
    return jsonObj;
  }

  public void setJsonObj(Object jsonObj) {
    this.jsonObj = jsonObj;
  }
}
