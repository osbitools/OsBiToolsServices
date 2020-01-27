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

package com.osbitools.ws.rest.prj.shared.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Map;

import com.osbitools.ws.rest.prj.shared.dto.EntityDto;
import com.osbitools.ws.base.WsSrvException;

/**
 * Interface for Entity processing service
 * 
 * @param <T>
 */
public interface EntityService<T> {

  /**
   * Create entity
   * 
   * @param name File Name relative to the Base directory name
   * @param EntityDto entity input Entity
   * @param overwrite True to overwrite existing file (must exists) and False to create
   *          new one (must not exists)
   * 
   * @throws WsSrvException
   */
  public void createEntity(String name, T entity, Boolean overwrite) throws WsSrvException;

  /**
   * Create entity from raw data from input stream
   * 
   * @param name File Name relative to the Base directory name
   * @param in Input Stream with file
   * 
   * @throws WsSrvException
   */
  EntityDto<T> createEntity(String name, InputStream in, Boolean overwrite)
      throws WsSrvException;

  /**
   * Read Entity from disk
   * 
   * @param name File Name relative to the Base directory name
   * @return File converted into ResponseDTO
   * 
   * @throws WsSrvException
   */
  public EntityDto<T> getEntity(String name) throws WsSrvException;

  /**
   * Rename entity. If successful return nothing if error return exception
   * 
   * @param name Original File
   * @param rename Name of destination file
   * @param overwrite Overwrite if destination file exists
   * 
   * @throws WsSrvException
   */
  public void renameEntity(String name, String rename) throws WsSrvException;

  /**
   * Delete entity
   * 
   * @param name File Name relative to the Base directory name
   * 
   * @throws WsSrvException
   */
  public void deleteEntity(String name) throws WsSrvException;

  /**
   * Get list of supported subdirectories together with list of supported file
   * extensions per subdirectory
   * 
   * @return list of supported subdirectories together with list of supported
   *         file extensions per subdirectory
   */
  public Map<String, String[]> getSubDirExtList();

  /**
   * Convert input binary data into entity object
   * 
   * @param fileInputStream Input stream
   * @return Entity Object
   * 
   * @throws WsSrvException
   */
  public T get(InputStream fileInputStream) throws WsSrvException;

  /**
   * Convert input binary data into entity object
   * 
   * @param data Input data
   * @return Entity Object
   * 
   * @throws WsSrvException
   */
  public T get(byte[] data) throws WsSrvException;

  /**
   * Read Entity file from storage and send it AS IS into output stream
   * 
   * @param name File Name relative to the Base directory name
   * @param out OutputStream channel
   * 
   * @return Path to External File
   * 
   * @throws WsSrvException
   */
  Path download(String name, OutputStream out) throws WsSrvException;

  /**
   * Locate file on storage and return Input Stream handler
   * 
   * @param name File Name relative to the Base directory name
   * @return InputStream
   * 
   * @throws WsSrvException
   */
  InputStream read(String name) throws WsSrvException;

  /**
   * Retrieve file by revision id
   * 
   * @param name File name
   * @param rev Revision Id
   * @return EntityDto object with T entity inside
   * 
   * @throws WsSrvException
   */
  EntityDto<T> getRevFile(String name, String rev) throws WsSrvException;
}
