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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.osbitools.ws.shared.Constants;
import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.rest.prj.shared.service.ProjectService;
import com.osbitools.ws.rest.shared.base.common.RestBaseConstants;
import com.osbitools.ws.rest.shared.base.controller.BaseController;

/**
 * 
 * API for Top Level Project Folder manipulation
 * 
 */

@RestController
public class ProjectController extends BaseController {

  private static final String BASE_PATH = CommonConstants.BASE_URL + "/projects";

  private static final String NAME_PATH = BASE_PATH + RestBaseConstants.PATH_SHORT_NAME_PARAM;

  @Autowired
  private ProjectService _prj;

  /**
   * Create new project
   * 
   * @throws WsSrvException
   */
  @RequestMapping(value = NAME_PATH, method = RequestMethod.PUT)
  protected void createProject(@PathVariable("name") String name) throws WsSrvException {

    _prj.createProject(name);
  }

  /**
   * Get project list
   */
  @RequestMapping(value = BASE_PATH, method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String[] getProjectList() {
    return _prj.getAllProjects();
  }

  /**
   * Get all files included into given project
   * 
   * @throws WsSrvException
   */
  @RequestMapping(value = NAME_PATH, method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String[] getProjectEntities(@PathVariable("name") String name) throws WsSrvException {

    return _prj.getProjectEntities(name);
  }

  /**
   * Rename existing project
   * 
   * @throws WsSrvException
   */
  @RequestMapping(value = NAME_PATH + "/{name_to:" + Constants.ID_REGEX + "}",
      method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  protected void renameProject(@PathVariable("name") String oldName,
      @PathVariable("name_to") String newName) throws WsSrvException {
    if (oldName.equals(newName)) {
      //-- 203
      throw new WsSrvException(203, "Cannot rename " + oldName + " to itself");
    }

    _prj.renameProject(oldName, newName);
  }

  /**
   * Get project or search for projects
   * 
   * @throws WsSrvException
   */
  @RequestMapping(value = NAME_PATH, method = RequestMethod.DELETE)
  public void deleteProject(@PathVariable("name") String name) throws WsSrvException {
    _prj.deleteProject(name);
  }
}
