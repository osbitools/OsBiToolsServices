/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 * Date: 2018-03-10
 * 
 */

package com.osbitools.ws.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import com.osbitools.ws.shared.binding.ds.AutoIncColumn;
import com.osbitools.ws.shared.binding.ds.AutoIncColumns;
import com.osbitools.ws.shared.binding.ds.CalcColumn;
import com.osbitools.ws.shared.binding.ds.CalcColumns;
import com.osbitools.ws.shared.binding.ds.ColumnHeader;
import com.osbitools.ws.shared.binding.ds.Columns;
import com.osbitools.ws.shared.binding.ds.ConditionFilter;
import com.osbitools.ws.shared.binding.ds.CsvData;
import com.osbitools.ws.shared.binding.ds.CsvDataSetDescr;
import com.osbitools.ws.shared.binding.ds.DataSetDescr;
import com.osbitools.ws.shared.binding.ds.DataSetExt;
import com.osbitools.ws.shared.binding.ds.DataSetExtList;
import com.osbitools.ws.shared.binding.ds.ExColumns;
import com.osbitools.ws.shared.binding.ds.GroupData;
import com.osbitools.ws.shared.binding.ds.GroupDataSetDescr;
import com.osbitools.ws.shared.binding.ds.LangColumn;
import com.osbitools.ws.shared.binding.ds.LangMap;
import com.osbitools.ws.shared.binding.ds.RowCell;
import com.osbitools.ws.shared.binding.ds.RowDef;
import com.osbitools.ws.shared.binding.ds.SortCond;
import com.osbitools.ws.shared.binding.ds.SortGroup;
import com.osbitools.ws.shared.binding.ds.SortTypes;
import com.osbitools.ws.shared.binding.ds.SqlData;
import com.osbitools.ws.shared.binding.ds.SqlDataSetDescr;
import com.osbitools.ws.shared.binding.ds.SqlDef;
import com.osbitools.ws.shared.binding.ds.SqlParameters;
import com.osbitools.ws.shared.binding.ds.StaticData;
import com.osbitools.ws.shared.binding.ds.StaticDataSetDescr;
import com.osbitools.ws.shared.binding.ds.StaticRecords;

/**
 * Set of tools for DataSet Maps test
 * 
 */
public class DsMapsTestUtils {

