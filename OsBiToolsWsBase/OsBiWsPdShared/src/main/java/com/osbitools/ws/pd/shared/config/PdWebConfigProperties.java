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

package com.osbitools.ws.pd.shared.config;

import com.osbitools.ws.rest.prj.shared.config.PrjWebConfigProperties;

/**
 * Default configuration properties. These properties used if config.properties file doesn't
 * exists and needs to be initialized with some values
 * 
 */
public class PdWebConfigProperties extends PrjWebConfigProperties {

  private String previewHost;

  /**
   * @return the previewHost
   */
  public String getPreviewHost() {
    return previewHost;
  }

  /**
   * @param previewHost the previewHost to set
   */
  public void setPreviewHost(String previewHost) {
    this.previewHost = previewHost;
  }

}
