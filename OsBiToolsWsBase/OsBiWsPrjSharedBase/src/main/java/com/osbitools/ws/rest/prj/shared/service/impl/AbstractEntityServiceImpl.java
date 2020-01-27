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

package com.osbitools.ws.rest.prj.shared.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.osbitools.ws.rest.prj.shared.dto.EntityDto;
import com.osbitools.ws.rest.prj.shared.model.GitFile;
import com.osbitools.ws.rest.prj.shared.service.EntityService;
import com.osbitools.ws.rest.prj.shared.service.GitService;
import com.osbitools.ws.shared.ExtListFileFilter;
import com.osbitools.ws.shared.GenericUtils;
import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.base.WsSrvException;

/**
 * Implementation of Entity Service interface
 * 
 * @param <T>
 */
public abstract class AbstractEntityServiceImpl<T> extends AbstractBaseService
    implements EntityService<T> {

  @Autowired
  private GitService _sgit;

  // List of all supported extensions
  private HashSet<String> _exts = new HashSet<String>();

  // List of file filters for each resource directory
  private final HashMap<String, FilenameFilter> _ffilters =
      new HashMap<String, FilenameFilter>();

  // Indexed list of supported extensions
  private final HashMap<String, HashSet<String>> _extl =
      new HashMap<String, HashSet<String>>();

  public AbstractEntityServiceImpl() {
    // Index supported extensions
    Map<String, String[]> sdirs = getSubDirExtList();

    for (String name : sdirs.keySet()) {
      String[] extl = sdirs.get(name);
      HashSet<String> sx = new HashSet<String>();

      for (String ext : extl) {
        sx.add(ext);
        if (!_exts.contains(ext))
          _exts.add(ext);
      }

      _ffilters.put(name, new ExtListFileFilter(extl));
      _extl.put(name, sx);
    }
  }

  /**
   * Convert object into input stream that can be saved in file (xml, text etc.)
   * 
   * @param entity Input object
   * @return
   */
  protected abstract InputStream convert(T entity) throws WsSrvException;

  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.service.EntityService#createEntity(java.lang.String, java.lang.Object, boolean)
   */
  @Override
  public void createEntity(String name, T entity, Boolean overwrite) throws WsSrvException {
    getReqLog().debug("Creating entity '" + name + "'");

    // 1. Get full path.
    File f = GenericUtils.checkFile(getWsCfg().getBaseDir(), name, getWsCfg().getBaseExt(),
        overwrite);

    // 2. Convert object from input format (json) into internal format
    // that can be saved in file (xml, text etc.)
    InputStream in = convert(entity);

    // 3. Save file
    GenericUtils.saveFile(f, in);
    getReqLog().debug("Entity '" + name + "' successfully created");
  }

  @Override
  public EntityDto<T> getEntity(String name) throws WsSrvException {
    EntityDto<T> res = getEntity(read(name), name);
    getReqLog().debug("Entity '" + name + "' successfully retrieved");
    return res;
  }

  @Override
  public void renameEntity(String name, String rename) throws WsSrvException {
    getReqLog().debug("Retrieving entity '" + name + "' to '" + rename + "'");

    // Rename file
    GenericUtils.renameFile(getWsCfg().getBaseDir(), name, rename, getWsCfg().getBaseExt());

    getReqLog().debug("Entity '" + name + "' successfully renamed into '" + rename + "'");
  }

  @Override
  public void deleteEntity(String name) throws WsSrvException {
    getReqLog().debug("Deleting entity '" + name + "'");

    // Delete file
    GenericUtils.deleteFile(getWsCfg().getBaseDir(), name, getWsCfg().getBaseExt());

    getReqLog().debug("Entity '" + name + "' successfully deleted");
  }

  @Override
  public EntityDto<T> createEntity(String name, InputStream in, Boolean overwrite)
      throws WsSrvException {
    getReqLog().debug(overwrite ? "Updating existing entity file '" + name + "'"
        : "Creating entity file '" + name + "'");

    // 1. Get full path
    File f = GenericUtils.checkFile(getWsCfg().getBaseDir(), name, getWsCfg().getBaseExt(),
        overwrite);

    GenericUtils.saveFile(f, in);

    getReqLog().debug(overwrite ? "File '" + name + "' successfully updated"
        : "File '" + name + "' successfully created");

    return getEntity(name);
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.service.EntityService#download(java.lang.String, java.io.OutputStream)
   */
  @Override
  public Path download(String name, OutputStream out) throws WsSrvException {
    // Remove file extension
    File f = GenericUtils.checkFile(getWsCfg().getBaseDir(),
        name.substring(0, name.length() - getWsCfg().getBaseExt().length() - 1),
        getWsCfg().getBaseExt());

    if (out == null)
      return f.toPath();

    try {
      BaseUtils.copy(new FileInputStream(f), out);
    } catch (IOException e) {
      //-- 295
      throw new WsSrvException(295, e);
    }

    return null;
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.service.EntityService#read(java.lang.String)
   */
  @Override
  public InputStream read(String name) throws WsSrvException {
    getReqLog().debug("Retrieving entity '" + name + "'");

    // 1. Get full path
    File f = GenericUtils.checkFile(getWsCfg().getBaseDir(), name, getWsCfg().getBaseExt());

    InputStream in;
    try {
      in = new FileInputStream(f);

    } catch (FileNotFoundException e) {
      //-- 201
      throw new WsSrvException(201, "File \"" + f.getAbsolutePath() + "\" not found");
    }

    return in;
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.service.EntityService#getRevFile(java.lang.String, java.lang.String)
   */
  @Override
  public EntityDto<T> getRevFile(String name, String rev) throws WsSrvException {
    GitFile gfile = _sgit.getRevFile(name, rev);
    return new EntityDto<T>(get(gfile.getData()), gfile.getHasLog(), gfile.getHasDiff());
  }

  protected EntityDto<T> getEntity(InputStream in, String name) throws WsSrvException {
    return new EntityDto<T>(get(in), _sgit.hasLog(name), _sgit.hasDiff(name));
  }

  protected String read(InputStream in) throws IOException {
    return new String(BaseUtils.readInputStream(in), "UTF-8");
  }
}
