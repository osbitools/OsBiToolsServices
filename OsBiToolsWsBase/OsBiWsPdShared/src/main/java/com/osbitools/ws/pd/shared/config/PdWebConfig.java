/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-02-17
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.pd.shared.config;

import org.slf4j.Logger;

import com.osbitools.ws.rest.prj.shared.config.PrjWebConfig;
import com.osbitools.ws.shared.config.PropertyItem;

/**
 * Web Client Configuration bean for Page Designer Web Properties
 * 
 */

public class PdWebConfig extends PrjWebConfig {

  // Serial Version UID
  private static final long serialVersionUID = 1L;

  // Preview host location to deploy design
  @PropertyItem
  private String previewHost;

  /**
   * Default constructor
   */
  public PdWebConfig() {
    super();
  }

  public PdWebConfig(String fileName) {
    super(fileName);
  }

  public PdWebConfig(String fileName, String homeDir, Logger log) {
    super(fileName, homeDir, log);
  }

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

  @Override
  public String toString() {
    return super.toString() +
        (previewHost != null ? " preview_host=" + previewHost + ";" : "");
  }
}
