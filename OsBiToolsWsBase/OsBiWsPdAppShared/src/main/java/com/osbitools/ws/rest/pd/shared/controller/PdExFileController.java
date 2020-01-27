/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-01-01
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.pd.shared.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.osbitools.ws.rest.prj.rest.shared.controller.AbstractExFileController;
import com.osbitools.ws.shared.GenericServiceUtils;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.pd.shared.dto.IconFileDto;

/**
 * Rest controller for Web Page Entity
 * 
 */

@RestController
public class PdExFileController
    extends AbstractExFileController<IconFileDto> {

  @RequestMapping(value = SRV_BASE_URL + "src/" + SRV_PATH_SET,
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public IconFileDto readExFileInfo(@PathVariable("dname") String dname,
      @PathVariable("fname") String fname, HttpServletRequest req)
      throws WsSrvException {
    return getExFileService().getExtFileInfo(dname, fname,
        GenericServiceUtils.getReqParamValues(req.getParameterMap()));
  }
}
