/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-02-24
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.me.shared.service.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.osbitools.ws.me.shared.ex_file.CsvFileInfo;
import com.osbitools.ws.rest.prj.shared.service.IExFileInfo;
import com.osbitools.ws.rest.prj.shared.service.impl.AbstractExFileServiceImpl;
import com.osbitools.ws.shared.base.dto.ColumnListDto;

@Service
public class MeExFileServiceImpl extends AbstractExFileServiceImpl<ColumnListDto> {

  public static final HashMap<String, IExFileInfo<ColumnListDto>> SUP_EX_FILE_EXT =
      new HashMap<>();
  static {
    SUP_EX_FILE_EXT.put("csv", new CsvFileInfo());
  }

  @Override
  public IExFileInfo<ColumnListDto> getExFileInfo(String ext) {
    return SUP_EX_FILE_EXT.get(ext);
  }

}
