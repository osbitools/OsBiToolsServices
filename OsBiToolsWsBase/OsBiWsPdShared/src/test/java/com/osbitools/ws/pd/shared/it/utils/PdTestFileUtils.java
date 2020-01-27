/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-08-04
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.pd.shared.it.utils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Set;

import com.osbitools.ws.pd.shared.common.PdSharedTestConstants;
import com.osbitools.ws.rest.prj.shared.common.TestPrjMgrBaseConstants;
import com.osbitools.ws.rest.prj.shared.it.utils.BasicJarDemoFileUtils;
import com.osbitools.ws.rest.prj.shared.it.utils.TestFileItem;
import com.osbitools.ws.wp.shared.WebPageTestResConfig;
import com.osbitools.ws.wp.shared.WpConstants;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.GenericUtils;
import com.osbitools.ws.shared.common.TestConstants;

/**
 * Generic utilities read demo maps resources out of jar file
 * 
 */

public class PdTestFileUtils extends BasicJarDemoFileUtils {
  
  // Demo component list
  private static String COMP_LIST = "{\"comp_list\":\"{\\\"ll_containers\\\":" +
      "[\\\"com.osbitools.containers.tab_box\\\", " +
      "\\\"com.osbitools.containers.header_box\\\"]}\"}";
  
  // List of test icons
  private static String ICON_FILE_LIST = "gear.gif,gear.jpeg,gear.png";
  
  private static String[][] TEST_ICON_FILES_INFO = new String[][] {
    new String[] {"gear.png", "@\\{" +
            "\"size\":736," +
            "\"read\":true," +
            "\"write\":true," +
            "\"last_modified\":\"" + TestPrjMgrBaseConstants.ISO_8601_FMT + "\"" +
        "\\}@"
    },
    new String[] {"gear.gif", "@\\{" +
            "\"size\":372," +
            "\"read\":true," +
            "\"write\":true," +
            "\"last_modified\":\"" + TestPrjMgrBaseConstants.ISO_8601_FMT + "\"" +
        "\\}@"
    },
    new String[] {"gear.jpeg", "@\\{" +
          "\"size\":688," +
          "\"read\":true," +
          "\"write\":true," +
          "\"last_modified\":\"" + TestPrjMgrBaseConstants.ISO_8601_FMT + "\"" +
        "\\}@"
    }
  };
  
  private static String TEST_GIF_SET =
    "R0lGODlhEAAQAKUzAAcHBwsLCxAQEBYWFicnKSwsLC0tLjAwMjIyM0JCRXZ2dpOTlJSUm6Gh" +
    "oKampbGxurKyvLS0tLu7u729w7+/vsLCzMTExMbGzMbG0MfHxsbG08rK1MvLyszMy8vL2M/P" +
    "1NDQz9HR0NLS0tPT1tTU09PT4tfX59nZ5Nzc3N7e3eDg4OLi4eTk5OXl5Obm5ejo9+rq6vX1" +
    "9fr6+v///////////////////////////////////////////////////yH+EUNyZWF0ZWQg" +
    "d2l0aCBHSU1QACH5BAEKAD8ALAAAAAAQABAAAAZ8wJ9wSCwaj8ihLJb8KWBCl0sIUxhVnFaE" +
    "xYqgOKpjCJRarVKgELJBchQKDlLD2KFkRAahQZShdIgWEhYjB0IHI4EWRgsfEEIQHwtHCBcT" +
    "Jy8vJxMXCEUDGA8VDBsbDBUPGANFCR4EAhoaAgQeCUcBPwEmJbe3TQAATcFCQQA7";
  
