/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-02-24
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.core.combo.shared.controller;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.core.shared.service.SqlInfoSevice;
import com.osbitools.ws.rest.shared.base.controller.BaseController;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.base.dto.ColumnListDto;

/**
 * Controller For SQL Info API. Retrieves different SQL information 
 * based on input SQL sentence.
 * 
 */

@Validated
@RestController
@RequestMapping(CommonConstants.BASE_URL)
@ConditionalOnProperty(name = "dev.enabled", havingValue = "true")
public class SqlInfoController extends BaseController {

  @Autowired
  private SqlInfoSevice _sis;

  @RequestMapping(value = "/sql_info/columns", method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ColumnListDto getSqlList(@RequestParam("sql") @NotEmpty String sql)
      throws WsSrvException {
    return _sis.getColumnList(sql);
  }

}
