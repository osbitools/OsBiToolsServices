/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-11-07
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.producers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.core.shared.daemons.LsFilesCheck;
import com.osbitools.ws.core.shared.model.*;
import com.osbitools.ws.core.shared.proc.CsvDataSetProc;
import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.binding.ds.*;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Csv DataSet Producer
 * 
 */
public class CsvDataSetProducer extends AbstractDataSetProducer<CsvData> {

  // Some defineds columns
  HashMap<String, String> _columns = new HashMap<String, String>();

  public CsvDataSetProducer(CsvDataSetProc dsProc, LsFilesCheck lcheck, CoreWsConfig cfg) {
    super(dsProc, lcheck, cfg);

    // Index columns
    CsvData cdata = dsProc.getDataSetSpec();
    Columns columns = cdata.getColumns();
    if (columns != null)
      for (ColumnHeader column : columns.getColumn())
        _columns.put(column.getName().toUpperCase(), column.getJavaType());
  }

  public void addColumn(DataSet d, String name) throws WsSrvException {
    // By default column type is String
    // Check if column type defined
    String jtype = _columns.get(name);
    if (jtype == null)
      d.addColumn(name);
    else
      d.addColumn(name, jtype);
  }

  @Override
  public DataSet read(String name, String lang, TraceRecorder trace, List<String> warn)
      throws WsSrvException {
    CsvData spec = getDataSetSpec();

    // Read project folder
    File dir = GenericUtils.getFileDir(getCoreWsConfig().getDsDir(), name);

    String path =
        dir.getAbsolutePath() + File.separator + "csv" + File.separator + spec.getFileName();

    DataSet d = makeNewDataSet(lang, trace, warn);

    CSVReader reader = null;
    FileReader in;
    try {
      in = new FileReader(path);
    } catch (FileNotFoundException e) {
      //-- 116
      throw new WsSrvException(116, "Unable read csv file '" + path + "'", e);
    }

    try {
      reader = new CSVReader(in, spec.getDelim().charAt(0), spec.getQuoteChar().charAt(0),
          spec.getEscapeChar().charAt(0));

      // Poehali
      d.startData();

      // Read columns
      String[] columns = null;

      if (spec.isColFirstRow()) {
        try {
          if ((columns = reader.readNext()) != null) {
            for (String column : columns)
              addColumn(d, column.toUpperCase());
            d.endColumn();
          }
        } catch (IOException e) {
          //-- 117
          throw new WsSrvException(117, "Unable read csv column line", e);
        }
      }

      // Read data
      int idx = 0;
      String[] line;

      try {
        while ((line = reader.readNext()) != null) {
          if (idx == 0 && !spec.isColFirstRow()) {
            // Late column initialization
            columns = new String[line.length];

            for (int i = 0; i < line.length; i++) {
              String cname = "COL" + (i + 1);
              addColumn(d, cname);
              columns[i] = cname;
            }

            d.endColumn();
          }

          d.startRecord();
          for (int i = 0; i < line.length; i++) {

            Object value = line[i];
            String column = columns[i].toUpperCase();
            String jtype = _columns.get(column);
            if (jtype != null)
              value = d.createValue(column, value.toString());

            d.addValue(column, value);
          }

          d.endRecordSuccess();
          idx++;
        }
      } catch (IOException e) {
        //-- 118
        throw new WsSrvException(118, "Unable read csv record: " + d.getRecordCount() + 1, e);
      }

      d.endData();
    } finally {
      try {
        in.close();
        if (reader != null)
          reader.close();
      } catch (IOException e) {
        // Ignore errors
      }
    }

    return d;
  }

  @Override
  CsvData getDataSetSpec() {
    return ((CsvDataSetProc) getDataSetProc()).getDataSetSpec();
  }

}
