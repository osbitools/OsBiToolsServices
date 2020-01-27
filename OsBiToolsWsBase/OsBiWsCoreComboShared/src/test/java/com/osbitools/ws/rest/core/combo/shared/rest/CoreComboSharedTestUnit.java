/*
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-11-07
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.core.combo.shared.rest;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.osbitools.ws.core.shared.bindings.BindingTest;
import com.osbitools.ws.core.shared.common.CoreSharedTestConstants;
import com.osbitools.ws.core.shared.daemons.DsDaemonTest;
import com.osbitools.ws.core.shared.daemons.LangLabelsDaemonTest;
import com.osbitools.ws.rest.core.combo.shared.common.CoreRestTestConstants;
import com.osbitools.ws.rest.shared.base.rest.SharedRestBaseTestUnit;
import com.osbitools.ws.rest.shared.base.utils.GenericRestTestUnit;
import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.base.dto.ColumnListDto;
import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.shared.common.JarTestResourceUtils;

public abstract class CoreComboSharedTestUnit extends SharedRestBaseTestUnit {

  private static String _ds_app = "ds";
  private static String _mp_app = "maps";

  private static String _mp_path = "/" + _mp_app + "/";
  private static String _ds_path = "/" + _ds_app + "/";

  private final static HashMap<String, String> rparams =
      new HashMap<String, String>();

  // List of tested services
  private final static String[] APP_URL_LIST =
      new String[] { _mp_app, _ds_app };

  static {
    rparams.put("sql_filter_complex", "conn=hsql&date_from=03/08/2003&" +
        "date_to=03/31/2010&num_from=4&num_to=6&f=3&t=5");
    rparams.put("csv_filter_params",
        "date_from=2007-01-01&" + "date_to=2007-12-31&num_from=4&num_to=6");
    rparams.put("sql_select", "conn=hsql");
    rparams.put("sql_select_cond", "conn=hsql&f=3&t=5");
  }

  private static final String EMPTY_MAP_INFO =
      "{\"columns\":[],\"params\":{\"param\":[]}}";

  private final static String[][] STATIC_TEST_DEMO = new String[][] {
      new String[] { "csv_filter1",

          DsDaemonTest.CSV_FILTER1,

          EMPTY_MAP_INFO },

      new String[] { "csv_filter_complex",

          DsDaemonTest.CSV_FILTER_COMPLEX,

          "{\"columns\":[" +
              "{\"name\":\"CDATE\",\"java_type\":\"com.osbitools.util.Date\",\"on_error\":\"\"}," +
              "{\"name\":\"CNUM\",\"java_type\":\"java.lang.Double\",\"on_error\":\"\"}]" +
              ",\"params\":{\"param\":[]}}" },

      new String[] { "csv_filter_complex_no_header",

          DsDaemonTest.CSV_FILTER_COMPLEX_NO_HEADER,

          "{\"columns\":[" +
              "{\"name\":\"COL4\",\"java_type\":\"com.osbitools.util.Date\"," +
              "\"on_error\":\"\"}," +
              "{\"name\":\"COL3\",\"java_type\":\"java.lang.Double\"," +
              "\"on_error\":\"\"}]" + ",\"params\":{\"param\":[]}}" },

      new String[] { "csv_filter_params",

          DsDaemonTest.CSV_FILTER_COMPLEX,

          "{\"columns\":[" +
              "{\"name\":\"CDATE\",\"java_type\":\"com.osbitools.util.Date\",\"on_error\":\"\"}," +
              "{\"name\":\"CNUM\",\"java_type\":\"java.lang.Double\",\"on_error\":\"\"}]," +
              "\"params\":{\"param\":[{\"name\":\"date_from\"," +
              "\"java_type\":\"com.osbitools.util.Date\",\"is_mandatory\":false}," +
              "{\"name\":\"date_to\",\"java_type\":\"com.osbitools.util.Date\"," +
              "\"is_mandatory\":false},{\"name\":\"num_from\",\"java_type\":" +
              "\"java.lang.Double\",\"is_mandatory\":false}," +
              "{\"name\":\"num_to\",\"java_type\":\"java.lang.Double\"," +
              "\"is_mandatory\":false}]}}" },

      new String[] { "csv_partial_num",

          DsDaemonTest.CSV_PARTIAL_NUM,

          "{\"columns\":[" +
              "{\"name\":\"YEAR\",\"java_type\":\"com.osbitools.util.Date\"," +
              "\"on_error\":\"1970-01-01\"}," +
              "{\"name\":\"ANNUAL_MEAN\",\"java_type\":\"java.lang.Float\"," +
              "\"on_error\":\"0\"}," +
              "{\"name\":\"5YEAR_MEAN\",\"java_type\":\"java.lang.Float\"," +
              "\"on_error\":\"0\"}" + "]," + "\"params\":{\"param\":[]}}" },

      new String[] { "csv_partial_num_complex",

          DsDaemonTest.CSV_PARTIAL_NUM_COMPLEX,

          "{\"columns\":[" +
              "{\"name\":\"YEAR\",\"java_type\":\"com.osbitools.util.Date\"," +
              "\"on_error\":\"1970-01-01\"}," +
              "{\"name\":\"ANNUAL_MEAN\",\"java_type\":\"java.lang.Float\"," +
              "\"on_error\":\"0\"}," +
              "{\"name\":\"5YEAR_MEAN\",\"java_type\":\"java.lang.Float\"," +
              "\"on_error\":\"0\"}," +
              "{\"name\":\"INC\",\"java_type\":\"java.lang.Integer\"," +
              "\"on_error\":\"\"}" +

              "]," + "\"params\":{\"param\":[]}}" },

      new String[] { "csv_sort1",

          DsDaemonTest.CSV_SORT1,

          EMPTY_MAP_INFO },

      new String[] { "csv_sort2",

          DsDaemonTest.CSV_SORT2,

          EMPTY_MAP_INFO },

      new String[] { "csv_sort3",

          DsDaemonTest.CSV_SORT3,

          EMPTY_MAP_INFO },

      new String[] { "csv_str1",

          DsDaemonTest.CSV_STR1,

          EMPTY_MAP_INFO },

      new String[] { "group_mixed",

          DsDaemonTest.STR_STATIC_TEST_GROUP_MIXED,

          "{\"columns\":[" +
              "{\"name\":\"CSTR\",\"java_type\":\"java.lang.String\"," +
              "\"on_error\":\"\"}," +
              "{\"name\":\"CNUM\",\"java_type\":\"java.lang.String\"," +
              "\"on_error\":\"\"}]" + ",\"params\":{\"param\":[]}}" },

      new String[] { "sql_filter_complex",

          DsDaemonTest.SQL_FILTER_COMPLEX,

          "{\"columns\":[],\"params\":{\"param\":[" +
              "{\"name\":\"date_from\",\"java_type\":\"java.util.Date\"," +
              "\"is_mandatory\":false}," +
              "{\"name\":\"date_to\",\"java_type\":\"java.util.Date\"," +
              "\"is_mandatory\":false}," +
              "{\"name\":\"num_from\",\"java_type\":\"java.lang.Double\"," +
              "\"is_mandatory\":false}," +
              "{\"name\":\"num_to\",\"java_type\":\"java.lang.Double\"," +
              "\"is_mandatory\":false}," +
              "{\"name\":\"f\",\"java_type\":\"java.lang.Integer\"," +
              "\"is_mandatory\":false}," +
              "{\"name\":\"t\",\"java_type\":\"java.lang.Integer\"," +
              "\"is_mandatory\":false}]}}" },
      
      new String[] { "sql_select",

          DsDaemonTest.SQL_SELECT,

          EMPTY_MAP_INFO },

      new String[] { "sql_select_cond",

          DsDaemonTest.SQL_SELECT_COND,

          "{\"columns\":[],\"params\":{\"param\":[" +
              "{\"name\":\"f\",\"java_type\":\"java.lang.Integer\"," +
              "\"is_mandatory\":false}," +
              "{\"name\":\"t\",\"java_type\":\"java.lang.Integer\"," +
              "\"is_mandatory\":false}]}}" },

      new String[] { "static_test_calc_date1",

          DsDaemonTest.STR_STATIC_TEST_CALC_DATE1,
          "{\"columns\":[" +
              "{\"name\":\"COL1\",\"java_type\":\"java.lang.Integer\",\"on_error\":\"\"}," +
              "{\"name\":\"COL2\",\"java_type\":\"java.lang.Integer\",\"on_error\":\"\"}," +
              "{\"name\":\"COL3\",\"java_type\":\"java.lang.Integer\",\"on_error\":\"\"}," +
              "{\"name\":\"COL4\",\"java_type\":\"java.lang.Long\"}," +
              "{\"name\":\"COL5\",\"java_type\":\"java.lang.String\"}" +
              "]" + ",\"params\":{\"param\":[]}}" },

      new String[] { "static_test_calc_date2",

          DsDaemonTest.STR_STATIC_TEST_CALC_DATE2,

          "{\"columns\":[" +
              "{\"name\":\"COL1\",\"java_type\":\"com.osbitools.util.Date\",\"on_error\":\"\"}," +
              "{\"name\":\"COL2\",\"java_type\":\"java.lang.String\"}" +
              "]" + ",\"params\":{\"param\":[]}}" },

      new String[] { "static_test_lmap",

          DsDaemonTest.STR_STATIC_TEST_LMAP_SINGLE,

          "{\"columns\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\"," +
              "\"on_error\":\"\"}]" + ",\"params\":{\"param\":[]}}" },

      new String[] { "static_test_lmap_auto_inc",

          DsDaemonTest.STR_STATIC_TEST_LMAP_AUTO_INC,

          "{\"columns\":[" +
              "{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}," +
              "{\"name\":\"COL2\",\"java_type\":\"java.lang.Integer\",\"on_error\":\"\"}," +
              "{\"name\":\"COL3\",\"java_type\":\"java.lang.Integer\",\"on_error\":\"\"}" +
              "],\"params\":{\"param\":[]}}" },

      new String[] { "static_test_lmap_calc",

          DsDaemonTest.STR_STATIC_TEST_LMAP_CALC,

          "{\"columns\":[" +
              "{\"name\":\"COL1\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}," +
              "{\"name\":\"COL2\",\"java_type\":\"java.lang.Integer\",\"on_error\":\"\"}," +
              "{\"name\":\"COL3\",\"java_type\":\"java.lang.Integer\",\"on_error\":\"\"}," +
              "{\"name\":\"COL4\",\"java_type\":\"java.lang.Integer\"}," +
              "{\"name\":\"COL5\",\"java_type\":\"java.lang.Integer\"}," +
              "{\"name\":\"COL6\",\"java_type\":\"java.lang.Integer\",\"on_error\":\"-100\"}," +
              "{\"name\":\"COL7\",\"java_type\":\"java.lang.Double\"}," +
              "{\"name\":\"COL8\",\"java_type\":\"java.lang.String\"}" +
              "]" + ",\"params\":{\"param\":[]}}" },

      new String[] { "static_test_lmap_grp_complex1",

          DsDaemonTest.STR_STATIC_TEST_LMAP_GRP_COMPLEX_1,

          EMPTY_MAP_INFO },

      new String[] { "static_test_lmap_grp_complex2",

          DsDaemonTest.STR_STATIC_TEST_LMAP_GRP_COMPLEX_2,

          "{\"columns\":[" +
              "{\"name\":\"CNT\",\"java_type\":\"java.lang.Integer\"," +
              "\"on_error\":\"\"}" + "]" + ",\"params\":{\"param\":[]}}" },

      new String[] { "static_test_lmap_grp_complex3",

          DsDaemonTest.STR_STATIC_TEST_LMAP_GRP_COMPLEX_3,

          EMPTY_MAP_INFO },

      new String[] { "static_test_lmap_grp_multi",

          DsDaemonTest.STR_STATIC_TEST_LMAP_MULTI,

          EMPTY_MAP_INFO },

      new String[] { "static_test_lmap_grp_single",

          DsDaemonTest.STR_STATIC_TEST_LMAP_SINGLE,

          EMPTY_MAP_INFO },

      new String[] { "static_test_str1",

          DsDaemonTest.STR_STATIC_TEST_STR1,

          "{\"columns\":[{\"name\":\"COL1\",\"java_type\":\"java.lang.String\"," +
              "\"on_error\":\"\"}," +
              "{\"name\":\"COL2\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}," +
              "{\"name\":\"COL3\",\"java_type\":\"java.lang.String\",\"on_error\":\"\"}]" +
              ",\"params\":{\"param\":[]}}" },

      new String[] { "static_test_str2",

          DsDaemonTest.STR_STATIC_TEST_STR_SORTED,

          EMPTY_MAP_INFO },

      new String[] { "static_test_str3",

          DsDaemonTest.STR_STATIC_TEST_STR_SORTED,

          EMPTY_MAP_INFO } };

  private final static String[][] STATIC_TEST_BAD = new String[][] {
      new String[] { "csv_bad",

          "@\\{\"request_id\":\\d+,\"error\":\\{" +
              "\"id\":116,\"message\":\"Fatal error processing dataset\"," +
              "\"details\":\\[\"Unable read csv file '.*test.csv'\"," +
              "\".*test.csv \\(No such file or directory\\)\"\\]," +
              "\"error_ds\":" + "\\{\"columns\":\\[" +
              "\\{\"name\":\"COL1\",\"java_type\":\"java.lang.Long\"\\}," +
              "\\{\"name\":\"COL2\",\"java_type\":\"java.lang.String\"\\}," +
              "\\{\"name\":\"COL3\",\"java_type\":\"com.osbitools.util.Date\"\\}," +
              "\\{\"name\":\"COL4\",\"java_type\":\"java.lang.Integer\"\\}," +
              "\\{\"name\":\"COL5\",\"java_type\":\"java.lang.Double\"\\}," +
              "\\{\"name\":\"COL6\",\"java_type\":\"java.lang.Float\"\\}\\]," +
              "\"data\":\\[\\[0,\"ERROR !!!\",\"2000-01-01\"," +
                  "0,0.0,0.0\\]" +
              "\\]\\}\\}\\}@" },

      new String[] { "static_test_calc_bad1",

          "@\\{\"request_id\":\\d+,\"error\":\\{\"id\":115," +
              "\"message\":\"Fatal error processing dataset\"," +
              "\"details\":\\[\"Unable evaluate expression '@\\{COL1\\}/@\\{COL2\\}'; " +
              "Reason: '/ by zero'\",\"COL1: 1\",\"COL2: 0\"\\]," +
              "\"error_ds\":\\{" + "\"columns\":\\[" +
              "\\{\"name\":\"COL1\",\"java_type\":\"java.lang.Integer\"\\}," +
              "\\{\"name\":\"COL2\",\"java_type\":\"java.lang.Integer\"\\}," +
              "\\{\"name\":\"COL3\",\"java_type\":\"java.lang.Integer\"\\}\\]," +
              "\"data\":\\[\\[99,0,-1\\]\\]\\}\\}\\}@" },

      new String[] { "static_test_calc_bad2",

          "@\\{\"request_id\":\\d+,\"error\":\\{\"id\":107," +
              "\"message\":\"Fatal error processing dataset\"," +
              "\"details\":\\[\"Unknown Java Type: xxx\",\"xxx\"\\]\\}\\}@" },

      new String[] { "static_test_calc_bad3",

          "@\\{\"request_id\":\\d+,\"error\":" +
              "\\{\"id\":114,\"message\":\"Fatal error processing dataset\"," +
              "\"details\":\\[\"Unable compile expression '@\\{COL1\\}\\*@\\{COL1\\} \\+ " +
              "\\\\n                \\\\t\\\\t\\\\t\\\\t\\\\t\\\\t@\\{COL2\\}\\*@\\{COL2\\} '\"," +
              "\"Line 1, Column 1: Binary numeric promotion not possible on types" +
              " \\\\\"java.lang.String\\\\\" and \\\\\"java.lang.String\\\\\"\"\\]\\}\\}@" },

  };

  private static final String SQL_INFO_JSON = "{\"col_list\":[" +
      "{\"name\":\"CSTR\",\"java_type\":\"java.lang.String\"}," +
      "{\"name\":\"CINT\",\"java_type\":\"java.lang.Integer\"}," +
      "{\"name\":\"CNUM\",\"java_type\":\"java.lang.Double\"}," +
      "{\"name\":\"CDATE\",\"java_type\":\"java.util.Date\"}]}";
  
  // Lock object for static synchronization
  private static final Object _lock = new Object();

  // Directory structure ready flag
  private static boolean _fls = false;

  // Ready flag
  private boolean _rdy = false;

  @BeforeClass
  public static void initDirList() throws Exception {
    // Singleton to use under MultiWebIt
    synchronized (_lock) {
      if (!_fls) {
        // Check if old directories needs to be removed
        // AbstractResourceDaemonTest.init();

        // Initiate error list
        Class.forName("com.osbitools.ws.core.shared.common.CustErrorList");

        // Copy demo ll_set to test project
        JarTestResourceUtils.copyDemoFileToFile(
            LsConstants.LANG_SET_FILE + "_combo",
            CoreSharedTestConstants.LS_SET_TEST_FILE_PATH,
            LangLabelsDaemonTest.LS_CFG);

        _fls = true;

        // Activate Binding
        Class.forName(BindingTest.class.getName());
      }
    }
  }

  @Before
  public void testReady() throws InterruptedException {
    while (!_rdy) {

      _rdy = isResponseOk(CoreRestTestConstants.RDY_URL);

      if (_rdy)
        break;

      Thread.sleep(1000);
    }
  }

  @Test
  public void testMapList() throws Exception {
    String list = "";

    // Get list of test bad maps
    for (int i = 0; i < STATIC_TEST_BAD.length; i++)
      list += ",\"" + CoreSharedTestConstants.BAD_PRJ_PREFIX +
          STATIC_TEST_BAD[i][0] + "\"";

    // Get list of test demo maps
    for (int i = 0; i < STATIC_TEST_DEMO.length; i++)
      list += ",\"" + CoreSharedTestConstants.TEST_PRJ_PREFIX +
          STATIC_TEST_DEMO[i][0] + "\"";

    checkWebResponse("List of loaded maps doesn't match", _mp_path,
        "[" + list.substring(1) + "]");
  }

  @Test
  public void testStaticGood() throws Exception {
    // Demo set
    for (int i = 0; i < STATIC_TEST_DEMO.length; i++) {
      String map = STATIC_TEST_DEMO[i][0];
      String fname = CoreSharedTestConstants.TEST_PRJ_PREFIX + map;
      String url = _ds_path + fname;

      // Check if extra params required
      String params = rparams.get(map);
      if (params != null)
        url += "?" + params;

      // Test data set
      checkWebResponse(STATIC_TEST_DEMO[i][0] + " test", url,
          STATIC_TEST_DEMO[i][1]);

      // Test map info
      checkWebResponse(STATIC_TEST_DEMO[i][0] + " columns test",
          _mp_path + fname, STATIC_TEST_DEMO[i][2]);
    }
  }

  @Test
  public void testStaticBad() throws Exception {
    for (int i = 0; i < STATIC_TEST_BAD.length; i++) {
      checkWebResponse(
          STATIC_TEST_BAD[i][0] + " test", _ds_path +
              CoreSharedTestConstants.BAD_PRJ_PREFIX + STATIC_TEST_BAD[i][0],
          STATIC_TEST_BAD[i][1]);
    }
  }

  @Test
  public void testSqlInfo() {
    // Try no sql parameter
    getRestTemplate().getForObject(CommonConstants.BASE_URL +
            "/sql_info/columns", ColumnListDto.class);
    
    // Try empty sql parameter
    getRestTemplate().getForObject(CommonConstants.BASE_URL +
            "/sql_info/columns?sql=", ColumnListDto.class);
    
    // Try empty sql parameter
    String response =
        getRestTemplate().getForObject(CommonConstants.BASE_URL +
            "/sql_info/columns?sql=" + CoreSharedTestConstants.SQL_COL_LIST, 
                String.class);
    
    assertEquals(SQL_INFO_JSON, response);
  }

  @Override
  public String[] getWepAppPath() {
    // Join 2 arrays
    String[] list = super.getWepAppPath();
    String[] res = new String[list.length + APP_URL_LIST.length];

    for (int i = 0; i < list.length; i++)
      res[i] = list[i];
    for (int i = 0; i < APP_URL_LIST.length; i++)
      res[list.length + i] = APP_URL_LIST[i];

    return res;
  }

  @Override
  protected byte getPenTestPathParamMask(String url, String method) {
    return (byte) (url.endsWith(_ds_app) ? 0b10
        : (url.endsWith("_mp_app") ? (method.equals("GET") ? 0b11 : 0b10)
            : super.getPenTestPathParamMask(url, method)));
  }

  @Override
  protected HashMap<String, Integer> getExpectedAuthPenTestRes(String url) {
    return url.endsWith(_ds_app) || url.endsWith(_mp_app) ||
        url.endsWith(_mp_app) ? GenericRestTestUnit.ONLY_GET_ALLOW_TEST_RES
            : super.getExpectedAuthPenTestRes(url);
  }

}
