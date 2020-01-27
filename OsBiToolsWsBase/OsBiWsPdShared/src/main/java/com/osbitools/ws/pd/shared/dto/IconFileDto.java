/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-01-01
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.pd.shared.dto;

/**
 * Icon Source DTO
 * 
 */

public class IconFileDto {

  // Icon Source in Base64 format
  private String base64;

  public IconFileDto(String base64) {
    this.base64 = base64;
  }
  
  /**
   * @return the base64
   */
  public String getBase64() {
    return base64;
  }

  /**
   * @param base64 the base64 to set
   */
  public void setBase64(String base64) {
    this.base64 = base64;
  }
}
