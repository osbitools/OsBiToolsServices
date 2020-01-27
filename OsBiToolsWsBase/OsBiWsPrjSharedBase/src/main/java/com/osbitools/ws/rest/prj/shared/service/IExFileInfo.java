/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2015-07-02
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.prj.shared.service;

import java.io.File;
import java.util.Map;

import com.osbitools.ws.base.WsSrvException;

/**
 * Interface for all External File processing
 * 
 */

public interface IExFileInfo<T> {

  /**
   * Get file into in json format after ex_file saved
   * 
   * @param f File handle
   * @param params Extra parameters
   * 
   * @return File info in JSON format
   * 
   * @throws WsSrvException
   */
  public T getSaveInfo(File f, Map<String, String> params) throws WsSrvException;
}
