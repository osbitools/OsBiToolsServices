/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-11-19
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.prj.shared.utils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;

import com.osbitools.ws.rest.prj.shared.service.IExFileInfo;
import com.osbitools.ws.base.WsSrvException;

/**
 * Class with static DsMap utilities
 * 
 */

public interface IEntityUtils {

  public String create(String base, String name, InputStream in,
      boolean overwrite, boolean minified) throws WsSrvException;

  /**
   * Get original file as String
   * 
   * @param in
   *          Input Stream
   * @param name
   *          File Name
   * @param minified
   *          Minified flag
   * @return String with original (not converted) file
   * @throws WsSrvException
   */
  public String get(InputStream in, String name, boolean minified)
      throws WsSrvException;

  public String getJson(InputStream in, String name, boolean minified)
      throws WsSrvException;

  public String getExt();

  /**
   * Get list of supported subdirectories together with list of supported file
   * extensions per subdirectory
   * 
   * @return list of supported subdirectories together with list of supported
   *         file extensions per subdirectory
   */
  public HashMap<String, String[]> getSubDirExtList();

  /**
   * Execute post-save action for ex file
   * 
   * @param f
   *          File Handle
   * @param name
   *          Input file name
   * @param sdir
   *          Subdirectory
   * @param params
   *          List of input parameters (together with file name)
   * @param minified
   *          Minified flag
   * @return Result of ex file post-upload action
   * @throws WsSrvException
   */
  public String postExFileCreate(File f, String name, String sdir,
      HashMap<String, String> params, boolean minified) throws WsSrvException;

  public boolean hasInfoReq(String info);

  public String execInfoReq(String info, String base, String fname,
      HashSet<String> extl, String subDir, HashMap<String, String> param,
      boolean minifieds) throws WsSrvException;

  public String getPrjRootDirName();

  /**
   * Get ExFile processing module to retrieve different info about file
   * 
   * @param ext
   * @return
   */
  public IExFileInfo<String> getExFileInfoProc(String ext);
}
