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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

public class TestTextEntityServiceImpl extends AbstractTextEntityServiceImpl {

  public static final HashMap<String, String[]> EXT_LIST =
      new HashMap<String, String[]>();

  static {
    EXT_LIST.put("dat", new String[] { "num", "int", "txt" });
  }

  @Override
  public HashMap<String, String[]> getSubDirExtList() {
    return EXT_LIST;
  }

  @Override
  protected InputStream convert(String entity) {
    // Strip JSON tag
    return new ByteArrayInputStream(
        entity.replaceAll("\\\\n", "\n").getBytes());
  }
}
