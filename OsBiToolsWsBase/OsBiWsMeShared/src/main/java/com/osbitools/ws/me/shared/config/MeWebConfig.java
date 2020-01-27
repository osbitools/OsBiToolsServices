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

package com.osbitools.ws.me.shared.config;

import org.slf4j.Logger;

import com.osbitools.ws.me.shared.common.MeSharedConstants;
import com.osbitools.ws.rest.prj.shared.config.PrjWebConfig;

/**
 * Web Client Configuration bean for Map Editor Web Properties
 * 
 */

public class MeWebConfig extends PrjWebConfig {

  // Serial Version UID
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor
   */
  public MeWebConfig() {
    super();
  }

  public MeWebConfig(String fileName) {
    super(fileName);
  }
  
  public MeWebConfig(String fileName, String homeDir, Logger log) {
    super(fileName, homeDir, log);
  }

  /**
   * Return hard-coded list of components.
   * Depends of internal implementation and not configured externally.
   */
  @Override
  public String getCompList() {
    return MeSharedConstants.COMP_LIST;
  }
}
