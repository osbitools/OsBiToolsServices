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

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Set;

import com.osbitools.ws.me.shared.common.MeSharedTestConstants;
import com.osbitools.ws.rest.prj.shared.common.TestPrjMgrBaseConstants;
import com.osbitools.ws.rest.prj.shared.it.utils.BasicJarDemoFileUtils;
import com.osbitools.ws.rest.prj.shared.it.utils.TestFileItem;
import com.osbitools.ws.shared.DsConstants;
import com.osbitools.ws.shared.DsMapTestResConfig;
import com.osbitools.ws.shared.TestDsConstants;
import com.osbitools.ws.shared.common.TestConstants;

/**
 * Generic utilities read demo maps resources out of jar file
 * 
 */

public class MeTestFileUtils extends BasicJarDemoFileUtils {

  public MeTestFileUtils() {
    super(new DsMapTestResConfig());
  }

  // @formatter:off

  public static String CSV_FILE_LIST;

  public static String CSV_FILE_LIST_JSON;

  //List of test csv files in test project
  public static String[][] TEST_CSV_FILES = new String[][] {
      new String[] { "filter_complex.csv",
          "@\\{" +
              "\"size\":3718," +
              "\"read\":true," +
              "\"write\":true," +
              "\"last_modified\":\"" + TestPrjMgrBaseConstants.ISO_8601_FMT + "\"" +
           "\\}@" },
      new String[] { "filter_complex_no_header.csv",
          "@\\{" +
              "\"size\":3697," +
              "\"read\":true," +
              "\"write\":true," +
              "\"last_modified\":\"" + TestPrjMgrBaseConstants.ISO_8601_FMT + "\"" +
           "\\}@" },
      new String[] { "partial_num.csv",
          "@\\{" +
              "\"size\":2974," +
              "\"read\":true," +
              "\"write\":true," +
              "\"last_modified\":\"" + TestPrjMgrBaseConstants.ISO_8601_FMT + "\"" +
          "\\}@" },
      new String[] { "sort1.csv",
          "@\\{" +
              "\"size\":760," +
              "\"read\":true," +
              "\"write\":true," +
              "\"last_modified\":\"" + TestPrjMgrBaseConstants.ISO_8601_FMT + "\"" +
          "\\}@" },
      new String[] { "sort2.csv",
          "@\\{" +
              "\"size\":531," +
              "\"read\":true," +
              "\"write\":true," +
              "\"last_modified\":\"" + TestPrjMgrBaseConstants.ISO_8601_FMT + "\"" +
          "\\}@" } };

  static {
    String flist1 = "";
    String flist2 = "";
    for (String[] fs : TEST_CSV_FILES) {
      flist1 += "," + fs[0];
      flist2 += ",\"" + fs[0] + "\"";
    }

    CSV_FILE_LIST = flist1.substring(1);

    CSV_FILE_LIST_JSON = "[" + flist2.substring(1) + "]";
  }

  //Create test params
  static HashMap<String, String> params1 = new HashMap<String, String>();
  static HashMap<String, String> params2 = new HashMap<String, String>();
  static HashMap<String, String> params3 = new HashMap<String, String>();

  static {
    params1.put("delim", ";");
    params1.put("quote_char", "\"");
    params1.put("escape_char", "\\");
    params2.put("col_first_row", "false");
  }

  @SuppressWarnings("unchecked")
  static HashMap<String, String>[] TEST_CSV_FILES_PARAMS =
      new HashMap[] { params1, params1, params2, params3 };

  @SuppressWarnings("unchecked")
  public static HashMap<String, String>[] TEST_CSV_COL_PARAMS =
      new HashMap[] { params3, params2, params3, params3 };

  public static String CSV1_TEST = "COL_1;COL_2;COL_3;COL_4;COL_5\n1;2;3;4;5";
  public static String JSON_CSV1 =
      "[" + "{\"name\":\"COL_1\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COL_2\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COL_3\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COL_4\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COL_5\",\"java_type\":\"java.lang.String\"}" + "]";

  public static String CSV2_TEST = "COLA;COLB;COLC;COLD;COLE" + "\nA;B;C;D;E\nА;Б;В;Г;Д";
  public static String JSON_CSV2 =
      "[" + "{\"name\":\"COLA\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COLB\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COLC\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COLD\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COLE\",\"java_type\":\"java.lang.String\"}" + "]";

  public static String CSV3_TEST = "А,Б,В,Г,Д\nA,B,C,D,E";

  public static String JSON_CSV3 =
      "[" + "{\"name\":\"COL1\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COL2\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COL3\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COL4\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COL5\",\"java_type\":\"java.lang.String\"}" + "]";

