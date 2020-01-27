/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-01-01
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.osbitools.ws.shared.service.impl.BaseServiceImpl;
import com.osbitools.ws.shared.web.config.BaseWebConfig;
import com.osbitools.ws.shared.web.service.BaseWebService;

@Service("base_web_srv")
public class BaseRestWebServiceImpl extends BaseServiceImpl 
    implements BaseWebService {

  @Autowired
  @Qualifier("web_cfg")
  private BaseWebConfig _cfg;
  
  @Override
  public Map<String, String> getWebConfig(String lst) {
    getLogger().debug("cfg request for '" + lst + "'");
    
    Map<String, String> map = new HashMap<String, String>();
    String[] arr = lst.split(",");
    
    if (_cfg == null)
      return map;
    
    for (String key: arr) {
      try {
        map.put(key, _cfg.getPropByName(key));
      } catch (Exception e) {
        // Skip configuration parameter if missing in a bean 
        getLogger().error(e.getMessage());
      }
    }
    
    return map;
  }
  
}
