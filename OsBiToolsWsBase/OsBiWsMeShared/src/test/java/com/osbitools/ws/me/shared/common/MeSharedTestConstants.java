/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-02-17
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.me.shared.common;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import com.osbitools.ws.rest.prj.shared.it.utils.TestFileItem;

/**
 * Some shared Map Editor constants 
 * 
 */
public class MeSharedTestConstants {

  // @formatter:off

  public static String CSV_FILE_LIST;

  //List of test csv files in test project
  public static String[][] TEST_CSV_FILES = new String[][] {
      new String[] { "filter_complex.csv",
          "@\\{\"file_info\":\\{" + "\"size\":3604," + "\"read\":true," +
              "\"write\":true," + "\"last_modified\":\\d*" + "\\}" + "\\}@" },
      new String[] { "filter_complex_no_header.csv",
          "@\\{\"file_info\":\\{" + "\"size\":3583," + "\"read\":true," +
              "\"write\":true," + "\"last_modified\":\\d*" + "\\}" + "\\}@" },
      new String[] { "partial_num.csv",
          "@\\{\"file_info\":\\{" + "\"size\":2974," + "\"read\":true," +
              "\"write\":true," + "\"last_modified\":\\d*" + "\\}" + "\\}@" },
      new String[] { "sort1.csv",
          "@\\{\"file_info\":\\{" + "\"size\":760," + "\"read\":true," +
              "\"write\":true," + "\"last_modified\":\\d*" + "\\}" + "\\}@" },
      new String[] { "sort2.csv",
          "@\\{\"file_info\":\\{" + "\"size\":531," + "\"read\":true," +
              "\"write\":true," + "\"last_modified\":\\d*" + "\\}" + "\\}@" } };

  static {
    String flist = "";
    for (String[] fs : TEST_CSV_FILES)
      flist += "," + fs[0];

    CSV_FILE_LIST = flist.substring(1);
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

  public static String CSV2_TEST =
      "COLA;COLB;COLC;COLD;COLE" + "\nA;B;C;D;E\nА;Б;В;Г;Д";
  public static String JSON_CSV2 =
      "[" + "{\"name\":\"COLA\",\"java_type\":\"java.lang.St  ````                  ring\"}," +
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
      new TestFileItem(CSV3_TEST.getBytes(StandardCharsets.UTF_8),
          JSON_CSV31) };

  public static String DEMO_CSV =
      "LANG,COLA,COLB,COLC,COLD,COLE" + "\nen,A,B,C,D,E\nru,А,Б,В,Г,Д\n";

  public static String DEMO_CSV_COLUMNS =
      "[" + "{\"name\":\"LANG\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COLA\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COLB\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COLC\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COLD\",\"java_type\":\"java.lang.String\"}," +
          "{\"name\":\"COLE\",\"java_type\":\"java.lang.String\"}" + "]";
    
  public static final String MAIN_DEMO_DS = "{" +
      "\"lang_map\":{" +
        "\"column\":[" +
          "{\"name\":\"COL1\"}," +
          "{\"name\":\"COL2\"}" +
        "]" +
      "}," +
      "\"ex_columns\":{" +
        "\"auto_inc\":{" +
          "\"column\":[" +
            "{\"name\":\"A11\",\"start_from\":0,\"inc_by\":1}," +
            "{\"name\":\"B22\",\"start_from\":0,\"inc_by\":1}" +
          "]" +
        "}," +
        "\"calc\":{" +
          "\"column\":[" +
            "{\"value\":\"A + B\",\"name\":\"CALC1\"," +
                "\"java_type\":\"java.lang.Integer\",\"stop_on_error\":true}," +
            "{\"value\":\"C + D\",\"name\":\"CALC2\"," +
                "\"java_type\":\"java.lang.String\",\"stop_on_error\":true}" +
          "]" +
        "}" +
      "}," +
      "\"sort_by_grp\":{" +
        "\"sort_by\":[" +
          "{\"idx\":1,\"column\":\"COL1\",\"sequence\":\"ASC\"," +
                      "\"unique\":false,\"str_sort\":\"NATURAL\"}," +
          "{\"idx\":2,\"column\":\"COL2\",\"sequence\":\"DESC\"," +
                      "\"unique\":true,\"str_sort\":\"NATURAL\"}" +
        "]" +
      "}," +
      "\"filter\":{\"value\":\"A < B\"}," +
      "\"group_data\":{" +
        "\"columns\":{" +
          "\"column\":[" +
            "{\"name\":\"COL1\",\"java_type\":\"java.lang.String\"," +
                                          "\"on_error\":\"ERROR !!!\"}," +
            "{\"name\":\"COL2\",\"java_type\":\"java.lang.String\"," +
                                          "\"on_error\":\"\"}," +
            "{\"name\":\"A11\",\"java_type\":\"java.lang.Integer\"," +
                                          "\"on_error\":\"-100\"}," +
            "{\"name\":\"B22\",\"java_type\":\"java.lang.Integer\"," +
                                          "\"on_error\":\"0\"}," +
            "{\"name\":\"CALC1\",\"java_type\":\"java.lang.Integer\"}," +
            "{\"name\":\"CALC2\",\"java_type\":\"java.lang.String\"}" +
          "]" +
        "}," +
        "\"ds_list\":[" +
        
