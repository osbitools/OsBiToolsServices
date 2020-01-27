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

package com.osbitools.ws.shared.common;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

/**
 * Shared utilities
 * 
 */
public abstract class SharedTestUtils {

  public static void testMsg(String msg, String expected, String actual) {
    if (expected != null && !expected.isEmpty() && expected.charAt(0) == 64 &&
        expected.charAt(expected.length() - 1) == 64) {
      // Process regular expression
      Pattern p = Pattern.compile(expected.substring(1, expected.length() - 1));
      assertTrue("Checking regex " + expected + " -> " + actual, p.matcher(actual).matches());
    } else {
      assertEquals(msg, expected, actual);
    }
  }
  
}
