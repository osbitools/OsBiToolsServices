/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-12-29
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.me.shared.it.utils.ex_file;

import java.io.File;
import java.util.Map;

import com.osbitools.ws.rest.prj.shared.service.IExFileInfo;
import com.osbitools.ws.base.WsSrvException;

/**
 * Interfase for all MapEditor External File processing
 * 
 */
public abstract class ExFileInfo implements IExFileInfo<String> {

  /**
   * Format column list into json format
   * 
   * @param base Base directory
   * @param name Entity name
   * @param params Extra parameters
   * @return Columns in JSON format
   * @throws WsSrvException
   */
  public String getColumns(File f, Map<String, String> params) throws WsSrvException {

    String res = "";
    String[][] columns = getColumnList(f, params);

    if (columns != null) {
      for (String[] column : columns) {
        res += "," + "{" + "\"name\":" + "\"" + column[0].toUpperCase() + "\"," +
            "\"java_type\":" + column[1] + "}";
      }
    }

    return "[" + (res.equals("") ? res : res.substring(1)) + "]";
  }

  @Override
  public String getSaveInfo(File f, Map<String, String> params) throws WsSrvException {
    return getColumns(f, params);
  }

  /**
   * Extract columns from dataset map 
   * 
   * @param base Base directory
   * @param name Entity name
   * @param params Extra parameters
   * @return Array of columns
   * @throws WsSrvException
   */
  public abstract String[][] getColumnList(File f, Map<String, String> params)
      throws WsSrvException;

}
