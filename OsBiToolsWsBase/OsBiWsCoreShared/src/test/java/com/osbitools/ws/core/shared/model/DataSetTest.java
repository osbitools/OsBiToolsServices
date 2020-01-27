/*
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-01-01
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.core.shared.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.osbitools.ws.core.shared.model.DataSet;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.common.CommonConstants;

public class DataSetTest {

  private static DataSet ds;

  @BeforeClass
  public static void setUpBeforeClass() throws WsSrvException {
    ds = new DataSet(null, CommonConstants.DEFAULT_LANG);

    init();
  }

  public static void init() throws WsSrvException {
    ds.addColumn("TEXT", "java.lang.String");
    ds.addColumn("INT", "java.lang.Integer");
    ds.addColumn("DOUBLE", "java.lang.Double");
    ds.addColumn("FLOAT", "java.lang.Float");
    ds.addColumn("DATE", "java.util.Date");
    ds.addColumn("UTIL_DATE", "com.osbitools.util.Date");
  }

  @Test
  public void testWrongJavaType() {
    try {
      ds.addColumn("ZZZ", "java.util.zzz");
    } catch (WsSrvException e) {
      assertEquals(107, e.getErrorCode());
    }

    try {
      ds.createValue("ZZZ", "zzz");
    } catch (WsSrvException e) {
      assertEquals(109, e.getErrorCode());
    }
  }

  @Test
  public void testString() throws WsSrvException {
    assertEquals("text", ds.createValue("TEXT", "text").toString());
    assertEquals("1", ds.createValue("TEXT", "1").toString());
    assertEquals("1.11", ds.createValue("TEXT", "1.11").toString());
  }

  @Test
  public void testInteger() throws WsSrvException {
    try {
      ds.createValue("INT", "text");
    } catch (WsSrvException e) {
      assertEquals(110, e.getErrorCode());
    }

    try {
      ds.createValue("INT", "1.11");
    } catch (WsSrvException e) {
      assertEquals(110, e.getErrorCode());
    }

    try {
      ds.createValue("INT", "1.0");
    } catch (WsSrvException e) {
      assertEquals(110, e.getErrorCode());
    }

    assertEquals(1, ds.createValue("INT", "1"));
  }

  @Test
  public void testDouble() throws WsSrvException {
    try {
      ds.createValue("DOUBLE", "text");
    } catch (WsSrvException e) {
      assertEquals(110, e.getErrorCode());
    }

    assertEquals(1.0d, ds.createValue("DOUBLE", "1"));
    assertEquals(1.11d, ds.createValue("DOUBLE", "1.11"));
  }

  @Test
  public void testFloat() throws WsSrvException {
    try {
      ds.createValue("FLOAT", "text");
    } catch (WsSrvException e) {
      assertEquals(110, e.getErrorCode());
    }

    assertEquals(1.0f, ds.createValue("FLOAT", "1"));
    assertEquals(1.11f, ds.createValue("FLOAT", "1.11"));
  }

  @Test
  public void testDate() throws WsSrvException {
    try {
      ds.createValue("DATE", "text");
    } catch (WsSrvException e) {
      assertEquals(110, e.getErrorCode());
    }

    try {
      ds.createValue("DATE", "1");
    } catch (WsSrvException e) {
      assertEquals(110, e.getErrorCode());
    }

    testDate(0, ds.createValue("DATE", "Thu Jan 01 00:00:00 UTC 1970"));
  }

  @Test
  public void testUtilDate() throws WsSrvException {
    try {
      ds.createValue("UTIL_DATE", "text");
    } catch (WsSrvException e) {
      assertEquals(110, e.getErrorCode());
    }

    try {
      ds.createValue("UTIL_DATE", "1");
    } catch (WsSrvException e) {
      assertEquals(110, e.getErrorCode());
    }

    Object obj = ds.createValue("UTIL_DATE", "1970-01-01");
    assertEquals("com.osbitools.util.Date", obj.getClass().getName());

    com.osbitools.util.Date dts = (com.osbitools.util.Date) obj;
    assertEquals(0, dts.getTime());
  }

  private void testDate(long expected, Object date) {
    assertEquals("java.util.Date", date.getClass().getName());

    java.util.Date dts = (java.util.Date) date;
    assertEquals(expected, dts.getTime());
  }
}
