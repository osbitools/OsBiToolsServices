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

package com.osbitools.ws.rest.prj.shared.service;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

import com.osbitools.ws.rest.prj.shared.dto.FileInfoDto;
import com.osbitools.ws.base.WsSrvException;

public interface ExFileService<T> {

  /**
   * Create external file and save in storage
   * 
   * @param dname Project Name
   * @param fname File Name
   * @param in Input Stream
   * @param params Parameters
   * @param overwrite Overwrite flag if file exists
   * @return Saved file
   * 
   * @throws WsSrvException
   */
  T createFile(String dname, String fname, InputStream in,
      Map<String, String> params, Boolean overwrite) throws WsSrvException;

  Set<String> getExtListByDirName(String dname) throws WsSrvException;

  /**
   * Get File Info
   * 
   * @param dname Resource name
   * @param name File Name
   *
   * @return String with File Info
   * 
   * @throws WsSrvException
   */
  FileInfoDto getFileInfo(String dname, String fname) throws WsSrvException;

  File checkExFile(String sdir, String name, Boolean overwrite)
      throws WsSrvException;

  String[] getExFileList(String pdir, String sdir) throws WsSrvException;

  /**
   * Execute post create action for ex file
   * 
   * @param f File Handle
   * @param params Filtered List of http query parameters
   * 
   * @return Result of ex file post create action
   * 
   * @throws WsSrvException
   */
  T postCreate(File f, Map<String, String> params) throws WsSrvException;

  /**
   * Get External File custom info
   * 
   * @param dname Resource name
   * @param name File Name
   * @param params Filtered List of http query parameters
   * @return Custom info for External File
   * 
   * @throws WsSrvException
   */
  T getExtFileInfo(String dname, String fname, Map<String, String> params)
      throws WsSrvException;

  /**
   * Read file from storage and send it AS IS into output stream
   * 
   * @param dname Resource name
   * @param name File Name
   * @param out OutputStream channel
   * 
   * @return Path to External File
   * 
   * @throws WsSrvException
   */
  Path sendExFile(String dname, String fname, OutputStream out) throws WsSrvException;
  
  /**
   * Get ExFile Info object
   * 
   * @param ext File Extension
   * 
   * @return ExFile Info object
   */
  IExFileInfo<T> getExFileInfo(String ext);
}