  private static String TEST_PNG_SET =
    "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9i" +
    "ZSBJbWFnZVJlYWR5ccllPAAAAoJJREFUeNqkU01oE1EQnk02iTFQE7QihUKRkKTF1iU9+FdQ" +
    "CoWYgAcPegkIeiiIWiHgwUvpQXs1Ggo99OYlFwUhWAhYhZJWUmhMxJbYYk1LFDcmJraSv911" +
    "vjQbevPgg9kZ5vu+eW9n3hM0TaP/WSI+gUCADAYDmUwmEgSBUNRoNJ5jaKjNSyuKsqRjjUaD" +
    "VFWlWCy2X0BfDJ5nd5r9KxZI0Wh0BuRgMHibcznGrrD/wD6hawwHxBdcLte12dnZGYfDcYOF" +
    "hkJBpnL5F3Y0IAcMHHB1nYAj+Xw+xHeZ8FSWf1BPTw+trqY2JElyAkilUhsej8dZKhWpu/s4" +
    "jY+P3+P0s/n5+f0TVCoVqlarL0Oh0KTZbCZZlmlgoN+pqgrBEO/u/iZg4IALTecX+BQX6/X6" +
    "9Xw+v8e7bYqiSMvLy+t+f2AGhhg5YOCAC43+7+T1eh+srCS1hYU32tJSQkun09rg4NA0TwLT" +
    "IMTIAQMHXGigbU2hVqsZq9UaNZsKKYrKoxRZKDYwKizEyAEDB1xoOk3kzo6xP4PExMT9WyMj" +
    "l/q2t7+npqYevkBucvLx1d7eE9Li4tutcPjJXEsoCO+z2WxcP0GcC3zmDt8ZHj7bVyyWyO32" +
    "SLHYOwl4ufyTdna+ELCuriN2nlSEC2x1mshdRZGbkchcSJaLfCOtFI+//prLbRIMMXLAwAEX" +
    "mk4T+ZLALo+Ojj1PJtc1t7s/bLfbHyUSGQ2GGDlg4IALTesd6Y8JY7JarX6bzTZtsVhOwq+t" +
    "fdMymZx2MAcOuPrmrSYKaDHRUbZjbIcA8sM6xQ9sADFP4xNf54/t21tnk9kKrG3qBdCLw20T" +
    "//GCFbY9tj+sVf8KMAACOoVxz9PPRwAAAABJRU5ErkJggg==";
  
  private static String TEST_JPEG_SET =
    "/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoM" +
    "DAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsN" +
    "FBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wgAR" +
    "CAAQABADAREAAhEBAxEB/8QAFgABAQEAAAAAAAAAAAAAAAAAAAUH/8QAFgEBAQEAAAAAAAAA" +
    "AAAAAAAAAAEC/9oADAMBAAIQAxAAAAHfIEjQf//EABgQAQADAQAAAAAAAAAAAAAAAAQBAwUU" +
    "/9oACAEBAAEFAl6M9BNGejSPYd+aexD/AP/EABQRAQAAAAAAAAAAAAAAAAAAACD/2gAIAQMB" +
    "AT8BH//EABYRAQEBAAAAAAAAAAAAAAAAAAABEP/aAAgBAgEBPwHIr//EAB4QAAEEAQUAAAAA" +
    "AAAAAAAAAAIBAxEhIgBBgbHx/9oACAEBAAY/AjATgBVExFbvvQNkcgSqmQrV96NxtpSbKCxm" +
    "rvnevQccaUGxkspu653vz//EABoQAAMAAwEAAAAAAAAAAAAAAAERIQAx8UH/2gAIAQEAAT8h" +
    "eKQGDoIBFqTj4QCgdABJtS9MIQzeRkLXTBhACb2Mja6Y/9oADAMBAAIAAwAAABDZL//EABQR" +
    "AQAAAAAAAAAAAAAAAAAAACD/2gAIAQMBAT8QH//EABcRAQEBAQAAAAAAAAAAAAAAAAEAMRD/" +
    "2gAIAQIBAT8Q4g2Q5f/EABkQAQEBAQEBAAAAAAAAAAAAAAERIQAxUf/aAAgBAQABPxCP1MwQ" +
    "MWqJfwq93apYKGJEsnyMTp6Hx/oK8UhjDIJw9BYfCNOoE1jtU5//2Q==";
  
  private static final HashMap<String, String> TEST_ICON_SET = 
                                      new HashMap<String, String>();
  
