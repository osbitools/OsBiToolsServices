/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2018-01-01
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.prj.shared.service;

import com.osbitools.ws.base.WsSrvException;

/**
 * Class with Project utilities
 * 
 */

public interface ProjectService {

  public String createProject(String name) throws WsSrvException;

  public String[] getAllProjects();

  public String[] getAllProjectsEntities() throws WsSrvException;

  public String[] getProjectEntities(String name) throws WsSrvException;

  public String renameProject(String oldName, String newName)
      throws WsSrvException;

  public void deleteProject(String name) throws WsSrvException;
  
}
