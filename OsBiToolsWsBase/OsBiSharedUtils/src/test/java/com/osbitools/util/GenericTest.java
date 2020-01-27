/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-11-09
 * 
 * Contributors:
 * 
 */

package com.osbitools.util;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;

import org.junit.Test;

import com.osbitools.util.Date;

/**
 * Test different ID combinations
 * 
 */
public class GenericTest {
	 
	private static final String[] GOOD_DATES = {
	    "1970-01-01T00:00:00.000Z", "2016-01-11",
	    "2016-01-11T17:31:10+00:00", "2016-01-11T17:31:10Z",
      "2016-W02", "2016-W02-1", "2016-011",
	    "1950-01-01", "1950-01-01T13:05:55", 
      "1970-01-01", "1970-01-01T11:00:15",
      "1999-12-31", "1999-12-31T23:59:59",
      "2000-01-01", "2000-01-01T00:00:00",
      "2008-11-16", "2008-11-16T14:37:15"
	};
	
	private static final String[] BAD_DATES = {"20160111T173110Z"};
	
	@Test
	public void testGoodDate() throws ParseException {
	  for (String ds: GOOD_DATES)
	    assertEquals(ds, (new Date(ds)).toString());
	}
	
	@Test
  public void testBadDate() throws ParseException {
    for (String ds: BAD_DATES)
      try {
        assertEquals(ds, (new Date(ds)).toString());
        fail("Exception expected");
      } catch (java.lang.IllegalArgumentException ex) {
        // Continue
      }
  }
	
	@Test
	public void testSerialization() throws IOException {
	  Date ds = new Date("1970-01-01");
	  
	  // BufferedOutputStream out = new BufferedOutputStream(
	     //                               new ByteArrayOutputStream());
	  
	  ByteArrayOutputStream barr = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(barr);
    out.writeObject(ds);
    out.close();
    barr.close();
    
    assertEquals(417, barr.size());
	}
	
	@Test
	public void testToTime() {
	  Date ds = new Date("1970-01-01");
	  assertEquals(0, ds.getTime());
	}
}
