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

import java.io.*;
import java.util.*;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.rest.prj.shared.service.IExFileInfo;
import com.osbitools.ws.shared.*;

/**
 * Class with static DsMap utilities
 * 
 */

public abstract class EntityUtils extends GenericUtils implements IEntityUtils {

  // List of all supported extensions
  private HashSet<String> _exts = new HashSet<String>();

  // List of file filters for each resource directory
  private final HashMap<String, FilenameFilter> _ffilters = new HashMap<String, FilenameFilter>();

  // Indexed list of supported extensions
  private final HashMap<String, HashSet<String>> _extl = new HashMap<String, HashSet<String>>();

  public EntityUtils() {
    // Index supported extensions
    HashMap<String, String[]> sdirs = getSubDirExtList();

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

  public HashSet<String> getExtList(String name) {
    return _extl.get(name);
  }

  /**
   * 
   * @param base
   *          Base directory name
   * @param name
   *          File Name
   * @param text
   *          Entity text
   * @param overwrite
   *          True to overwrite existing file (must exists) and False to create
   *          new one (must not exists)
   * @return Create DataSet Map in JSON format
   * @throws WsSrvException
   */
  public String create(String base, String name, String text, boolean overwrite,
      boolean minified) throws WsSrvException {
    return create(base, name, new ByteArrayInputStream(text.getBytes()),
        overwrite, minified);
  }

  public String get(String base, String name, boolean minified)
      throws WsSrvException {
    name = doCheckAddFileNameExt(name);
    
    // 0. Get full path
    File f = checkFile(base, name, getExt());
    try {
      return get(new FileInputStream(f), name, minified);
    } catch (FileNotFoundException e) {
      throw new WsSrvException(211,
          "File \"" + f.getAbsolutePath() + "\" not found");
    }
  }

  private String read(InputStream in) throws IOException {
    return new String(GenericUtils.readInputStream(in), "UTF-8");
  }

  @Override
  public String get(InputStream in, String name, boolean minified)
      throws WsSrvException {
    try {
      return read(in);
    } catch (IOException e) {
      throw new WsSrvException(352, e);
    }
  }

  @Override
  public String postExFileCreate(File f, String name, String sdir,
      HashMap<String, String> params, boolean minified) throws WsSrvException {
    IExFileInfo<String> exf = getExFileInfoProc(sdir);
    if (exf == null)
      //-- 259
      throw new WsSrvException(259,
          "Processing module for ex-file " + sdir + " is not found");

    return exf.getSaveInfo(f, params);
  }

  public String getJson(String base, String name, boolean minified)
      throws WsSrvException {
    name = doCheckAddFileNameExt(name);
    
    // 0. Get full path
    File f = checkFile(base, name, getExt());
    try {
      return getJson(new FileInputStream(f), name, minified);
    } catch (FileNotFoundException e) {
      throw new WsSrvException(211,
          "File \"" + f.getAbsolutePath() + "\" not found");
    }
  }

  public boolean hasExt(String ext) {
    return _exts.contains(ext);
  }

  public FilenameFilter getExtLstFilenameFilter(String name) {
    return _ffilters.get(name);
  }
  
  private String doCheckAddFileNameExt(String name) {
    int pos = name.lastIndexOf(".");
    if (pos != -1 && ! name.substring(pos + 1).equals(getExt()))
      name += "." + getExt();
    
    return name;
  }
}
