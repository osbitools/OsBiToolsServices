/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2016-26-05
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared.config;

/**
 * Base class for all future Ws Configuratino Properties
 * 
 */
public class BaseAppWsConfigProperties extends BaseWsConfigProperties {

  private String lang;

  /**
   * @return the lang
   */
  public String getLang() {
    return lang;
  }

  /**
   * @param lang
   *          the lang to set
   */
  public void setLang(String lang) {
    this.lang = lang;
  }
}