  public static void checkDemoDataSetMap(DataSetDescr ds) {
    assertEquals("Description doesn't match", "Demo Map with all data set types",
        ds.getDescr());
    assertEquals("Enabled doesn't match", true, ds.isEnabled());
    assertEquals("Version max doesn't match", 1, ds.getVerMax());
    assertEquals("Version max doesn't match", 0, ds.getVerMin());

    // Test first lang map
    LangMap lmap = ds.getLangMap();
    assertNotNull("top lang_map tag check failed", lmap);
    List<LangColumn> lc = lmap.getColumn();
    assertNotNull("top lang_map->column(s) tag check failed", lc);
    assertEquals("Top Lang Map Size check failed", 2, lc.size());
    assertEquals("COL1 Lang Column not found", "COL1", lc.get(0).getName());
    assertEquals("COL2 Lang Column not found", "COL2", lc.get(1).getName());

    // Test top ex-columnts
    ExColumns ec = ds.getExColumns();
    assertNotNull("ex_columns tag check failed", ec);

    AutoIncColumns lai = ec.getAutoInc();
    assertNotNull("ex_columns->auto_inc tag check failed", lai);
    List<AutoIncColumn> ai = lai.getColumn();
    assertNotNull("ex_columns->auto_inc->column(s) tag check failed", ai);
    assertEquals("Auto Inc Column Size failed", 2, ai.size());
    assertEquals("Auto Inc Column #1 Name check failed", "A11", ai.get(0).getName());
    assertEquals("Auto Inc Column #2 Name check failed", "B22", ai.get(1).getName());

    CalcColumns lcc = ec.getCalc();
    assertNotNull("ex_columns->calc tag check failed", lcc);
    List<CalcColumn> cc = lcc.getColumn();
    assertNotNull("ex_columns->calc->column(s) tag check failed", lc);
    assertEquals("Calculated Column Size check failed", 2, cc.size());
    assertEquals("Calculated Column #1 Name check failed", "CALC1", cc.get(0).getName());
    assertEquals("Calculated Column #1 Formula check failed", "A + B", cc.get(0).getValue());
    assertEquals("Calculated Column #2 Name check failed", "CALC2", cc.get(1).getName());
    assertEquals("Calculated Column #2 Formula check failed", "C + D", cc.get(1).getValue());

    // Test top sort_by_grp
    SortGroup fs = ds.getSortByGrp();
    ConditionFilter cf = ds.getFilter();
    assertEquals("Condition check failed", "A < B", cf.getValue());

    List<SortCond> sff = fs.getSortBy();
    checkFilter(sff.get(0), 1, "COL1", SortTypes.ASC, false);
    checkFilter(sff.get(1), 2, "COL2", SortTypes.DESC, true);

    assertNull("Non-existing static_data check failed", ds.getStaticData());
    assertNull("Non-existing csv_data check failed", ds.getCsvData());
    assertNull("Non-existing sql_data check failed", ds.getSqlData());
    assertNull("Non-existing xml_data check failed", ds.getXmlData());

    // Check group_data
    GroupData gd = ds.getGroupData();
    assertNotNull("group_ds tag check failed", gd);

    // Check group data columns
    Columns columns = gd.getColumns();
    List<ColumnHeader> col_list = checkColumnsTag("Top", columns, 6);

    checkColumnHeader(col_list.get(0), "COL1", "java.lang.String", "ERROR !!!");
    checkColumnHeader(col_list.get(1), "COL2", "java.lang.String", "");
    checkColumnHeader(col_list.get(2), "A11", "java.lang.Integer", "-100");
    checkColumnHeader(col_list.get(3), "B22", "java.lang.Integer", "0");
    checkColumnHeader(col_list.get(4), "CALC1", "java.lang.Integer");
    checkColumnHeader(col_list.get(5), "CALC2", "java.lang.String");

    // Check group_data->ds_list
    DataSetExtList dsl = gd.getDsList();
    assertNotNull("group_data -> ds_list tag check failed", dsl);

    List<DataSetExt> dsl_list = gd.getDsList().getGroupDsOrStaticDsOrCsvDs();
    assertEquals("DsGroup #1 size", 2, dsl_list.size());

    // Check group_data->ds_list first dataset
    DataSetExt dse = dsl_list.get(0);
    testDataSetExt(dse);
    assertEquals(dse.getClass(), GroupDataSetDescr.class);
    List<DataSetExt> gdsd_list =
        ((GroupDataSetDescr) dse).getGroupData().getDsList().getGroupDsOrStaticDsOrCsvDs();
    assertEquals("group_data->ds_list-group_ds Size check failed", 4, gdsd_list.size());

    // TODO Check group_data->ds_list-group_ds->group_data
    dse = gdsd_list.get(0);
    testDataSetExt(dse, true);
    assertEquals(dse.getClass(), GroupDataSetDescr.class);

    // TODO Check group_data->ds_list-group_ds->static_data
    dse = gdsd_list.get(1);
    testDataSetExt(dse);
    assertEquals(dse.getClass(), StaticDataSetDescr.class);

    // TODO Check group_data->ds_list-group_ds->csv_data
    dse = gdsd_list.get(2);
    testDataSetExt(dse);
    assertEquals(dse.getClass(), CsvDataSetDescr.class);
    CsvDataSetDescr csve = (CsvDataSetDescr) dse;
    CsvData csvd = csve.getCsvData();
    assertNotNull("group_data->ds_list-group_ds->csv_ds->csv_data tag check failed", csvd);
    assertEquals("group_data->ds_list-group_ds->csv_ds->csv_data FileName check failed",
        "filter_complex_no_header.csv", csvd.getFileName());
    assertFalse("group_data->ds_list-group_ds->csv_ds->csv_data ColFirstRow check failed",
        csvd.isColFirstRow());
    assertEquals("group_data->ds_list-group_ds->csv_ds->csv_data Delim check failed", ",",
        csvd.getDelim());
    assertEquals("group_data->ds_list-group_ds->csv_ds->csv_data EscapeChar check failed",
        "\\", csvd.getEscapeChar());
    assertEquals("group_data->ds_list-group_ds->csv_ds->csv_data QuoteChar check failed", "\"",
        csvd.getQuoteChar());

    col_list = checkColumnsTag("group_data->ds_list->group_ds->csv_ds->csv_data",
        csvd.getColumns(), 2);
    checkColumnHeader(col_list.get(0), "COL1", "java.lang.String");
    checkColumnHeader(col_list.get(1), "COL2", "java.lang.String");

    // Check group_data->ds_list-group_ds->sql_data
    dse = gdsd_list.get(3);
    testDataSetExt(dse);
    assertEquals(dse.getClass(), SqlDataSetDescr.class);
    SqlDataSetDescr sqle = (SqlDataSetDescr) dse;
    SqlData sqld = sqle.getSqlData();
    assertNotNull(" sql_data tag check failed", sqld);
    SqlDef sqlf = sqld.getSql();
    assertNotNull(" sql tag check failed", sqlf);
    assertEquals(" Description check failed", "Simple Select", sqlf.getDescr());
    assertEquals(" Description check failed", "SELECT * FROM TEST_DATA", sqlf.getSqlText());

    SqlParameters sqlp = sqlf.getSqlParams();
    assertNull(" SqlParams check failed", sqlp);

    // Check group_data->ds_list->static_ds second dataset
    dse = dsl_list.get(1);
    assertEquals(dse.getClass(), StaticDataSetDescr.class);
    StaticDataSetDescr dsd = (StaticDataSetDescr) dse;

    StaticDataSetDescr sds = dsd;
    assertNotNull("group_data->ds_list->static_ds tag check failed", sds);
    StaticData sdf = sds.getStaticData();
    assertNotNull("group_data->ds_list->static_ds->static_data tag check", sdf);

    col_list = checkColumnsTag("group_data->ds_list->static_ds", sdf.getColumns(), 2);
    checkColumnHeader(col_list.get(0), "COL1", "java.lang.String", "ERROR GRP 1 !!!");
    checkColumnHeader(col_list.get(1), "COL2", "java.lang.String", "ERROR GRP 2 !!!");

    StaticRecords sr = sdf.getStaticRows();
    assertNotNull("static_ds->static tag check", gd);
    List<RowDef> lrd = sr.getRow();
    assertNotNull("static tag->row(s) check", gd);
    assertEquals("Static Rows size", 2, lrd.size());

    RowDef srd = lrd.get(0);
    List<RowCell> lcd = srd.getCell();
    assertNotNull("row#1>column(s) check", lcd);
    assertEquals("Static Columns size", 2, lcd.size());

    checkRowColumn(lcd.get(0), 1, 1, "COL1", "bBb");
    checkRowColumn(lcd.get(1), 1, 2, "COL2", "УуУ");

    srd = lrd.get(1);
    lcd = srd.getCell();
    assertNotNull("row#2>column(s) check", lcd);
    checkRowColumn(lcd.get(0), 2, 1, "COL1", "AaA");
    checkRowColumn(lcd.get(1), 2, 2, "COL2", "пПп");
  }