  public static String JSON_CSV31 =
      "[" + "{\"name\":\"А\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"Б\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"В\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"Г\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"Д\",\"java_type\":\"java.lang.String\"}" + "]";

  public static TestFileItem[] TEST_CSV_SET = new TestFileItem[] {
      new TestFileItem(CSV1_TEST.getBytes(StandardCharsets.UTF_8), JSON_CSV1),
      new TestFileItem(CSV2_TEST.getBytes(StandardCharsets.UTF_8), JSON_CSV2),
      new TestFileItem(CSV3_TEST.getBytes(StandardCharsets.UTF_8), JSON_CSV3),
      new TestFileItem(CSV3_TEST.getBytes(StandardCharsets.UTF_8), JSON_CSV31) };

  public static String DEMO_CSV =
      "LANG,COLA,COLB,COLC,COLD,COLE" + "\nen,A,B,C,D,E\nru,А,Б,В,Г,Д\n";

  public static String DEMO_CSV_COLUMNS =
      "[" + "{\"name\":\"LANG\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COLA\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COLB\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COLC\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COLD\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COLE\",\"java_type\":\"java.lang.String\"}" + "]";

  public static final String[][] PROJ_SORT_LIST = { new String[] { "bad",

      "csv_bad,static_test_calc_bad1,static_test_calc_bad2," + "static_test_calc_bad3" },

      new String[] { "test",

          "csv_filter1,csv_filter_complex,csv_filter_complex_no_header," +
              "csv_filter_params,csv_partial_num,csv_partial_num_complex,csv_sort1," +
              "csv_sort2,csv_sort3," +
              "csv_str1,group_mixed,sql_filter_complex,sql_select,sql_select_cond," +
              "static_test_calc_date1,static_test_calc_date2,static_test_lmap," +
              "static_test_lmap_auto_inc,static_test_lmap_calc," +
              "static_test_lmap_grp_complex1,static_test_lmap_grp_complex2," +
              "static_test_lmap_grp_complex3,static_test_lmap_grp_multi," +
              "static_test_lmap_grp_single,static_test_str1,static_test_str2," +
              "static_test_str3" } };

  private static String COMP_LIST = "{\"comp_list\":\"{\\\"ll_containers\\\":" +
      "[\\\"group\\\"],\\\"ll_avail_ds_types\\\":" +
      "[\\\"csv\\\",\\\"static\\\",\\\"sql\\\"]}\"}";

  // @formatter:on

  @Override
  public String[][] getProjList() {
    return PROJ_SORT_LIST;
  }

  @Override
  public int readDemoFileSize() throws IOException {
    return 5018;
  }

  @Override
  public String getCompList() {
    return COMP_LIST;
  }

  @Override
  public String getProjSrcDir() {
    return TestDsConstants.DS_MAPS_PACKAGE;
  }

  @Override
  public String getProjWorkDir() {
    return TestConstants.WORK_OSBI_SHARED_PATH + DsConstants.DS_DIR + File.separator;
  }

  @Override
  public String getBaseExt() {
    return MeEntityUtils.BASE_EXT;
  }

  @Override
  public String getExtFileList(String resName) {
    return CSV_FILE_LIST;
  }

  @Override
  public String[][] getExtFileTestFiles(String resName) {
    return TEST_CSV_FILES;
  }

  @Override
  public TestFileItem[] getExtFileTestSet(String resName, String fileExt) {
    return TEST_CSV_SET;
  }

  @Override
  public HashMap<String, String>[] getExtFileTestSetParams(String resName) {
    return TEST_CSV_FILES_PARAMS;
  }

  @Override
  public String[] getExtList(String resName) {
    return new String[] { "csv" };
  }

  @Override
  public String[] getResDirList() {
    Set<String> set = MeEntityUtils.EXT_LIST.keySet();
    return set.toArray(new String[set.size()]);
  }

  @Override
  public byte[] getDemoExFileAsBytes(String resName, String ext) {
    return DEMO_CSV.getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public String getDemoExFileUploadResp(String resName, String ext) {
    return "{\"col_list\":" + DEMO_CSV_COLUMNS + "}";
  }

  @Override
  public String getMainDemoFileJson() {
    return MeSharedTestConstants.MAIN_DEMO_DS;
  }

  @Override
  public String[][] getDemoEntities() {
    return MeSharedTestConstants.DS_DEMO_MAPS;
  }

  @Override
  public String[][] getTestEntities() {
    return MeSharedTestConstants.DS_TEST_MAPS;
  }

  @Override
  public String getJsonPrefix() {
    return "";
  }

  @Override
  public String getJsonSuffix() {
    return "";
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.it.utils.IDemoFileUtils#getMimeType(java.lang.String)
   */
  @Override
  public String getMimeType(String fname) {
    return "text/csv";
  }
}
