/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2019-08-30
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.service;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.core.shared.rt.RtBasket;

public interface RtBasketService {

  /**
   * Add value to existing real time basket using current date time stamp
   * 
   * @param name Basket name
   * @param value Value
   * @throws WsSrvException 
   */
  void addValue(String name, Object value) throws WsSrvException;
  
  /**
   * Get basket data
   * 
   * @param name Basket name
   * 
   * @return Map with data
   * @throws WsSrvException 
   */
  RtBasket getBasket(String name) throws WsSrvException;
}
