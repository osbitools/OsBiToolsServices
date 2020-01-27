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

package com.osbitools.ws.rest.prj.shared.common;

/**
 * List of file extension supported by directory. Directory located in project
 * root directory
 * 
 */

// TODO Delete later - Not used.
public class DirExtList {

  // Subdirectory name
  private final String _dname;

  // List of file extension supported by directory
  private final String[] _extl;

  public DirExtList(String dirName, String[] extList) {
    _dname = dirName;
    _extl = extList;
  }

  public String getDirName() {
    return _dname;
  }

  public String[] getExtList() {
    return _extl;
  }
}
