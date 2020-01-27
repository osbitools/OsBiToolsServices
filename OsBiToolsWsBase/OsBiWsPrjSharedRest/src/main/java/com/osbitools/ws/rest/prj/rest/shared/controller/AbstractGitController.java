/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-12-12
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.prj.rest.shared.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.rest.prj.shared.dto.EntityDto;
import com.osbitools.ws.rest.prj.shared.dto.RevCommitDto;
import com.osbitools.ws.rest.prj.shared.service.EntityService;
import com.osbitools.ws.rest.prj.shared.service.GitService;
import com.osbitools.ws.rest.shared.base.common.RestBaseConstants;
import com.osbitools.ws.rest.shared.base.controller.BaseController;

/**
 * 
 * Git Manager. Implements next API:
 * 
 * GET - Read Previous revisions list or extract file for specific revision
 * PUT - Commit revision
 * POST - Push or Pull to/from remote repository
 * 
 */

public class AbstractGitController<T> extends BaseController {

  private static final String URL_PATH = CommonConstants.BASE_URL + "/git/";

  @Autowired
  private GitService _sgit;

  @Autowired
  private EntityService<T> _sent;
  
  @RequestMapping(
      value = URL_PATH + "commit" + RestBaseConstants.PATH_NAME_PARAM,
      method = RequestMethod.PUT, produces = MediaType.TEXT_PLAIN_VALUE)
  protected String commitRevision(@PathVariable("name") String name,
      @RequestParam(name = "comment") String comment) throws WsSrvException {
    return _sgit.commitFile(name, comment);
  }

  @RequestMapping(
      value = URL_PATH + "revisions" + RestBaseConstants.PATH_NAME_PARAM,
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public RevCommitDto[] readAllRevisions(@PathVariable("name") String name)
      throws WsSrvException {
    return _sgit.getAllRevisions(name);
  }

  // TODO Add pattern for revision
  @RequestMapping(
      value = URL_PATH + "revisions/{rev}" + RestBaseConstants.PATH_NAME_PARAM,
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public EntityDto<T> readRevEntity(@PathVariable("name") String name,
      @PathVariable("rev") String rev) throws WsSrvException {
    return _sent.getRevFile(name, rev);
  }

  @RequestMapping(value = URL_PATH + "push", method = RequestMethod.POST)
  public String pushToRemote() throws WsSrvException {
    return _sgit.pushToRemote();
  }

  @RequestMapping(value = URL_PATH + "pull", method = RequestMethod.POST)
  protected void pullFromRemote() throws WsSrvException {
    _sgit.pullFromRemote();
  }

}
