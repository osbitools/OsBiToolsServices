/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-08-04
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.pd.shared.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.osbitools.ws.pd.shared.common.PdSharedConstants;
import com.osbitools.ws.pd.shared.dto.IconFileDto;
import com.osbitools.ws.pd.shared.ex_file.IconFileInfo;
import com.osbitools.ws.rest.prj.shared.service.IExFileInfo;
import com.osbitools.ws.rest.prj.shared.service.impl.AbstractExFileServiceImpl;

@Service
public class PdExFileServiceImpl extends AbstractExFileServiceImpl<IconFileDto> {

  public static final Map<String, IExFileInfo<IconFileDto>> SUP_EX_FILE_EXT = new HashMap<>();

  static {
    for (String ext : PdSharedConstants.EXT_LIST.get("icons"))
      SUP_EX_FILE_EXT.put(ext, new IconFileInfo());

    /* TODO Delete after test
    SUP_EX_FILE_EXT.put("png", new IconFileInfo());
    SUP_EX_FILE_EXT.put("gif", new IconFileInfo());
    SUP_EX_FILE_EXT.put("jpeg", new IconFileInfo());
    */
  }

  @Override
  public IExFileInfo<IconFileDto> getExFileInfo(String ext) {
    return SUP_EX_FILE_EXT.get(ext);
  }

}
