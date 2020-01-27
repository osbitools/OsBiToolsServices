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

package com.osbitools.ws.core.shared.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.core.shared.rt.RtBasket;
import com.osbitools.ws.core.shared.service.RtBasketService;

@Service
public class RtBasketServiceImpl implements RtBasketService {

  @Autowired
  private CoreWsConfig _cfg;

  // Real time baskets
  private final Map<String, RtBasket> _baskets = new HashMap<>();
  
  @PostConstruct
  public void init() {
    // Quick check
    if (_cfg.getRtBaskets() == null || _cfg.getRtBaskets().size() == 0)
      return;

    // Create cache && topics
    for (String bname : _cfg.getRtBaskets()) {
      // TODO Read backet configuration
      _baskets.put(bname, new RtBasket(10)); // TEST
    }
  }

  @Override
  public void addValue(String name, Object value) throws WsSrvException {
    RtBasket basket = getBasket(name);
    basket.process(value);
  }

  @Override
  public RtBasket getBasket(String name) throws WsSrvException {
    RtBasket basket = _baskets.get(name);
    if (basket == null)
      //-- 140
      throw new WsSrvException(140, "Basket [" + name + "] not found.");
    
    return basket;
  }
}
