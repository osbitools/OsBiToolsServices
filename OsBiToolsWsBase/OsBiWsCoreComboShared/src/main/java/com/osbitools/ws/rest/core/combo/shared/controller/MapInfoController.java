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

package com.osbitools.ws.rest.core.combo.shared.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.osbitools.ws.core.shared.model.MapInfo;
import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.core.shared.service.MapInfoService;
import com.osbitools.ws.rest.shared.base.common.RestBaseConstants;
import com.osbitools.ws.rest.shared.base.controller.BaseController;
import com.osbitools.ws.base.WsSrvException;

/**
 * API For Map processing. Retrieves Map list as well as info about single map
 * 
 */

@RestController
@RequestMapping(CommonConstants.BASE_URL)
@ConditionalOnProperty(name = "dev.enabled", havingValue = "true")
public class MapInfoController extends BaseController {

  @Autowired
  private MapInfoService _mis;

  @RequestMapping(value = "/maps", method = RequestMethod.GET, 
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String[] getMapList() {
    return _mis.getMapList();
  }

  /**
   * Get Map Info
   * 
   * @param mname
   *          map name
   * @return the Map Info record
   * @throws WsSrvException
   */
  @RequestMapping(value = "/maps" + RestBaseConstants.PATH_NAME_PARAM,
          method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public MapInfo getMapList(@PathVariable(RestBaseConstants.REQ_ENTITY_PARAM_NAME) String name)
      throws WsSrvException {
    return _mis.getMapInfo(name);
  }

}
