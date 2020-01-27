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

package com.osbitools.ws.shared.web.config;

import com.osbitools.ws.shared.web.config.BaseWebConfigProperties;

/**
 * Default configuration properties. These properties used if config.properties file doesn't
 * exists and needs to be initialized with some values
 * 
 */

public class TestWebConfigProperties extends BaseWebConfigProperties {

  private String param1;
  private String param2;
  private String param3;
  /**
   * @return the param1
   */
  public String getParam1() {
    return param1;
  }
  /**
   * @param param1 the param1 to set
   */
  public void setParam1(String param1) {
    this.param1 = param1;
  }
  /**
   * @return the param2
   */
  public String getParam2() {
    return param2;
  }
  /**
   * @param param2 the param2 to set
   */
  public void setParam2(String param2) {
    this.param2 = param2;
  }
  /**
   * @return the param3
   */
  public String getParam3() {
    return param3;
  }
  /**
   * @param param3 the param3 to set
   */
  public void setParam3(String param3) {
    this.param3 = param3;
  }


}
