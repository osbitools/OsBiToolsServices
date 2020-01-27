/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2015-07-03
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.it.utils;

/**
 * Class to keep single Test File
 * 
 */
public class TestFileItem {

  private byte[] _ftext;
  private String _sresp;

  public TestFileItem(byte[] ftext, String sresp) {
    _ftext = ftext;
    _sresp = sresp;
  }

  public byte[] getFileText() {
    return _ftext;
  }

  public void setFileText(byte[] text) {
    _ftext = text;
  }

  public String getStrResponse() {
    return _sresp;
  }

  public void setStrResponse(String response) {
    _sresp = response;
  }

}