  static {
    TEST_ICON_SET.put("png", TEST_PNG_SET);
    TEST_ICON_SET.put("gif", TEST_GIF_SET);
    TEST_ICON_SET.put("jpeg", TEST_JPEG_SET);
  }
  
  private static final HashMap<String, String> DEMO_ICON_SET = 
                                      new HashMap<String, String>();
  
  static {
    DEMO_ICON_SET.put("png",
"iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJ" + 
"bWFnZVJlYWR5ccllPAAAAoJJREFUeNqkU01oE1EQnk02iTFQE7QihUKRkKTF1iU9+FdQCoWYgAcP" + 
"egkIeiiIWiHgwUvpQXs1Ggo99OYlFwUhWAhYhZJWUmhMxJbYYk1LFDcmJraSv911vjQbevPgg9kZ" + 
"5vu+eW9n3hM0TaP/WSI+gUCADAYDmUwmEgSBUNRoNJ5jaKjNSyuKsqRjjUaDVFWlWCy2X0BfDJ5n" + 
"d5r9KxZI0Wh0BuRgMHibcznGrrD/wD6hawwHxBdcLte12dnZGYfDcYOFhkJBpnL5F3Y0IAcMHHB1" + 
"nYAj+Xw+xHeZ8FSWf1BPTw+trqY2JElyAkilUhsej8dZKhWpu/s4jY+P3+P0s/n5+f0TVCoVqlar" + 
"L0Oh0KTZbCZZlmlgoN+pqgrBEO/u/iZg4IALTecX+BQX6/X69Xw+v8e7bYqiSMvLy+t+f2AGhhg5" + 
"YOCAC43+7+T1eh+srCS1hYU32tJSQkun09rg4NA0TwLTIMTIAQMHXGigbU2hVqsZq9UaNZsKKYrK" + 
"oxRZKDYwKizEyAEDB1xoOk3kzo6xP4PExMT9WyMjl/q2t7+npqYevkBucvLx1d7eE9Li4tutcPjJ" + 
"XEsoCO+z2WxcP0GcC3zmDt8ZHj7bVyyWyO32SLHYOwl4ufyTdna+ELCuriN2nlSEC2x1mshdRZGb" + 
"kchcSJaLfCOtFI+//prLbRIMMXLAwAEXmk4T+ZLALo+Ojj1PJtc1t7s/bLfbHyUSGQ2GGDlg4IAL" + 
"Tesd6Y8JY7JarX6bzTZtsVhOwq+tfdMymZx2MAcOuPrmrSYKaDHRUbZjbIcA8sM6xQ9sADFP4xNf" + 
"54/t21tnk9kKrG3qBdCLw20T//GCFbY9tj+sVf8KMAACOoVxz9PPRwAAAABJRU5ErkJggg==");
    
    DEMO_ICON_SET.put("gif",
"R0lGODlhEAAQAKUzAAcHBwsLCxAQEBYWFicnKSwsLC0tLjAwMjIyM0JCRXZ2dpOTlJSUm6GhoKam" + 
"pbGxurKyvLS0tLu7u729w7+/vsLCzMTExMbGzMbG0MfHxsbG08rK1MvLyszMy8vL2M/P1NDQz9HR" + 
"0NLS0tPT1tTU09PT4tfX59nZ5Nzc3N7e3eDg4OLi4eTk5OXl5Obm5ejo9+rq6vX19fr6+v//////" + 
"/////////////////////////////////////////////yH+EUNyZWF0ZWQgd2l0aCBHSU1QACH5" + 
"BAEKAD8ALAAAAAAQABAAAAZ8wJ9wSCwaj8ihLJb8KWBCl0sIUxhVnFaExYqgOKpjCJRarVKgELJB" + 
"chQKDlLD2KFkRAahQZShdIgWEhYjB0IHI4EWRgsfEEIQHwtHCBcTJy8vJxMXCEUDGA8VDBsbDBUP" + 
"GANFCR4EAhoaAgQeCUcBPwEmJbe3TQAATcFCQQA7");
    
    DEMO_ICON_SET.put("jpeg", 
"/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsK" + 
"CwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQU" + 
"FBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wgARCAAQABADAREA" + 
"AhEBAxEB/8QAFgABAQEAAAAAAAAAAAAAAAAAAAUH/8QAFgEBAQEAAAAAAAAAAAAAAAAAAAEC/9oA" + 
"DAMBAAIQAxAAAAHfIEjQf//EABgQAQADAQAAAAAAAAAAAAAAAAQBAwUU/9oACAEBAAEFAl6M9BNG" + 
"ejSPYd+aexD/AP/EABQRAQAAAAAAAAAAAAAAAAAAACD/2gAIAQMBAT8BH//EABYRAQEBAAAAAAAA" + 
"AAAAAAAAAAABEP/aAAgBAgEBPwHIr//EAB4QAAEEAQUAAAAAAAAAAAAAAAIBAxEhIgBBgbHx/9oA" + 
"CAEBAAY/AjATgBVExFbvvQNkcgSqmQrV96NxtpSbKCxmrvnevQccaUGxkspu653vz//EABoQAAMA" + 
"AwEAAAAAAAAAAAAAAAERIQAx8UH/2gAIAQEAAT8heKQGDoIBFqTj4QCgdABJtS9MIQzeRkLXTBhA" + 
"Cb2Mja6Y/9oADAMBAAIAAwAAABDZL//EABQRAQAAAAAAAAAAAAAAAAAAACD/2gAIAQMBAT8QH//E" + 
"ABcRAQEBAQAAAAAAAAAAAAAAAAEAMRD/2gAIAQIBAT8Q4g2Q5f/EABkQAQEBAQEBAAAAAAAAAAAA" + 
"AAERIQAxUf/aAAgBAQABPxCP1MwQMWqJfwq93apYKGJEsnyMTp6Hx/oK8UhjDIJw9BYfCNOoE1jt" + 
"U5//2Q==");
  };
  
