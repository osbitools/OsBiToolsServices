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

package com.osbitools.ws.rest.prj.rest.shared.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.rest.prj.rest.shared.common.PrjMgrRestConstants;
import com.osbitools.ws.rest.prj.shared.dto.EntityDto;
import com.osbitools.ws.rest.prj.shared.service.EntityService;
import com.osbitools.ws.rest.shared.base.common.RestBaseConstants;
import com.osbitools.ws.base.WsSrvException;

/**
 * Abstract controller for Entity processing
 * 
 * @param <T>
 */

@RequestMapping(value = CommonConstants.BASE_URL + "/entities")
public abstract class AbstractEntityController<T> extends BasePrjController {

  @Autowired
  private EntityService<T> _ents;

  @RequestMapping(value = RestBaseConstants.PATH_NAME_PARAM,
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public EntityDto<T> getEntity(@PathVariable("name") String name)
      throws WsSrvException {
    return _ents.getEntity(name);
  }

  @RequestMapping(value = RestBaseConstants.PATH_NAME_PARAM,
      method = RequestMethod.PUT,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public void createEntity(
      @PathVariable(RestBaseConstants.REQ_ENTITY_PARAM_NAME) String name,
      @RequestBody T entity, HttpServletRequest req) throws WsSrvException {
    _ents.createEntity(name, entity, false);
  }

  @RequestMapping(value = RestBaseConstants.PATH_NAME_PARAM,
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public void updateEntity(
      @PathVariable(RestBaseConstants.REQ_ENTITY_PARAM_NAME) String name,
      @RequestBody T entity, HttpServletRequest req) throws WsSrvException {
    _ents.createEntity(name, entity, true);
  }

  @RequestMapping(value = RestBaseConstants.PATH_NAME_PARAM + 
            "/{rename:" + RestBaseConstants.ENTITY_FULL_NAME_REGEX + "}",
      method = RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.OK)
  public void renameEntity(
      @PathVariable(RestBaseConstants.REQ_ENTITY_PARAM_NAME) String name,
      @PathVariable("rename") String rename)
      throws WsSrvException {
    _ents.renameEntity(name, rename);
  }

  @RequestMapping(value = RestBaseConstants.PATH_NAME_PARAM,
      method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteEntity(
      @PathVariable(RestBaseConstants.REQ_ENTITY_PARAM_NAME) String name,
      HttpServletRequest req) throws WsSrvException {
    _ents.deleteEntity(name);
  }

  @RequestMapping(value="/download" +
            "/{name:" + RestBaseConstants.ENTITY_FULL_NAME_REGEX + "." +
                                      PrjMgrRestConstants.FILE_EXT_REGEX + "}",
      method = RequestMethod.GET)
  public void downloadEntity(
      @PathVariable(RestBaseConstants.REQ_ENTITY_PARAM_NAME) String name,
      HttpServletResponse resp) throws WsSrvException {
    // First time call send file without output stream to get path only
    Path path = _ents.download(name, null);

    try {
      String mtype = Files.probeContentType(path);
      if (mtype != null)
        resp.setContentType(mtype);
        // resp.addHeader(HttpHeaders.CONTENT_TYPE, mtype + "; charset=utf-8");
    } catch (IOException e) {
      //-- 296
      throw new WsSrvException(296,
          "Unable recognize mime type for file '" + path.toAbsolutePath() + "'", e);
    }

    resp.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
                                                    path.getFileName().toString());
    
    resp.setContentLengthLong(path.toFile().length());
    
    try {
      _ents.download(name, resp.getOutputStream());
    } catch (IOException e) {
      //-- 267
      throw new WsSrvException(267, e);
    }
  }
  
  @RequestMapping(value="/upload"  + RestBaseConstants.PATH_NAME_PARAM,
      method = RequestMethod.POST)
  public EntityDto<T> createEntity(
      @PathVariable(RestBaseConstants.REQ_ENTITY_PARAM_NAME) String name,
      @RequestParam(value = "overwrite", required = false,
      defaultValue = "false") Boolean overwrite, MultipartFile file) throws WsSrvException {
    
    InputStream in = getUploadedFile(file);
    return _ents.createEntity(name, in, overwrite);
  }
}
