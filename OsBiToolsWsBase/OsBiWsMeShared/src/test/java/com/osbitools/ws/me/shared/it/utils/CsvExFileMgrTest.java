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

package com.osbitools.ws.me.shared.it.utils;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.BeforeClass;
import org.junit.Test;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.me.shared.it.utils.ex_file.CsvFileInfo;
import com.osbitools.ws.rest.prj.shared.common.TestPrjMgrBaseConstants;
import com.osbitools.ws.rest.prj.shared.it.utils.GenericPrjMgrTest;
import com.osbitools.ws.rest.prj.shared.it.utils.TestFileItem;
import com.osbitools.ws.rest.prj.shared.utils.ExFileUtils;
import com.osbitools.ws.rest.prj.shared.utils.ProjectUtils;
import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.common.JarTestResourceUtils;

public class CsvExFileMgrTest extends GenericPrjMgrTest {

  // @formatter:off

  // List of test csv files in test project
  public static String[][] TEST_CSV_FILES = new String[][] {
      new String[] {
          "filter_complex.csv",
          "{\"col_list\":[" + "{\"name\":\"CSTR\",\"java_type\":\"java.lang.String\"}," +
              "{\"name\":\"CINT\",\"java_type\":\"java.lang.String\"}," +
              "{\"name\":\"CNUM\",\"java_type\":\"java.lang.String\"}," +
              "{\"name\":\"CDATE\",\"java_type\":\"java.lang.String\"}" + "]}",
          "@\\{" + "\"size\":3718," + "\"read\":true," + "\"write\":true," +
              "\"last_modified\":\"" + TestPrjMgrBaseConstants.ISO_8601_FMT + "\"" + "\\}@" },

      new String[] { "filter_complex_no_header.csv",
          "{\"col_list\":[" + "{\"name\":\"COL1\",\"java_type\":\"java.lang.String\"}," +
              "{\"name\":\"COL2\",\"java_type\":\"java.lang.String\"}," +
              "{\"name\":\"COL3\",\"java_type\":\"java.lang.String\"}," +
              "{\"name\":\"COL4\",\"java_type\":\"java.lang.String\"}" + "]}",
          "@\\{" + "\"size\":3697," + "\"read\":true," + "\"write\":true," +
              "\"last_modified\":\"" + TestPrjMgrBaseConstants.ISO_8601_FMT + "\"" + "\\}@" },

      new String[] { "sort1.csv",
          "{\"col_list\":[" + "{\"name\":\"CSTR\",\"java_type\":\"java.lang.String\"}," +
              "{\"name\":\"CNUM\",\"java_type\":\"java.lang.String\"}" + "]}",
          "@\\{" + "\"size\":760," + "\"read\":true," + "\"write\":true," +
              "\"last_modified\":\"" + TestPrjMgrBaseConstants.ISO_8601_FMT + "\"" + "\\}@" },

      new String[] { "sort2.csv",
          "{\"col_list\":[" + "{\"name\":\"CSTR\",\"java_type\":\"java.lang.String\"}" + "]}",
          "@\\{" + "\"size\":531," + "\"read\":true," + "\"write\":true," +
              "\"last_modified\":\"" + TestPrjMgrBaseConstants.ISO_8601_FMT + "\"" + "\\}@" } };

  // @formatter:on

  @BeforeClass
  public static void prepCsvDir() throws IOException, URISyntaxException {
    // Copy all top demo maps into demo folder
    JarTestResourceUtils.copyJarDemoProj(TestPrjMgrBaseConstants.DEMO_PRJ_DIR_S, "",
        new DsMapTestResConfig());
  }

  CsvFileInfo _cfi = new CsvFileInfo();

  @Test
  public void testCsvColumnsInfo() throws WsSrvException {
    String dname = "test";

    for (int i = 0; i < TEST_CSV_FILES.length; i++) {
      String[] ftest = TEST_CSV_FILES[i];

      HashMap<String, String> params = MeTestFileUtils.TEST_CSV_COL_PARAMS[i];

      assertEquals("Checking colums for " + ftest[0], TEST_CSV_FILES[i][1],
          "{\"col_list\":" + _cfi.getColumns(new File(TestPrjMgrBaseConstants.DEMO_PRJ_DIR +
              dname + File.separator + "csv" + File.separator + ftest[0]), params) + "}");
    }
  }

  @Test
  public void testExFile() throws WsSrvException {
    String dname = "xxx";
    ProjectUtils.createProject(getRoot(), dname);

    for (int i = 1; i < MeTestFileUtils.TEST_CSV_SET.length; i++)
      testCsvFile(dname + ".test" + i, MeTestFileUtils.TEST_CSV_SET[i],
          MeTestFileUtils.TEST_CSV_FILES_PARAMS[i]);

    ProjectUtils.delProject(getRoot(), dname);
  }

  private void testCsvFile(String name, TestFileItem ts, HashMap<String, String> params)
      throws WsSrvException {

    String cname = name + ".csv";
    String rname = name + "_temp.csv";
    HashSet<String> extl = EUT.getExtList("csv");
    String columns = ExFileUtils.createFile(getRoot(), cname,
        new ByteArrayInputStream(ts.getFileText()), extl, "csv", params, EUT, true);

    assertEquals("Columns doesn't match for " + name, ts.getStrResponse(), columns);

    GenericUtils.renameFile(getRoot(), cname, rname, "csv", "csv");

    assertArrayEquals("File after get doesn't match original file", ts.getFileText(),
        ExFileUtils.getFile(getRoot(), rname, extl, "csv"));

    assertEquals(
        "Columns doesn't match", ts.getStrResponse(), _cfi
            .getColumns(
                new File(getRoot() + File.separator +
                    rname.replaceFirst("\\.", File.separator + "csv" + File.separator)),
                params));

    GenericUtils.deleteFile(getRoot(), rname, "csv", "csv");
  }
}