  public static final String[][] PROJ_SORT_LIST = {
    new String[] {
      "test",
      
      "bar_chart,single_chart,tab_bar_chart,tab_box"
    },
    
    new String[] {
      "bad",
      
      "bad_chart,bad_container,bad_idx"
    }
  };
  
  private static final String MAIN_DEMO_WP = 
    "{\"descr\":\"Test Web Page #1\",\"ver_max\":1,\"ver_min\":0,\"inc\":2,\"" +
    "panels\":{\"panel\":[{\"wwg_cont\":[{\"uid\":1,\"idx\":0,\"class_name\":\"com" +
    ".osbitools.containers.tab_box\",\"props\":{\"prop\":[{\"name\":\"id\",\"" +
    "value\":\"wp_test\"}]},\"wwg_list\":{\"wwg_chart\":[{\"uid\":2,\"idx\":0" +
    ",\"class_name\":\"\",\"props\":{\"prop\":[{\"name\":\"height\",\"value\":\"20" +
    "0\"},{\"name\":\"width\",\"value\":\"200\"}]}}]}}]}]}}";
  
  public static String[][] TEST_WEB_PAGES = new String[][] {
    
    new String[] {"wp", MAIN_DEMO_WP},
    
    new String[] {
      "wp_containers",
      
      "{\"ver_max\":1,\"ver_min\":0,\"inc\":1,\"panels\":{\"panel\":[{\"wwg_cont\":[{\"uid\":1,\"idx\":0,\"class_name\":\"com.osbitools.containers.tab_box\",\"props\":{\"prop\":[{\"name\":\"id\",\"value\":\"com_osbitools_containers_tab_box_1\"}]},\"wwg_list\":{\"wwg_cont\":[{\"uid\":2,\"idx\":0,\"class_name\":\"com.osbitools.containers.tab_box\",\"props\":{\"prop\":[{\"name\":\"id\",\"value\":\"com_osbitools_containers_tab_box_2\"}]},\"wwg_list\":{\"wwg_cont\":[{\"uid\":5,\"idx\":0,\"class_name\":\"com.osbitools.containers.header_box\",\"props\":{\"prop\":[{\"name\":\"id\",\"value\":\"com_osbitools_containers_header_box_5\"}]}}]}}]}},{\"uid\":3,\"idx\":1,\"class_name\":\"com.osbitools.containers.header_box\",\"props\":{\"prop\":[{\"name\":\"id\",\"value\":\"com_osbitools_containers_header_box_3\"}]},\"wwg_list\":{\"wwg_cont\":[{\"uid\":4,\"idx\":0,\"class_name\":\"com.osbitools.containers.tab_box\",\"props\":{\"prop\":[{\"name\":\"id\",\"value\":\"com_osbitools_containers_tab_box_4\"}]}}]}}]}]}}"
    },
    
    new String[] {
        "wp_chart", 
        
       "{\"descr\":\"Test Web Page #1\",\"ver_max\":1,\"ver_min\":0,\"inc\":2,\"panels\":{\"panel\":[{\"wwg_chart\":[{\"uid\":2,\"idx\":0,\"class_name\":\"\",\"props\":{\"prop\":[{\"name\":\"height\",\"value\":\"200\"},{\"name\":\"width\",\"value\":\"200\"}]}}]}]}}"
    }
  };
  
