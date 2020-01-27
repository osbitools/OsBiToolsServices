/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2015-07-02
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.it.utils;

import java.util.HashMap;

import com.osbitools.ws.rest.prj.shared.common.TestPrjMgrBaseConstants;
import com.osbitools.ws.rest.prj.shared.service.IExFileInfo;
import com.osbitools.ws.rest.prj.shared.utils.BasicTextEntityUtils;
import com.osbitools.ws.base.ErrorList;

/**
 * Test class with IEntityUtils final implemenation
 * 
 */
public class TestEntityUtils extends BasicTextEntityUtils {

  // Base Extension
  public static String BASE_EXT = "txt";
  
  public static final HashMap<String, String[]> EXT_LIST = 
                              new HashMap<String, String[]>();
  static {
    EXT_LIST.put("dat", new String[] {"num", "int", "txt"});
  }
  
  private static TestExFileInfo _exf = new TestExFileInfo();
  
  public TestEntityUtils() {
    super();
    
    ErrorList.addError(351, "Error processing uploading request");
    ErrorList.addError(352, "Error reading file");
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
  public String getPrjRootDirName() {
    return TestPrjMgrBaseConstants.PRJ_DIR;
  }

  @Override
  public IExFileInfo<String> getExFileInfoProc(String ext) {
    return _exf;
  }
}
