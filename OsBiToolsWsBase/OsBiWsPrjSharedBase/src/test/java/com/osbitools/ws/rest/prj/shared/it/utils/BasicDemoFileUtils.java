/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2015-03-15
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.it.utils;

import java.util.Arrays;

import com.osbitools.ws.rest.prj.shared.common.TestPrjMgrBaseConstants;

/**
 * Generic utilities read demo text resources for unit test
 * 
 */
public abstract class BasicDemoFileUtils implements IDemoFileUtils {

  @Override
  public String getProjListSorted() {
    String[][] plist = getProjList();
    String[] dlist = new String[plist.length];
    for (int i = 0; i < plist.length; i++)
      dlist[i] = plist[i][0];

    Arrays.sort(dlist);

    String res = "";
    for (String dname : dlist)
      res += "," + dname;

    return res.substring(1);
  }

  @Override
  public String getProjEntitiesListSorted() {
    String[][] dset = getDemoSet();
    String[] dlist = new String[dset.length];
    for (int i = 0; i < dset.length; i++)
      dlist[i] = dset[i][0];

    Arrays.sort(dlist);

    String res = "";
    for (String dname : dlist)
      res += "," + dname;

    return res.substring(1);
  }

  @Override
  public String[][] getJsonTestSetFull() {
    String[][] res = getJsonTestSet();

    for (String[] set : res)
      set[1] = "{" + TestPrjMgrBaseConstants.ENTYTY_PREFIX + getJsonPrefix() + set[1] +
          getJsonSuffix() + ",\"has_log\":false,\"has_diff\":true" + "}";

    return res;
  }

  @Override
  public String[][] getJsonDemoSetFull() {
    String[][] res = getJsonDemoSet();

    for (String[] set : res)
      set[1] = "{" + TestPrjMgrBaseConstants.ENTYTY_PREFIX + getJsonPrefix() + set[1] +
          getJsonSuffix() + ",\"has_log\":false,\"has_diff\":true" + "}";

    return res;
  }

  @Override
  public String getExtFileListJson(String rname) {
    String list = getExtFileList(rname);

    // Wrap to JSON format
    String[] slist = list.split(",");

    String res = "";
    for (String s : slist)
      res += ",\"" + s + "\"";

    return "[" + (res.equals("") ? "" : res.substring(1)) + "]";
  }
}
