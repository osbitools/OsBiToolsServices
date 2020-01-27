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

package com.osbitools.ws.pd.shared.common;

import java.util.HashMap;

import com.osbitools.ws.wp.shared.WpTestConstants;
import com.osbitools.ws.wp.shared.mapping.WpJsonMappingTest;

/**
 * Some shared Page Designer constants 
 * 
 */
public class PdSharedTestConstants {

  // @formatter:off

  //List of test icons
  public static String ICON_FILE_LIST;

  //List of test csv files in test project
  private static String[][] TEST_ICON_FILES_INFO = new String[][] {
    new String[] {"gear.png",
        "@\\{\"file_info\":\\{" +
            "\"size\":736," +
            "\"read\":true," +
            "\"write\":true," +
            "\"last_modified\":\\d*" +
          "\\}" +
        "\\}@"
    },
    new String[] {"gear.gif",
        "@\\{\"file_info\":\\{" +
            "\"size\":372," +
            "\"read\":true," +
            "\"write\":true," +
            "\"last_modified\":\\d*" +
          "\\}" +
        "\\}@"
    },
    new String[] {"gear.jpeg",
        "@\\{\"file_info\":\\{" +
            "\"size\":688," +
            "\"read\":true," +
            "\"write\":true," +
            "\"last_modified\":\\d*" +
          "\\}" +
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
  
  public static final String GEAR_PNG = "{\"base64\":\"" + TEST_PNG_SET + "\"}";
  
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
  
  // TODO Finish formatting
  public static String[][] DEMO_WEB_PAGES = new String[][] {
    
    new String[] {WpTestConstants.WP_DEMO_FNAME, WpJsonMappingTest.WP_MAIN_DEMO_JSON},
    
    new String[] {
      "wp_containers.xml",
      
      "{\"panels\":{" +
        "\"panel\":[" +
          "{\"wwg_list\":[{" +
            "\"wwg_container\":{" +
              "\"props\":{" +
                "\"prop\":[" +
                  "{\"name\":\"id\",\"value\":\"com_osbitools_containers_tab_box_1\"}" +
                "]}," +
              "\"uid\":1,\"idx\":0,\"class_name\":\"com.osbitools.containers.tab_box\"," +
              "\"wwg_list\":[{" +
                "\"wwg_container\":{" +
                  "\"props\":{" +
                    "\"prop\":[" +
                      "{\"name\":\"id\",\"value\":\"com_osbitools_containers_tab_box_2\"}" +
                    "]}," +
                  "\"uid\":2,\"idx\":0,\"class_name\":\"com.osbitools.containers.tab_box\"," +
                  "\"wwg_list\":[{" +
                    "\"wwg_container\":{" +
                      "\"props\":{" +
                        "\"prop\":[" +
                          "{\"name\":\"id\"," +
                            "\"value\":\"com_osbitools_containers_header_box_5\"}]}," +
                      "\"uid\":5,\"idx\":0," +
                        "\"class_name\":\"com.osbitools.containers.header_box\"}}]}}]}}," +
                    "{\"wwg_container\":{" +
                      "\"props\":{" +
                        "\"prop\":[" +
                          "{\"name\":\"id\"," +
                            "\"value\":\"com_osbitools_containers_header_box_3\"}]}," +
                      "\"uid\":3,\"idx\":1," +
                          "\"class_name\":\"com.osbitools.containers.header_box\"," +
                      "\"wwg_list\":[" +
                        "{\"wwg_container\":{" +
                          "\"props\":{" +
                            "\"prop\":[" +
                              "{\"name\":\"id\"," +
                                "\"value\":\"com_osbitools_containers_tab_box_4\"}]}," +
                          "\"uid\":4,\"idx\":0," +
                            "\"class_name\":\"com.osbitools.containers.tab_box\"}}]}}]}]}," +
        "\"ver_max\":1,\"ver_min\":0,\"inc\":1}"
    },
    
    new String[] {
        "wp_chart.xml", 
        
        "{\"panels\":{" +
          "\"panel\":[" +
            "{\"wwg_list\":[{" +
              "\"wwg_chart\":{" +
                "\"props\":{" +
                  "\"prop\":[" +
                    "{\"name\":\"height\",\"value\":\"200\"}," +
                    "{\"name\":\"width\",\"value\":\"200\"}" +
                  "]}," +
                "\"uid\":2,\"idx\":0,\"class_name\":\"\"}}]}]}," +
          "\"descr\":\"Test Web Page #1\"," +
          "\"ver_max\":1," +
          "\"ver_min\":0," +
          "\"inc\":2" +
        "}"
    }
  };
  
  static public String[][] TEST_WEB_PAGES = new String[][] {
    new String[] {
      "bad.bad_chart",
      
      "{\"panels\":{" +
        "\"panel\":[" +
          "{\"wwg_list\":[{" +
            "\"wwg_container\":{\"props\":{\"prop\":[{\"name\":\"id\",\"value\":\"test\"}]},\"uid\":1,\"idx\":0,\"class_name\":\"com.osbitools.containers.tab_box\",\"wwg_list\":[{\"wwg_chart\":{\"props\":{\"prop\":[{\"name\":\"height\",\"value\":\"200\"},{\"name\":\"width\",\"value\":\"200\"}]},\"uid\":2,\"idx\":0,\"class_name\":\"\"}}]}}]}]},\"descr\":\"Test Web Page #1\",\"ver_max\":1,\"ver_min\":0,\"inc\":2}"
    },
    
    new String[] {
      "bad.bad_container",
      
      "{\"panels\":{" +
        "\"panel\":[" +
          "{\"wwg_list\":[{" +
            "\"wwg_container\":{\"props\":{\"prop\":[{\"name\":\"id\",\"value\":\"test\"}]},\"uid\":1,\"idx\":0,\"class_name\":\"com.osbitools.containers.tab_box\",\"wwg_list\":[{\"wwg_chart\":{\"props\":{\"prop\":[{\"name\":\"height\",\"value\":\"200\"},{\"name\":\"width\",\"value\":\"200\"}]},\"uid\":2,\"idx\":0,\"class_name\":\"\"}}]}}]}]},\"descr\":\"Test Web Page #1\",\"ver_max\":1,\"ver_min\":0,\"inc\":2}"
    },
    
    new String[] {
      "bad.bad_idx",
      
      "{\"panels\":{" +
        "\"panel\":[" +
          "{\"wwg_list\":[{" +
            "\"wwg_container\":{" +
              "\"props\":{" +
                "\"prop\":[" +
                  "{\"name\":\"id\",\"value\":\"test\"}]}," +
              "\"uid\":1,\"idx\":0,\"class_name\":\"com.osbitools.containers.tab_box\"," +
                "\"wwg_list\":[" +
                  "{\"wwg_chart\":{" +
                    "\"props\":{" +
                      "\"prop\":[" +
                        "{\"name\":\"height\",\"value\":\"200\"}," +
                        "{\"name\":\"width\",\"value\":\"200\"}]}," +
                    "\"uid\":2,\"idx\":0,\"class_name\":\"xxx\"}}]}}]}]}," +
        "\"descr\":\"Test Web Page #1\",\"ver_max\":1,\"ver_min\":0,\"inc\":0}"
    },
    
    new String[] {
      "test.bar_chart",
      
      "{\"panels\":{" +
        "\"panel\":[" +
          "{\"wwg_list\":[{" +
            "\"wwg_chart\":{\"props\":{\"prop\":[{\"name\":\"id\",\"value\":\"com_osbitools_charts_bar_3\"},{\"name\":\"size_width\",\"value\":\"300\"},{\"name\":\"size_height\",\"value\":\"300\"},{\"name\":\"db_conn\",\"value\":\"hsql\"},{\"name\":\"ds\",\"value\":\"tt.static\"},{\"name\":\"is_animate\",\"value\":\"true\"},{\"name\":\"x_axis\",\"value\":\"NAME\"}]},\"prop_groups\":{\"prop_group\":[{\"props\":[{\"prop\":[{\"name\":\"label\",\"value\":\"LL_ID\"},{\"name\":\"y_axis\",\"value\":\"ID\"}]},{\"prop\":[{\"name\":\"label\",\"value\":\"LL_TEST\"},{\"name\":\"y_axis\",\"value\":\"TEST\"}]}],\"name\":\"series\"}]},\"uid\":3,\"idx\":0,\"class_name\":\"com.osbitools.charts.jqplot.bar\"}}]}]},\"descr\":\"\",\"ver_max\":1,\"ver_min\":0,\"inc\":8}"
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
      
      "{\"panels\":{" +
        "\"panel\":[" +
          "{\"wwg_list\":[{" +
            "\"wwg_container\":{" +
              "\"props\":{" +
                "\"prop\":[" +
                  "{\"name\":\"header\",\"value\":\"test\"}]}," +
              "\"uid\":1,\"idx\":0,\"class_name\":\"com.osbitools.containers.tab_box\"}}]}]}," +
              "\"descr\":\"Test Web Page #1\",\"ver_max\":1,\"ver_min\":0,\"inc\":2}"
    }
  };

  // @formatter:on
  
  static {
    String flist = "";
    for (String[] fs : TEST_ICON_FILES_INFO)
      flist += "," + fs[0];

    ICON_FILE_LIST = flist.substring(1);
  }

}