  static public String[][] DEMO_WEB_PAGES = new String[][] {
    new String[] {
      "bad.bad_chart",
      
      "{\"descr\":\"Test Web Page #1\",\"ver_max\":1,\"ver_min\":0,\"inc\":2," +
      "\"panels\":{\"panel\":[{\"wwg_cont\":[{\"uid\":1,\"idx\":0,\"class_name\":" +
      "\"com.osbitools.containers.tab_box\",\"props\":{\"prop\":[{\"name\":\"" +
      "id\",\"value\":\"test\"}]},\"wwg_list\":{\"wwg_chart\":[{\"uid\":2,\"i" +
      "dx\":0,\"class_name\":\"\",\"props\":{\"prop\":[{\"name\":\"height\",\"valu" +
      "e\":\"200\"},{\"name\":\"width\",\"value\":\"200\"}]}}]}}]}]}}"
    },
    
    new String[] {
      "bad.bad_container",
      
      "{\"descr\":\"Test Web Page #1\",\"ver_max\":1,\"ver_min\":0,\"inc\":2," +
      "\"panels\":{\"panel\":[{\"wwg_cont\":[{\"uid\":1,\"idx\":0,\"class_name\":" +
      "\"com.osbitools.containers.tab_box\",\"props\":{\"prop\":[{\"name\":\"" +
      "id\",\"value\":\"test\"}]},\"wwg_list\":{\"wwg_chart\":[{\"uid\":2,\"i" +
      "dx\":0,\"class_name\":\"\",\"props\":{\"prop\":[{\"name\":\"height\",\"valu" +
      "e\":\"200\"},{\"name\":\"width\",\"value\":\"200\"}]}}]}}]}]}}"
    },
    
    new String[] {
      "bad.bad_idx",
      
      "{\"descr\":\"Test Web Page #1\",\"ver_max\":1,\"ver_min\":0,\"inc\":0," +
      "\"panels\":{\"panel\":[{\"wwg_cont\":[{\"uid\":1,\"idx\":0,\"class_name\":" +
      "\"com.osbitools.containers.tab_box\",\"props\":{\"prop\":[{\"name\":\"" +
      "id\",\"value\":\"test\"}]},\"wwg_list\":{\"wwg_chart\":[{\"uid\":2,\"i" +
      "dx\":0,\"class_name\":\"xxx\",\"props\":{\"prop\":[{\"name\":\"height\",\"v" +
      "alue\":\"200\"},{\"name\":\"width\",\"value\":\"200\"}]}}]}}]}]}}"
    },
    
    new String[] {
      "test.bar_chart",
      
      "{\"descr\":\"\",\"ver_max\":1,\"ver_min\":0,\"inc\":8,\"panels\":{\"panel\":[{\"wwg_chart\":[{\"uid\":3,\"idx\":0,\"class_name\":\"com.osbitools.charts.jqplot.bar\",\"props\":{\"prop\":[{\"name\":\"id\",\"value\":\"com_osbitools_charts_bar_3\"},{\"name\":\"size_width\",\"value\":\"300\"},{\"name\":\"size_height\",\"value\":\"300\"},{\"name\":\"db_conn\",\"value\":\"hsql\"},{\"name\":\"ds\",\"value\":\"tt.static\"},{\"name\":\"is_animate\",\"value\":\"true\"},{\"name\":\"x_axis\",\"value\":\"NAME\"}]},\"prop_groups\":{\"prop_group\":[{\"name\":\"series\",\"props\":[{\"prop\":[{\"name\":\"label\",\"value\":\"LL_ID\"},{\"name\":\"y_axis\",\"value\":\"ID\"}]},{\"prop\":[{\"name\":\"label\",\"value\":\"LL_TEST\"},{\"name\":\"y_axis\",\"value\":\"TEST\"}]}]}]}}]}]}}"
    },

    new String[] {
        "test.single_chart",
        
        "{\"panels\":{" +
          "\"panel\":[" +
            "{\"wwg_list\":[" +
              "{\"wwg_chart\":{" +
                "\"props\":{" +
                  "\"prop\":[" +
                    "{\"name\":\"id\",\"value\":\"com_osbitools_charts_bar_1\"}," +
                    "{\"name\":\"ds\",\"value\":\"tt.static\"}," +
                    "{\"name\":\"is_animate\",\"value\":\"true\"}," +
                    "{\"name\":\"x_axis\",\"value\":\"NAME\"}," +
                    "{\"name\":\"is_show_point_labels\",\"value\":\"true\"}," +
                    "{\"name\":\"legend_enabled\",\"value\":\"false\"}," +
                    "{\"name\":\"is_stack_series\",\"value\":\"false\"}]}," +
                  "\"prop_groups\":{" +
                    "\"prop_group\":[" +
                      "{\"props\":[" +
                        "{\"prop\":[" +
                          "{\"name\":\"label\",\"value\":\"LX_ID\"}," +
                          "{\"name\":\"y_axis\",\"value\":\"ID\"}," +
                          "{\"name\":\"color\",\"value\":\"#ff0000\"}]}," +
                          "{\"prop\":[{\"name\":\"label\",\"value\":\"LX_TEST\"}," +
                          "{\"name\":\"y_axis\",\"value\":\"TEST\"}]}]," +
                    "\"name\":\"series\"}]}," +
                "\"uid\":1,\"idx\":0,\"class_name\":\"com.osbitools.charts.jqplot.bar\"," +
                "\"engine\":\"jqplot\"}}]}]}," +
          "\"copyright\":\"2015 OsBiTools\",\"title\":\"LL_TOOL_TITLE\"," +
          "\"charset\":\"utf-8\",\"page_loading\":\"LL_LOADING\",\"ver_max\":1," +
          "\"ver_min\":0,\"inc\":8}"
    },
    
    new String[] {
        "test.tab_bar_chart",
        
        "{\"panels\":{" +
          "\"panel\":[" +
            "{\"wwg_list\":[" +
              "{\"wwg_container\":{" +
                "\"props\":{" +
                  "\"prop\":[" +
                    "{\"name\":\"id\",\"value\":\"com_osbitools_containers_tab_box_1\"}," +
                    "{\"name\":\"header_title\",\"value\":\"LX_BAR_CHART_DEMO\"}," +
                    "{\"name\":\"header_icon\",\"value\":\"chart_bar.png\"}]}," +
                "\"uid\":1,\"idx\":0,\"class_name\":\"com.osbitools.containers.tab_box\"," +
                "\"wwg_list\":[" +
                  "{\"wwg_chart\":{" +
                    "\"props\":{" +
                      "\"prop\":[" +
                        "{\"name\":\"id\",\"value\":\"com_osbitools_charts_bar_2\"}," +
                        "{\"name\":\"ds\",\"value\":\"tt.static\"}," +
                        "{\"name\":\"x_axis\",\"value\":\"NAME\"}]}," +
                      "\"prop_groups\":{" +
                        "\"prop_group\":[" +
                          "{\"props\":[" +
                            "{\"prop\":[" +
                              "{\"name\":\"label\",\"value\":\"LL_DEMO\"}," +
                              "{\"name\":\"y_axis\",\"value\":\"ID\"}]}]," +
                          "\"name\":\"series\"}]}," +
                    "\"uid\":2,\"idx\":0,\"class_name\":\"com.osbitools.charts.jqplot.bar\"," +
                    "\"engine\":\"jqplot\"}}]}}]}]}," +
          "\"ver_max\":1,\"ver_min\":0,\"inc\":8}"
    },

    new String[] {
      "test.tab_box",
      
      "{\"descr\":\"Test Web Page #1\",\"ver_max\":1,\"ver_min\":0,\"inc\":2,\"panels\":{\"panel\":[{\"wwg_cont\":[{\"uid\":1,\"idx\":0,\"class_name\":\"com.osbitools.containers.tab_box\",\"props\":{\"prop\":[{\"name\":\"header\",\"value\":\"test\"}]}}]}]}}"
    }
  };
  