          // Group DataSet #1 start
          "{\"group_ds\":{" +
            "\"group_data\":{" +
              "\"ds_list\":[" +
            
                // 1.1 Group DataSet #2 start
                "{\"group_ds\":" +
                  "{\"sort_by_grp\":" +
                    "{\"sort_by\":[" +
                      "{\"idx\":1,\"column\":\"COL1\",\"sequence\":\"ASC\"," +
                            "\"unique\":false,\"str_sort\":\"NATURAL\"}" +
                    "]}," +
                    "\"group_data\":{" +
                      "\"ds_list\":[" +
                    
                        // 2.1 Static DataSet
                        "{\"static_ds\":{" +
                          "\"static_data\":{" +
                            "\"columns\":{" +
                              "\"column\":[" +
                                "{\"name\":\"COL1\"," +
                                    "\"java_type\":\"java.lang.String\"," +
                                    "\"on_error\":\"ERROR GRP 1-1 !!!\"}," +
                                "{\"name\":\"COL2\"," +
                                    "\"java_type\":\"java.lang.String\"," +
                                    "\"on_error\":\"ERROR GRP 1-2 !!!\"}" +
                              "]" +
                            "}," +
                            "\"static_rows\":{" +
                              "\"row\":[" +
                                "{\"cell\":[" +
                                  "{\"name\":\"COL1\",\"value\":\"UUu\"}," +
                                  "{\"name\":\"COL2\",\"value\":\"ЦцЦ\"}" +
                                "]}," +
                                "{\"cell\":[" +
                                  "{\"name\":\"COL1\",\"value\":\"dDd\"}," +
                                  "{\"name\":\"COL2\",\"value\":\"ЗЗз\"}" +
                                "]}" +
                              "]" +
                            "}" +
                          "}" +
                        "}}," +
                          
                        // 2.2 Static DataSet  
                        "{\"static_ds\":{" + 
                          "\"static_data\":{" +
                            "\"columns\":{" +
                              "\"column\":[" +
                                "{\"name\":\"COL1\"," +
                                    "\"java_type\":\"java.lang.String\"," +
                                    "\"on_error\":\"ERROR GRP 2-1 !!!\"}," +
                                "{\"name\":\"COL2\"," +
                                    "\"java_type\":\"java.lang.String\"," +
                                    "\"on_error\":\"ERROR GRP 2-2 !!!\"}" +
                              "]" +
                            "}," +
                            "\"static_rows\":{" +
                              "\"row\":[" +
                                "{\"cell\":[" +
                                  "{\"name\":\"COL1\",\"value\":\"uuU\"}," +
                                  "{\"name\":\"COL2\",\"value\":\"Ццц\"}" +
                                "]}," +
                                "{\"cell\":[" +
                                  "{\"name\":\"COL1\",\"value\":\"Ddd\"}," +
                                  "{\"name\":\"COL2\",\"value\":\"ЗЗз\"}" +
                                "]}" +
                              "]" +
                            "}" +
                          "}}" +
                        "}" +
                      "]" +
                    "}" +
                  "}" +
                "}," +
                
                // 1.2 Static DataSet
                "{\"static_ds\":" +
                  "{\"static_data\":{" +
                    "\"columns\":{" +
                      "\"column\":[" +
                        "{\"name\":\"COL1\",\"java_type\":\"java.lang.String\"," +
                                  "\"on_error\":\"ERROR GRP 3-1 !!!\"}," +
                        "{\"name\":\"COL2\",\"java_type\":\"java.lang.String\"," +
                                  "\"on_error\":\"ERROR GRP 3-2 !!!\"}" +
                      "]" +
                    "}," +
                    "\"static_rows\":{" +
                      "\"row\":[" +
                        "{\"cell\":[" +
                          "{\"name\":\"COL1\",\"value\":\"uUu\"}," +
                          "{\"name\":\"COL2\",\"value\":\"цЦц\"}" +
                        "]}," +
                        "{\"cell\":[" +
                          "{\"name\":\"COL1\",\"value\":\"Ddd\"}," +
                          "{\"name\":\"COL2\",\"value\":\"ЗзЗ\"}" +
                        "]}" +
                      "]" +
                    "}" +
                  "}}" +
                "}," +
                    
