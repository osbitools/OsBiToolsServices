/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-12-29
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.me.shared.it.utils.ex_file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.base.WsSrvException;

/**
 * CSV File processing
 * 
 */
public class CsvFileInfo extends ExFileInfo {

  @Override
  public String[][] getColumnList(File f, Map<String, String> params) throws WsSrvException {
    CSVReader reader = null;
    FileReader in;
    try {
      in = new FileReader(f);
    } catch (FileNotFoundException e) {
      throw new WsSrvException(253, "Unable find csv file \\\'" + f.getAbsolutePath() + "\\\'",
          e);
    }

    String[][] columns;

    try {
      if (params.size() == 0) {
        reader = new CSVReader(in);
      } else {
        String delim = params.get("delim");
        String quote_char = params.get("quote_char");
        String escape_char = params.get("escape_char");
        reader = new CSVReader(in,
            BaseUtils.isEmpty(delim) ? CSVReader.DEFAULT_SEPARATOR : delim.charAt(0),
            BaseUtils.isEmpty(quote_char) ? CSVReader.DEFAULT_QUOTE_CHARACTER
                : quote_char.charAt(0),
            BaseUtils.isEmpty(escape_char) ? CSVReader.DEFAULT_ESCAPE_CHARACTER
                : escape_char.charAt(0));
      }

      // Read columns
      String cfl = params.get("col_first_row");
      Boolean fcol = cfl == null ? true : Boolean.parseBoolean(cfl);

      try {
        // Read first line
        String[] fline;
        if ((fline = reader.readNext()) == null)
          throw new WsSrvException(117, "", "csv column line is empty");

        columns = new String[fline.length][2];

        for (int i = 0; i < fline.length; i++) {
          String cname = (fcol) ? fline[i] : "COL" + (i + 1);

          // By default column type is String
          columns[i] = new String[] { cname, "\"java.lang.String\"" };
        }
      } catch (IOException e) {
        throw new WsSrvException(117, "Unable read csv column line", e);
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