  private static void testDataSetExt(DataSetExt dse) {
    testDataSetExt(dse, false);
  }
  
  private static void testDataSetExt(DataSetExt dse, boolean fSortByGrp) {
    assertEquals(dse.getExColumns(), null);
    assertEquals(dse.getFilter(), null);
    assertEquals(dse.getIdx(), null);
    assertEquals(dse.getLangMap(), null);
    
    if (fSortByGrp)
      assertNotNull(dse.getSortByGrp());
    else
      assertNull(dse.getSortByGrp());
  }

  private static List<ColumnHeader> checkColumnsTag(String msg, Columns columns, int size) {
    assertNotNull(msg + " columns tag check failed", columns);

    List<ColumnHeader> col_list = columns.getColumn();
    assertEquals(msg + "Columns Size check failed", size, col_list.size());

    return columns.getColumn();
  }

  private static void checkColumnHeader(ColumnHeader ch, String name, String type) {
    checkColumnHeader(ch, name, type, null);
  }

  private static void checkColumnHeader(ColumnHeader ch, String name, String type,
      String onError) {
    assertEquals(name, ch.getName());
    assertEquals(type, ch.getJavaType());
    if (onError == null) {
      assertNull(ch.getOnError());
    } else {
      assertNotNull(ch.getOnError());
      assertEquals(onError, ch.getOnError());
    }
  }

  private static void checkFilter(SortCond sc, int idx, String column, SortTypes stype,
      boolean fgrp) {
    assertEquals("Sort By Group Index check failed", idx, sc.getIdx());
    assertEquals("Sort By Group Column check failed", column, sc.getColumn());
    assertEquals("Sort By Group Sequence Type check failed", stype, sc.getSequence());
    assertEquals("Sort By Group Unique check failed", fgrp, sc.isUnique());
  }

  private static void checkRowColumn(RowCell cd, int row, int col, String name, String value) {
    assertEquals("Row #" + row + " Column #" + col + " Name check failed", name, cd.getName());
    assertEquals("Row #" + row + " Column #" + col + " Value check failed", value,
        cd.getValue());
  }
}
