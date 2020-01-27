/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2019-09-02
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.core.combo.shared.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.core.shared.service.RtBasketService;
import com.osbitools.ws.rest.shared.base.controller.BaseController;
import com.osbitools.ws.base.WsSrvException;

/**
 * Controller to feed real time data into the system.
 * 
 */

@RestController
@RequestMapping(CommonConstants.BASE_URL)
public class RtFeedController extends BaseController {

  @Autowired
  private RtBasketService _rtbs;

  @RequestMapping(value = "/pub/{basket_name}/{value}",
      method = RequestMethod.POST)
  public void publishRtOAsket(
      @PathVariable(value = "basket_name", required = true) String name,
      @PathVariable(value = "value", required = true) Integer value)
      throws WsSrvException {
    _rtbs.addValue(name, value);
  }

}
