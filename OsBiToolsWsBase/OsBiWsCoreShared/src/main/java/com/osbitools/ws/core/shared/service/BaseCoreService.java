/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-02-03
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.service;

/**
 * Interface for Base Core Service
 */
public interface BaseCoreService {

  /**
   * Get Default Language.
   * 
   * @return Default Language
   */
  public String getDefaultLang();
  
  /**
   * Get Trace Flag.
   * @return Trace Flag
   */
  public boolean getTrace();
}
