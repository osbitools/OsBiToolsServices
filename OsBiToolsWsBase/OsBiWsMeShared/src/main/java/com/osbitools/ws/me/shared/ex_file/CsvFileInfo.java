/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2014-12-29
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.me.shared.ex_file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.springframework.util.StringUtils;

import au.com.bytecode.opencsv.CSVReader;

import com.osbitools.ws.rest.prj.shared.service.IExFileInfo;
import com.osbitools.ws.shared.Constants;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.base.dto.ColumnDto;
import com.osbitools.ws.shared.base.dto.ColumnListDto;

/**
 * CSV File processing
 * 
 */
public class CsvFileInfo implements IExFileInfo<ColumnListDto> {

  @Override
  public ColumnListDto getSaveInfo(File f, Map<String, String> params)
      throws WsSrvException {

    CSVReader reader = null;
    FileReader in;
    try {
      in = new FileReader(f);
    } catch (FileNotFoundException e) {
      //-- 303
      throw new WsSrvException(303,
          "Unable find csv file \\\'" + f.getAbsolutePath() + "\\\'", e);
    }

    ColumnListDto columns;

    // Read columns text
    String cfl = null;
        
    try {
      if (params == null || params.size() == 0) {
        reader = new CSVReader(in);
      } else {
        cfl = params.get(Constants.PARAM_VAL_SUFIX + "col_first_row");
        String delim = params.get(Constants.PARAM_VAL_SUFIX + "delim");
        String quote_char = params.get(Constants.PARAM_VAL_SUFIX + "quote_char");
        String escape_char = params.get(Constants.PARAM_VAL_SUFIX + "escape_char");
        
        reader = new CSVReader(in,
            StringUtils.isEmpty(delim) ? CSVReader.DEFAULT_SEPARATOR
                : delim.charAt(0),
            StringUtils.isEmpty(quote_char) ? CSVReader.DEFAULT_QUOTE_CHARACTER
                : quote_char.charAt(0),
            StringUtils.isEmpty(escape_char)
                ? CSVReader.DEFAULT_ESCAPE_CHARACTER
                : escape_char.charAt(0));
      }

      // Read columns flag
      Boolean fcol = cfl == null ? true : Boolean.parseBoolean(cfl);

      try {
        // Read first line
        String[] fline;
        if ((fline = reader.readNext()) == null)
          //-- 304
          throw new WsSrvException(304, "", "csv column line is empty");

        columns = new ColumnListDto(fline.length);

        for (int i = 0; i < fline.length; i++) {
          String cname = (fcol) ? fline[i] : "COL" + (i + 1);

          // By default column type is String
          columns.addColumn(new ColumnDto(cname, String.class.getName()));
        }
      } catch (IOException e) {
        //-- 305
        throw new WsSrvException(305, "Unable read csv column line", e);
      }
    } finally {
      try {
        reader.close();
      } catch (IOException e) {
        // Ignore
      }
    }

    return columns;
  }
}