                // 1.3 CSV Data
                "{\"csv_ds\":" +
                  "{\"csv_data\":{" +
                    "\"columns\":{" +
                      "\"column\":[" +
                        "{\"name\":\"COL1\"," +
                                "\"java_type\":\"java.lang.String\"}," +
                        "{\"name\":\"COL2\"," +
                                "\"java_type\":\"java.lang.String\"}" +
                      "]" +
                    "}," +
                    "\"file_name\":\"filter_complex_no_header.csv\"," +
                    "\"delim\":\",\"," +
                    "\"quote_char\":\"\\\"\"," +
                    "\"escape_char\":\"\\\\\"," +
                    "\"col_first_row\":false" +
                  "}}" +
                "}," +
              
                // SQL Data
                "{\"sql_ds\":" +
                  "{\"sql_data\":{" +
                    "\"sql\":{" +
                      "\"descr\":\"Simple Select\"," +
                      "\"sql_text\":\"SELECT * FROM TEST_DATA\"" +
                    "}" +
                  "}}" +
                "}" +
              "]" +
            "}" +
          // Group DataSet #1 End
          "}}," +
              
          // Static DataSet
          "{\"static_ds\":{" +
            "\"lang_map\":{" +
              "\"column\":[" +
                "{\"name\":\"COL1\"}," +
                "{\"name\":\"COL2\"}" +
              "]" +
            "}," +
            
            "\"static_data\":{" +
              "\"columns\":{" +
                "\"column\":[" +
                  "{\"name\":\"COL1\",\"java_type\":\"java.lang.String\"," +
                          "\"on_error\":\"ERROR GRP 1 !!!\"}," +
                  "{\"name\":\"COL2\",\"java_type\":\"java.lang.String\"," +
                          "\"on_error\":\"ERROR GRP 2 !!!\"}" +
                "]" +
              "}," +
              
              "\"static_rows\":{" +
                "\"row\":[" +
                  "{\"cell\":[" +
                    "{\"name\":\"COL1\",\"value\":\"bBb\"}," +
                    "{\"name\":\"COL2\",\"value\":\"УуУ\"}" +
                  "]}," +
                  "{\"cell\":[" +
                    "{\"name\":\"COL1\",\"value\":\"AaA\"}," +
                    "{\"name\":\"COL2\",\"value\":\"пПп\"}" +
                  "]}" +
                "]" +
              "}" +
            "}" +
          "}}" +
        "]" +
      "}," +
      "\"descr\":\"Demo Map with all data set types\"," +
      "\"enabled\":true," +
      "\"ver_max\":1," +
      "\"ver_min\":0" +
  "}";

  static public String[][] DS_DEMO_MAPS = new String[][] {

      new String[] { "ds.xml", MAIN_DEMO_DS },

      new String[] { "ds_single.xml",

          "{\"ex_columns\":{\"auto_inc\":{\"column\":[{\"name\":\"A11\",\"st" +
              "art_from\":0,\"inc_by\":1},{\"name\":\"B22\",\"start_from\":0,\"i" +
              "nc_by\":1}]},\"calc\":{\"column\":[{\"value\":\"A + B\",\"name\":" +
              "\"CALC1\",\"java_type\":\"Integer\",\"stop_on_error\":true},{\"value\":\"C + D\",\"name\":\"CALC2\",\"java_type\":\"String\",\"stop_on_error\":true}]}},\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"COL1\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"NATURAL\"},{\"idx\":2,\"column\":\"COL2\",\"sequence\":\"DESC\",\"unique\":false,\"str_sort\":\"NATURAL\"}]},\"filter\":{\"value\":\"A < B\"},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{\"name\":\"COL2\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"UUu\"},{\"name\":\"COL2\",\"value\":\"ЦцЦ\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"dDd\"},{\"name\":\"COL2\",\"value\":\"ЗЗз\"}]}]}},\"descr\":\"Test DataSet\",\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] { "ds_empty.xml",

          "{\"xml_data\":{},\"descr\":\"Empty DataSet\",\"enabled\":false,\"ver_max\":1,\"ver_min\":0}" } };

