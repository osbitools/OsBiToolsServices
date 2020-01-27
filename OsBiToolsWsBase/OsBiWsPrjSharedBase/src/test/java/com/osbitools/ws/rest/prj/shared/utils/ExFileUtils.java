/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-12-09
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.prj.shared.utils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.*;

/**
 * Class with static External File utilities
 * 
 */

public class ExFileUtils extends GenericUtils {

  public static String createFile(String base, String name, InputStream in,
      HashSet<String> extl, String sdir, HashMap<String, String> params,
      IEntityUtils eut, boolean minified) throws WsSrvException {
    return createFile(base, name, in, extl, sdir, params, eut, minified, false);
  }

  public static String createFile(String base, String name, InputStream in,
      HashSet<String> extl, String sdir, HashMap<String, String> params,
      IEntityUtils eut, boolean minified, boolean overwrite)
      throws WsSrvException {
    File f = checkFile(base, name, extl, sdir, overwrite);

    // Check if ext directory exists
    File fdir = f.getAbsoluteFile().getParentFile();

    if (!fdir.exists() && !fdir.mkdir())
      throw new WsSrvException(231,
          "Unable create subdirectory " + fdir.getAbsolutePath());

    saveFile(f, in);

    return eut.postExFileCreate(f, name, sdir, params, minified);
  }

  public static byte[] getFile(String base, String name, HashSet<String> extl,
      String sdir) throws WsSrvException {
    File f = checkFile(base, name, extl, sdir, true);

    return readFile(f);
  }
}