  public PdTestFileUtils() {
    super(new WebPageTestResConfig());
  }

  @Override
  public int readDemoFileSize() throws IOException {
    return 641;
  }

  @Override
  public String[][] getProjList() {
    return PROJ_SORT_LIST;
  }

  @Override
  public String getProjWorkDir() {
    return TestConstants.WORK_OSBI_SHARED_PATH + WpConstants.WP_DIR + File.separator;
  }

  @Override
  public String getCompList() {
    return COMP_LIST;
  }

  @Override
  public String getBaseExt() {
    return PdEntityUtils.BASE_EXT;
  }

  @Override
  public String getExtFileList(String resName) {
    return ICON_FILE_LIST;
  }

  @Override
  public String[][] getExtFileTestFiles(String resName) {
    return TEST_ICON_FILES_INFO;
  }

  @Override
  public TestFileItem[] getExtFileTestSet(String resName, String fileExt) {
    String img = TEST_ICON_SET.get(fileExt);
    
    return new TestFileItem[] {
        new TestFileItem(Base64.getDecoder().decode(img), 
            "{\"base64\":\"" + img +"\"}")};
  }

  @Override
  public HashMap<String, String>[] getExtFileTestSetParams(String resName) {
    return null;
  }

  @Override
  public String[] getExtList(String resName) {
    return TEST_ICON_SET.keySet().toArray(new String[TEST_ICON_SET.size()]);
  }

  @Override
  public String[] getResDirList() {
    Set<String> set = PdEntityUtils.EXT_LIST.keySet();
    return set.toArray(new String[set.size()]);
  }

  @Override
  public byte[] getDemoExFileAsBytes(String resName, String ext) {
    return Base64.getDecoder().decode(DEMO_ICON_SET.get(ext));
  }

  @Override
  public String getDemoExFileUploadResp(String resName, String ext) {
    return "{\"base64\":\"" + TEST_ICON_SET.get(ext) + "\"}";
  }

  @Override
  public String[][] getTestEntities() {
    return PdSharedTestConstants.TEST_WEB_PAGES;
  }

  @Override
  public String getMainDemoFileJson() {
    return MAIN_DEMO_WP;
  }

  @Override
  public String[][] getDemoEntities() {
    return PdSharedTestConstants.DEMO_WEB_PAGES;
  }

  @Override
  public String getJsonPrefix() {
    return "";
  }

  @Override
  public String getJsonSuffix() {
    return "";
  }

  @Override
  public String getMimeType(String fname) {
    // Extract filename extension
    try {
      return "image/" + GenericUtils.getFileExt(fname);
    } catch (WsSrvException e) {
      return "";
    }
  }
}
