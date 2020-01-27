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

package com.osbitools.ws.rest.pd.shared.common;

/**
 * Some shared Map Editor constants 
 * 
 */
public class PdAppSharedTestConstants {

  // @formatter:off

  private static final String COMP_LIST = "\"{" +
      "\\\\\\\"ll_containers\\\\\\\":[" +
        "\\\\\\\"com.osbitools.containers.tab_box\\\\\\\"," +
        "\\\\\\\"com.osbitools.containers.header_box\\\\\\\"," +
        "\\\\\\\"com.osbitools.containers.utility\\\\\\\"]," +
      "\\\\\\\"ll_charts\\\\\\\":[" +
        "\\\\\\\"com.osbitools.charts.jqplot.line\\\\\\\"," +
        "\\\\\\\"com.osbitools.charts.jqplot.bar\\\\\\\"," +
        "\\\\\\\"com.osbitools.charts.jqplot.pie\\\\\\\"," +
        "\\\\\\\"com.osbitools.charts.jqplot.ohlc\\\\\\\"]," +
      "\\\\\\\"ll_demo\\\\\\\":[" +
        "\\\\\\\"com.osbitools.demo.tbl_hbar\\\\\\\"]," +
      "\\\\\\\"ll_data_grids\\\\\\\":[" +
        "\\\\\\\"com.osbitools.data_grid.data_tables\\\\\\\"]" +
  "}\"";
  
  public static String[][] CONFIG_LIST =  new String[][] {
        new String[] { "comp_list", "{\"comp_list\":" + COMP_LIST + "}" },
        new String[] { "minified", "{\"minified\":\"true\"}" },
        new String[] { "comp_list,minified", "{\"comp_list\":" + COMP_LIST + 
            ",\"minified\":\"true\"}" } 
  };
  
  // @formatter:on

}