  static public String[][] DS_TEST_MAPS = new String[][] { new String[] {
      "bad.csv_bad",

      "{\"csv_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.Long\",\"on_error\":\"0\"},{\"name\":\"COL2\",\"java_type\":\"java.lang.String\",\"on_error\":\"ERROR !!!\"},{\"name\":\"COL3\",\"java_type\":\"com.osbitools.util.Date\",\"on_error\":\"2000-01-01\"},{\"name\":\"COL4\",\"java_type\":\"java.lang.Integer\",\"on_error\":\"0\"},{\"name\":\"COL5\",\"java_type\":\"java.lang.Double\",\"on_error\":\"0\"},{\"name\":\"COL6\",\"java_type\":\"java.lang.Float\",\"on_error\":\"0\"}]},\"file_name\":\"test.csv\",\"delim\":\",\",\"quote_char\":\"\\\"\",\"escape_char\":\"\\\\\",\"col_first_row\":true},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] { "bad.static_test_calc_bad1",

          "{\"ex_columns\":{\"auto_inc\":{\"column\":[{\"name\":\"COL2\",\"start_from\":0,\"inc_by\":1}]},\"calc\":{\"column\":[{\"value\":\"@{COL1}/@{COL2}\",\"name\":\"COL3\",\"java_type\":\"java.lang.Integer\",\"stop_on_error\":true,\"error_value\":\"-1\"}]}},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.Integer\",\"on_error\":\"99\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"1\"}]}]}},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "bad.static_test_calc_bad2",

          "{\"ex_columns\":{\"calc\":{\"column\":[{\"value\":\"\",\"name\":\"COL3\",\"java_type\":\"xxx\",\"stop_on_error\":true}]}},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{\"name\":\"COL2\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"1\"},{\"name\":\"COL2\",\"value\":\"1\"}]}]}},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] { "bad.static_test_calc_bad3",

          "{\"ex_columns\":{\"calc\":{\"column\":[{\"value\":\"@{COL1}*@{COL1} + \\n                \\t\\t\\t\\t\\t\\t@{COL2}*@{COL2} \",\"name\":\"COL3\",\"java_type\":\"java.lang.Integer\",\"stop_on_error\":true}]}},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"ERROR !!!\"},{\"name\":\"COL2\",\"java_type\":\"java.lang.String\",\"on_error\":\"ERROR !!!\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"1\"},{\"name\":\"COL2\",\"value\":\"1\"}]}]}},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] { "test.csv_filter_complex",

          "{\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"CDATE\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"NATURAL\"},{\"idx\":2,\"column\":\"CNUM\",\"sequence\":\"DESC\",\"unique\":false,\"str_sort\":\"NATURAL\"}]},\"filter\":{\"value\":\"(new java.util.Date(\\\"01/01/2007\\\")).before(@{CDATE}) &&\\n          @{CNUM} > 4d &&\\n          (new java.util.Date(\\\"12/31/2007\\\")).after(@{CDATE}) &&\\n          @{CNUM} < 6d\"},\"csv_data\":{\"columns\":{\"column\":[{\"name\":\"CDATE\",\"java_type\":\"com.osbitools.util.Date\",\"on_error\":\"\"},{\"name\":\"CNUM\",\"java_type\":\"java.lang.Double\",\"on_error\":\"\"}]},\"file_name\":\"filter_complex.csv\",\"delim\":\",\",\"quote_char\":\"\\\"\",\"escape_char\":\"\\\\\",\"col_first_row\":true},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] { "test.csv_filter1",

          "{\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"CNUM\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"NATURAL\"},{\"idx\":2,\"column\":\"CSTR\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"COLLATOR\"}]},\"filter\":{\"value\":\"(Integer.parseInt(@{CNUM}) > 2) && \\n\\t\\t\\t\\t\\t(Integer.parseInt(@{CNUM}) < 5)\"},\"csv_data\":{\"file_name\":\"sort1.csv\",\"delim\":\",\",\"quote_char\":\"\\\"\",\"escape_char\":\"\\\\\",\"col_first_row\":true},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "test.csv_filter_complex_no_header",

          "{\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"COL4\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"NATURAL\"},{\"idx\":2,\"column\":\"COL3\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"NATURAL\"}]},\"filter\":{\"value\":\"(new java.util.Date(\\\"01/01/2007\\\")).before(@{COL4}) &&\\n          @{COL3} > 4d &&\\n          (new java.util.Date(\\\"12/31/2007\\\")).after(@{COL4}) &&\\n          @{COL3} < 6d\"},\"csv_data\":{\"columns\":{\"column\":[{\"name\":\"COL4\",\"java_type\":\"com.osbitools.util.Date\",\"on_error\":\"\"},{\"name\":\"COL3\",\"java_type\":\"java.lang.Double\",\"on_error\":\"\"}]},\"file_name\":\"filter_complex_no_header.csv\",\"delim\":\",\",\"quote_char\":\"\\\"\",\"escape_char\":\"\\\\\",\"col_first_row\":false},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] { "test.csv_filter_params",

          "{\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"CDATE\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"NATURAL\"},{\"idx\":2,\"column\":\"CSTR\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"NATURAL\"}]},\"filter\":{\"value\":\"${date_from}.before(@{CDATE}) &&\\n          @{CNUM} > ${num_from} &&\\n          ${date_to}.after(@{CDATE}) &&\\n          @{CNUM} < ${num_to}\\n    \"},\"req_params\":{\"param\":[{\"name\":\"date_from\",\"java_type\":\"com.osbitools.util.Date\",\"is_mandatory\":false},{\"name\":\"date_to\",\"java_type\":\"com.osbitools.util.Date\",\"is_mandatory\":false},{\"name\":\"num_from\",\"java_type\":\"java.lang.Double\",\"is_mandatory\":false},{\"name\":\"num_to\",\"java_type\":\"java.lang.Double\",\"is_mandatory\":false}]},\"csv_data\":{\"columns\":{\"column\":[{\"name\":\"CDATE\",\"java_type\":\"com.osbitools.util.Date\",\"on_error\":\"\"},{\"name\":\"CNUM\",\"java_type\":\"java.lang.Double\",\"on_error\":\"\"}]},\"file_name\":\"filter_complex.csv\",\"delim\":\",\",\"quote_char\":\"\\\"\",\"escape_char\":\"\\\\\",\"col_first_row\":true},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] { "test.csv_partial_num",

          "{\"csv_data\":{\"columns\":{\"column\":[{\"name\":\"YEAR\",\"java_type\":\"com.osbitools.util.Date\",\"on_error\":\"1970-01-01\"},{\"name\":\"ANNUAL_MEAN\",\"java_type\":\"java.lang.Float\",\"on_error\":\"0\"},{\"name\":\"5YEAR_MEAN\",\"java_type\":\"java.lang.Float\",\"on_error\":\"0\"}]},\"file_name\":\"partial_num.csv\",\"delim\":\",\",\"quote_char\":\"\\\"\",\"escape_char\":\"\\\\\",\"col_first_row\":true},\"descr\":\"CSV Partial Number\",\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] { "test.csv_partial_num_complex",

          "{\"ex_columns\":{\"auto_inc\":{\"column\":[{\"name\":\"INC\",\"start_from\":0,\"inc_by\":1}]}},\"csv_data\":{\"columns\":{\"column\":[{\"name\":\"YEAR\",\"java_type\":\"com.osbitools.util.Date\",\"on_error\":\"1970-01-01\"},{\"name\":\"ANNUAL_MEAN\",\"java_type\":\"java.lang.Float\",\"on_error\":\"0\"},{\"name\":\"5YEAR_MEAN\",\"java_type\":\"java.lang.Float\",\"on_error\":\"0\"}]},\"file_name\":\"partial_num.csv\",\"delim\":\",\",\"quote_char\":\"\\\"\",\"escape_char\":\"\\\\\",\"col_first_row\":true},\"descr\":\"\",\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] { "test.csv_sort1",

          "{\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"CSTR\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"NATURAL\"}]},\"csv_data\":{\"file_name\":\"sort1.csv\",\"delim\":\",\",\"quote_char\":\"\\\"\",\"escape_char\":\"\\\\\",\"col_first_row\":true},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] { "test.csv_sort2",

          "{\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"CNUM\",\"sequence\":\"DESC\",\"unique\":false,\"str_sort\":\"NATURAL\"},{\"idx\":2,\"column\":\"CSTR\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"NATURAL\"}]},\"csv_data\":{\"file_name\":\"sort1.csv\",\"delim\":\",\",\"quote_char\":\"\\\"\",\"escape_char\":\"\\\\\",\"col_first_row\":true},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] { "test.csv_sort3",

          "{\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"CSTR\",\"sequence\":\"ASC\",\"unique\":true,\"str_sort\":\"NATURAL\"}]},\"csv_data\":{\"file_name\":\"sort2.csv\",\"delim\":\",\",\"quote_char\":\"\\\"\",\"escape_char\":\"\\\\\",\"col_first_row\":true},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] { "test.csv_str1",

          "{\"csv_data\":{\"file_name\":\"sort1.csv\",\"delim\":\",\",\"quote_char\":\"\\\"\",\"escape_char\":\"\\\\\",\"col_first_row\":true},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] { "test.group_mixed",

          "{\"group_data\":{\"columns\":{\"column\":[{\"name\":\"CSTR\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{\"name\":\"CNUM\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"ds_list\":[{\"csv_ds\":{\"idx\":0,\"csv_data\":{\"columns\":{\"column\":[{\"name\":\"CSTR\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{\"name\":\"CNUM\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"file_name\":\"sort1.csv\",\"delim\":\",\",\"quote_char\":\"\\\"\",\"escape_char\":\"\\\\\",\"col_first_row\":true}}},{\"static_ds\":{\"idx\":1,\"static_data\":{\"columns\":{\"column\":[{\"name\":\"CSTR\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{\"name\":\"CNUM\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"CSTR\",\"value\":\"_UUu\"},{\"name\":\"CNUM\",\"value\":\"222\"}]},{\"cell\":[{\"name\":\"CSTR\",\"value\":\"_dDd\"},{\"name\":\"CNUM\",\"value\":\"333\"}]}]}}}},{\"csv_ds\":{\"idx\":2,\"csv_data\":{\"columns\":{\"column\":[{\"name\":\"CSTR\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{\"name\":\"CNUM\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"file_name\":\"sort1.csv\",\"delim\":\",\",\"quote_char\":\"\\\"\",\"escape_char\":\"\\\\\",\"col_first_row\":true}}}]},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "test.sql_filter_complex",

          "{\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"CDATE\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"NATURAL\"}]},\"filter\":{\"value\":\"import java.sql.Date;\\n        ${date_from}.before(@{CDATE}) &&\\n          @{CNUM} > ${num_from} &&\\n          ${date_to}.after(@{CDATE}) &&\\n          @{CNUM} < ${num_to}\\n    \"},\"req_params\":{\"param\":[{\"name\":\"date_from\",\"java_type\":\"java.util.Date\",\"is_mandatory\":false},{\"name\":\"date_to\",\"java_type\":\"java.util.Date\",\"is_mandatory\":false},{\"name\":\"num_from\",\"java_type\":\"java.lang.Double\",\"is_mandatory\":false},{\"name\":\"num_to\",\"java_type\":\"java.lang.Double\",\"is_mandatory\":false},{\"name\":\"f\",\"java_type\":\"java.lang.Integer\",\"is_mandatory\":false},{\"name\":\"t\",\"java_type\":\"java.lang.Integer\",\"is_mandatory\":false}]},\"sql_data\":{\"sql\":{\"sql_text\":\"SELECT * FROM TEST_DATA \\n                        WHERE CINT >= ? and CINT <= ?\",\"sql_params\":{\"param\":[{\"idx\":1,\"req_param\":{\"name\":\"f\",\"java_type\":\"java.lang.Integer\",\"is_mandatory\":false}},{\"idx\":2,\"req_param\":{\"name\":\"t\",\"java_type\":\"java.lang.Integer\",\"is_mandatory\":false}}]}}},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "test.sql_select",

          "{\"sql_data\":{\"sql\":{\"sql_text\":\"SELECT * FROM TEST_DATA\"}},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "test.sql_select_cond",

          "{\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"CINT\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"NATURAL\"}]},\"req_params\":{\"param\":[{\"name\":\"f\",\"java_type\":\"java.lang.Integer\",\"is_mandatory\":false},{\"name\":\"t\",\"java_type\":\"java.lang.Integer\",\"is_mandatory\":false}]},\"sql_data\":{\"sql\":{\"sql_text\":\"SELECT * FROM TEST_DATA \\n                        WHERE CINT >= ? and CINT <= ?\",\"sql_params\":{\"param\":[{\"idx\":1,\"req_param\":{\"name\":\"f\",\"java_type\":\"java.lang.Integer\",\"is_mandatory\":false}},{\"idx\":2,\"req_param\":{\"name\":\"t\",\"java_type\":\"java.lang.Integer\",\"is_mandatory\":false}}]}}},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "test.static_test_calc_date1",

          "{\"ex_columns\":{\"auto_inc\":{\"column\":[{\"name\":\"COL2\",\"start_from\":9,\"inc_by\":2},{\"name\":\"COL3\",\"start_from\":1995,\"inc_by\":1}]},\"calc\":{\"column\":[{\"value\":\"\\n            \\t(new java.util.Date(@{COL1} + \\\"/\\\" + @{COL2} + \\n            \\t\\t\\t\\t\\t\\t\\t\\t\\\"/\\\" + @{COL3})).getTime()\\n            \",\"name\":\"COL4\",\"java_type\":\"java.lang.Long\",\"stop_on_error\":true},{\"value\":\"\\n            \\tnew java.text.SimpleDateFormat(\\\"M/d/y\\\").format(\\n            \\t\\tnew java.util.Date(@{COL1} + \\\"/\\\" + @{COL2} + \\\"/\\\" + @{COL3}))\\n            \",\"name\":\"COL5\",\"java_type\":\"java.lang.String\",\"stop_on_error\":true}]}},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.Integer\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"1\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"2\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"3\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"4\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"5\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"6\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"7\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"8\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"9\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"10\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"11\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"12\"}]}]}},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "test.static_test_calc_date2",

          "{\"ex_columns\":{\"calc\":{\"column\":[{\"value\":\"\\n            \\t\\\"[\\\" + @{COL1}.getTime() + \\\"]\\\"\\n            \",\"name\":\"COL2\",\"java_type\":\"java.lang.String\",\"stop_on_error\":true}]}},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"com.osbitools.util.Date\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"2000-12-31\"}]}]}},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "test.static_test_lmap",

          "{\"lang_map\":{\"column\":[{\"name\":\"COL1\"}]},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "test.static_test_lmap_auto_inc",

          "{\"lang_map\":{\"column\":[{\"name\":\"COL1\"}]},\"ex_columns\":{\"auto_inc\":{\"column\":[{\"name\":\"COL2\",\"start_from\":-5,\"inc_by\":5},{\"name\":\"COL3\",\"start_from\":0,\"inc_by\":1}]}},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "test.static_test_lmap_calc",

          "{\"lang_map\":{\"column\":[{\"name\":\"COL1\"}]},\"ex_columns\":{\"auto_inc\":{\"column\":[{\"name\":\"COL2\",\"start_from\":-5,\"inc_by\":5},{\"name\":\"COL3\",\"start_from\":0,\"inc_by\":1}]},\"calc\":{\"column\":[{\"value\":\"@{COL2} + \\n                \\t\\t\\t\\t\\t\\t\\t\\t\\t\\t@{COL3}\",\"name\":\"COL4\",\"java_type\":\"java.lang.Integer\",\"stop_on_error\":true},{\"value\":\"@{COL2} + \\n                \\t@{COL3} + @{COL2} * 2 + @{COL2}* @{COL3} + @{COL3} * \\n                \\t@{COL3} \",\"name\":\"COL5\",\"java_type\":\"java.lang.Integer\",\"stop_on_error\":true},{\"value\":\"@{COL2}/@{COL3} \",\"name\":\"COL6\",\"java_type\":\"java.lang.Integer\",\"stop_on_error\":false,\"error_value\":\"-100\"},{\"value\":\"new Double(@{COL2})/\\n\\t\\t\\t                                     new Double(@{COL3} + 1) \",\"name\":\"COL7\",\"java_type\":\"java.lang.Double\",\"stop_on_error\":true},{\"value\":\"@{COL1} + \\\" \\\" + \\n                \\t@{COL1} + \\\" \\\" + @{COL2}*@{COL2}\",\"name\":\"COL8\",\"java_type\":\"java.lang.String\",\"stop_on_error\":true}]}},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_LETS_GO\"}]}]}},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "test.static_test_lmap_grp_complex1",

          "{\"group_data\":{\"ds_list\":[{\"group_ds\":{\"lang_map\":{\"column\":[{\"name\":\"COL1\"}]},\"ex_columns\":{\"auto_inc\":{\"column\":[{\"name\":\"CNT\",\"start_from\":0,\"inc_by\":1}]}},\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"COL1\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"NATURAL\"}]},\"group_data\":{\"ds_list\":[{\"static_ds\":{\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}},{\"static_ds\":{\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}},{\"group_ds\":{\"group_data\":{\"ds_list\":[{\"static_ds\":{\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}}]}}}]}}}]},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "test.static_test_lmap_grp_complex2",

          "{\"lang_map\":{\"column\":[{\"name\":\"COL1\"}]},\"ex_columns\":{\"auto_inc\":{\"column\":[{\"name\":\"CNT\",\"start_from\":0,\"inc_by\":1}]}},\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"COL1\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"NATURAL\"}]},\"group_data\":{\"ds_list\":[{\"group_ds\":{\"group_data\":{\"ds_list\":[{\"static_ds\":{\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}},{\"static_ds\":{\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}},{\"group_ds\":{\"group_data\":{\"ds_list\":[{\"static_ds\":{\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}}]}}}]}}}]},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "test.static_test_lmap_grp_complex3",

          "{\"group_data\":{\"ds_list\":[{\"group_ds\":{\"lang_map\":{\"column\":[{\"name\":\"COL1\"}]},\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"COL1\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"NATURAL\"}]},\"group_data\":{\"ds_list\":[{\"static_ds\":{\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}},{\"static_ds\":{\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}},{\"group_ds\":{\"group_data\":{\"ds_list\":[{\"static_ds\":{\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}}]}}}]}}}]},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "test.static_test_lmap_grp_multi",

          "{\"group_data\":{\"ds_list\":[{\"static_ds\":{\"lang_map\":{\"column\":[{\"name\":\"COL1\"}]},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}},{\"static_ds\":{\"lang_map\":{\"column\":[{\"name\":\"COL1\"}]},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}},{\"group_ds\":{\"group_data\":{\"ds_list\":[{\"static_ds\":{\"lang_map\":{\"column\":[{\"name\":\"COL1\"}]},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}}]}}}]},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "test.static_test_lmap_grp_single",

          "{\"group_data\":{\"ds_list\":[{\"static_ds\":{\"lang_map\":{\"column\":[{\"name\":\"COL1\"}]},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_USERNAME\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"LL_PASSWORD\"}]}]}}}}]},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "test.static_test_str1",

          "{\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"COL1\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"COLLATOR\"}]},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{\"name\":\"COL2\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{\"name\":\"COL3\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"A1\"},{\"name\":\"COL2\",\"value\":\"B1\"},{\"name\":\"COL3\",\"value\":\"C1\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"a2\"},{\"name\":\"COL2\",\"value\":\"b2\"},{\"name\":\"COL3\",\"value\":\"c2\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"A2\"},{\"name\":\"COL2\",\"value\":\"B2\"},{\"name\":\"COL3\",\"value\":\"C2\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"a1\"},{\"name\":\"COL2\",\"value\":\"b1\"},{\"name\":\"COL3\",\"value\":\"c1\"}]}]}},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "test.static_test_str2",

          "{\"group_data\":{\"ds_list\":[{\"group_ds\":{\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"COL1\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"NATURAL\"}]},\"group_data\":{\"ds_list\":[{\"static_ds\":{\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{\"name\":\"COL2\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{\"name\":\"COL3\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"A1\"},{\"name\":\"COL2\",\"value\":\"B1\"},{\"name\":\"COL3\",\"value\":\"C1\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"a2\"},{\"name\":\"COL2\",\"value\":\"b2\"},{\"name\":\"COL3\",\"value\":\"c2\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"A2\"},{\"name\":\"COL2\",\"value\":\"B2\"},{\"name\":\"COL3\",\"value\":\"C2\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"a1\"},{\"name\":\"COL2\",\"value\":\"b1\"},{\"name\":\"COL3\",\"value\":\"c1\"}]}]}}}}]}}}]},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

      new String[] {

          "test.static_test_str3",

          "{\"group_data\":{\"ds_list\":[{\"static_ds\":{\"sort_by_grp\":{\"sort_by\":[{\"idx\":1,\"column\":\"COL1\",\"sequence\":\"ASC\",\"unique\":false,\"str_sort\":\"NATURAL\"}]},\"static_data\":{\"columns\":{\"column\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{\"name\":\"COL2\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"},{\"name\":\"COL3\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]},\"static_rows\":{\"row\":[{\"cell\":[{\"name\":\"COL1\",\"value\":\"A1\"},{\"name\":\"COL2\",\"value\":\"B1\"},{\"name\":\"COL3\",\"value\":\"C1\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"a2\"},{\"name\":\"COL2\",\"value\":\"b2\"},{\"name\":\"COL3\",\"value\":\"c2\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"A2\"},{\"name\":\"COL2\",\"value\":\"B2\"},{\"name\":\"COL3\",\"value\":\"C2\"}]},{\"cell\":[{\"name\":\"COL1\",\"value\":\"a1\"},{\"name\":\"COL2\",\"value\":\"b1\"},{\"name\":\"COL3\",\"value\":\"c1\"}]}]}}}}]},\"enabled\":true,\"ver_max\":1,\"ver_min\":0}" },

  };

  public static final String[][] PROJ_SORT_LIST = { new String[] { "bad",

      "csv_bad,static_test_calc_bad1,static_test_calc_bad2," +
          "static_test_calc_bad3" },

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
  
  // @formatter:on

}
