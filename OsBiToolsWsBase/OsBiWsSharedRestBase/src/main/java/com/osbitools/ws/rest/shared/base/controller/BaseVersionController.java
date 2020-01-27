/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2016-26-05
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.shared.base.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.shared.config.BaseWsConfig;
import com.osbitools.ws.shared.service.BaseService;

/**
 * Same as BaseController but added mapping for version.
 * Support both / and /version calls
 * 
 */

@RestController
@RequestMapping(CommonConstants.BASE_URL)
public class BaseVersionController
    extends BaseRestController<BaseService<BaseWsConfig>, BaseWsConfig> {

  @RequestMapping(value = { "/" }, method = RequestMethod.GET,
      produces = MediaType.TEXT_PLAIN_VALUE)
  public String defaultHandler() {
    return getVersion();
  }

  @RequestMapping(value = { "/version" }, method = RequestMethod.GET,
      produces = MediaType.TEXT_PLAIN_VALUE)
  public String version() {
    return getVersion();
  }
}
