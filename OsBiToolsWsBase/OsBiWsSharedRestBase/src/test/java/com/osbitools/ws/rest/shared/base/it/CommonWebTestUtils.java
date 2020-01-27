/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2014-10-02
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.shared.base.it;

import static org.junit.Assert.*;

import java.io.*;
import java.net.*;

import com.osbitools.ws.rest.shared.config.common.TestRestConstants;
import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.shared.common.TestConstants;

/**
 * Shared utilities
 * 
 */
public abstract class CommonWebTestUtils {

  // Special static version
  public static String readGetEx(String url) throws MalformedURLException, IOException {
    String res = "";
    BufferedReader reader = null;

    try {
      HttpURLConnection conn;
      conn = (HttpURLConnection) (new URL(url)).openConnection();

      reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

      String line;

      while ((line = reader.readLine()) != null)
        res += line;

    } finally {
      if (reader != null)
        reader.close();
    }

    return res;
  }

  public static void checkReady() throws MalformedURLException, IOException, InterruptedException {
    boolean b = Boolean.parseBoolean(CommonWebTestUtils
        .readGetEx(TestRestConstants.HTTP_SRV_URL + CommonConstants.BASE_URL + "/rdy"));

    long dts = System.currentTimeMillis();
    while (!b && (System.currentTimeMillis() - dts) < TestConstants.WAIT_TIME) {
      b = Boolean
          .parseBoolean(CommonWebTestUtils.readGetEx(TestRestConstants.HTTP_SRV_URL + "/rdy"));

      if (b)
        break;

      Thread.sleep(TestConstants.RESULT_CHECK_TIME);
    }
    
    assertTrue(
            "WebApp is not ready " + "after " + TestConstants.WAIT_TIME + " msec of waiting", b);
  }
}
