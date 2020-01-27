/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-08-04
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.pd.shared.it.utils;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.osbitools.ws.pd.shared.common.PdSharedConstants;
import com.osbitools.ws.rest.prj.shared.service.IExFileInfo;
import com.osbitools.ws.rest.prj.shared.utils.BasicXmlEntityUtils;
import com.osbitools.ws.wp.shared.WpConstants;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.wp.shared.binding.WebPage;

/**
 * Implement IEntityUtils interface for Page Designer
 * 
 */
public class PdEntityUtils extends BasicXmlEntityUtils<WebPage> {

  public static final String BASE_EXT = "xml";

  public static final HashMap<String, String[]> EXT_LIST = new HashMap<String, String[]>();

  static {
    EXT_LIST.put("icons", new String[] { "png", "gif", "jpeg" });
  }

  public static final Map<String, IExFileInfo<String>> SUP_EX_FILE_EXT = new HashMap<>();
  
  static {
    for (String ext : PdSharedConstants.EXT_LIST.get("icons"))
      SUP_EX_FILE_EXT.put(ext, new TestIconFileInfo());
  }

  @Override
  public String getExt() {
    return BASE_EXT;
  }

  @Override
  public HashMap<String, String[]> getSubDirExtList() {
    return EXT_LIST;
  }

  @Override
  public IExFileInfo<String> getExFileInfoProc(String ext) {
    return SUP_EX_FILE_EXT.get(ext);
  }

  @Override
  public boolean hasInfoReq(String info) {
    return "columns".equals(info);
  }

  @Override
  public String execInfoReq(String info, String base, String name, HashSet<String> extl,
      String subDir, HashMap<String, String> params, boolean minified) throws WsSrvException {
    String ext = getFileExt(name);

    if ("columns".equals(info)) {
      IExFileInfo<String> exf = SUP_EX_FILE_EXT.get(ext);
      if (exf == null)
        //-- 301
        throw new WsSrvException(301,
            "Processing module for extension " + ext + " is not found");

      File f = checkFile(base, name, extl, subDir, true);

      return exf.getSaveInfo(f, params);
    } else {
      //-- 302
      throw new WsSrvException(302, "Info request \'" + info + "\' is not supported.");
    }
  }

  @Override
  public String getPrjRootDirName() {
    return WpConstants.WP_DIR;
  }

  @Override
  public String getBindPkgName() {
    return WpConstants.BIND_PKG_WEB_PAGE;
  }
}
