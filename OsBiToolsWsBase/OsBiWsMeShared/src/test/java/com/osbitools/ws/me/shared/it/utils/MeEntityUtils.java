/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-11-09
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.me.shared.it.utils;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import com.osbitools.ws.me.shared.it.utils.ex_file.*;
import com.osbitools.ws.rest.prj.shared.service.IExFileInfo;
import com.osbitools.ws.rest.prj.shared.utils.BasicXmlEntityUtils;
import com.osbitools.ws.shared.DsConstants;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.binding.ds.DataSetDescr;

/**
 * Implement IEntityUtils interface for Map Editor
 * 
 */
public class MeEntityUtils extends BasicXmlEntityUtils<DataSetDescr> {

  public static final String BASE_EXT = "xml";

  public static final HashMap<String, String[]> EXT_LIST = 
                        new HashMap<String, String[]>();
  
  static {
    EXT_LIST.put("csv", new String[] {"csv"});
  }

  public static final HashMap<String, ExFileInfo> SUP_EX_FILE_EXT = 
                                      new HashMap<String, ExFileInfo>();
  static {
    SUP_EX_FILE_EXT.put("csv", new CsvFileInfo());
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
  public String execInfoReq(String info, String base, String name,
      HashSet<String> extl, String subDir, HashMap<String, String> params,
                                  boolean minified) throws WsSrvException {
    String ext = getFileExt(name);
    
    if ("columns".equals(info)) {
      ExFileInfo exf = SUP_EX_FILE_EXT.get(ext);
      if (exf == null)
        //-- 301
        throw new WsSrvException(301, "Processing module for extension " + 
                                                      ext + " is not found");
      
      File f =  checkFile(base, name, extl, subDir, true);
      
      return exf.getColumns(f, params);
    } else {
      //-- 302
      throw new WsSrvException(302, "Info request \'" + 
                                              info + "\' is not supported.");
    }
  }

  @Override
  public String getPrjRootDirName() {
    return DsConstants.DS_DIR;
  }

  @Override
  public String getBindPkgName() {
    return DsConstants.BIND_PKG_DS_MAP;
  }
}
