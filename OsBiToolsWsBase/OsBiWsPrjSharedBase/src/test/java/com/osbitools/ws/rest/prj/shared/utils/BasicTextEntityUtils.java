/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 * Date: 2015-05-02
 * 
 */

package com.osbitools.ws.rest.prj.shared.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.*;

public abstract class BasicTextEntityUtils extends EntityUtils {

  public BasicTextEntityUtils() {
    super();
  }

  @Override
  public String create(String base, String name, InputStream in, boolean overwrite,
      boolean minified) throws WsSrvException {
    File f = GenericUtils.checkFile(base, name, getExt(), overwrite);

    // 1. Save initial xml
    GenericUtils.saveFile(f, in);

    // 2. Reset input stream
    try {
      in.reset();
      return getJson(in, name, minified);
    } catch (IOException e) {
      throw new WsSrvException(351, e);
    }
  }

  @Override
  public boolean hasInfoReq(String info) {
    return false;
  }

  @Override
  public String execInfoReq(String info, String base, String fname, HashSet<String> extl,
      String subDir, HashMap<String, String> params, boolean minified) throws WsSrvException {
    return null;
  }

  @Override
  public String getJson(InputStream in, String name, boolean minified) throws WsSrvException {
    return
    // TODO Update common function
    // Utils.escJsonStr(get(in, name, minified)) + 
    get(in, name, minified).replaceAll("\\\\", "\\\\\\\\").replace("\n", "\\n");
  }
}
