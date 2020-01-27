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

package com.osbitools.ws.core.shared.service.impl;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.osbitools.ws.core.shared.common.CoreConstants;
import com.osbitools.ws.core.shared.daemons.DsDescrResource;
import com.osbitools.ws.core.shared.model.MapInfo;
import com.osbitools.ws.core.shared.service.MapInfoService;
import com.osbitools.ws.base.WsSrvException;

/**
 * Get list of DataSet Maps
 * 
 */

@Service
public class MapInfoServiceImpl extends BaseCoreSeviceImpl
    implements MapInfoService {

  @Override
  public String[] getMapList() {
    String[] list = getDsFilesCheck().getDsMapList();
    Arrays.sort(list);

    // Strip file name extension
    for (int i = 0; i < list.length; i++)
      list[i] = list[i].substring(0, list[i].length() - 
          CoreConstants.BASE_EXT_LEN);

    return list;
  }

  /**
   * Get Map Info
   * 
   * @param mname
   *          map name
   * @return the Map Info record
   * @throws WsSrvException
   */
  @Override
  public MapInfo getMapInfo(String mname) throws WsSrvException {
    // Load map
    String name = mname + ".xml";
    DsDescrResource dsr = getDsFilesCheck().getResource(name);
    
    if (dsr == null)
      //-- 100
      throw new WsSrvException(100, "Map not found for '" + mname + "'");

    return new MapInfo(dsr.getColumnSet(), dsr.getResource().getReqParams());
  }

}
