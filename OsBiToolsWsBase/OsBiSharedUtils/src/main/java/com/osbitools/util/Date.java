/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2016-27-05
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.util;

import java.io.IOException;
import java.text.ParseException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

/**
 * 
 * Information from http://www.joda.org/joda-time/cal_iso.html
 *
 * <div class="section"> 
 * <h2 id="ISO8601_Java_calendar_system">ISO8601 Java calendar system</h2> 
 * <p> The ISO 8601 calendar system is the default implementation within 
 *  Joda-Time. The standard formalises the 
 *  <a href="cal_gregorian.html">Gregorian</a> 
 *  calendar system used by the modern business world.
 * </p> 
 * <p> The ISO8601 standard was created by the International Organization for 
 *  Standards based in Geneva. It aims to eliminate the risk of misinterpretting
 *  dates and times when representations are passed between systems and across 
 *  national boundaries. We are unable to provide a direct link to the standard 
 *  as it is a paid-for document. However some 
 *  <a class="externalLink" href="http://www.exit109.com/~ghealton/.dates.html
 *    #@Standards.ISO.8601.Commentary.Links">ISO8601 links</a> 
 *  may be useful. 
 * </p> 
 * <p> The ISO8601 standard is based on the <i>proleptic Gregorian calendar</i>.
 *  This makes it fully compatible with the calendar system used in most 
 *  countries today. The <i>proleptic</i> means that the Gregorian rules for 
 *  leap years are applied for all time, thus the ISO8601 standard gives 
 *  different results for dates before the year 1583 when the historic cutover 
 *  from the Julian calendar occurred.
 * </p> 
 * <p> The standard sets out a framework within which dates and times can be 
 *  represented. It offers many choices, however in reality there are three 
 *  main date representations, year month day, year dayOfYear and year week 
 *  dayOfWeek.
 * </p> 
 * <p> References </p> 
 * <ul> 
 *  <li>Calendrical Calculations - Millenium Edition - ISBN 0521777526</li> 
 *  <li><a class="externalLink" 
 *    href="http://www.exit109.com/~ghealton/.dates.html
 *    #@Standards.ISO.8601.Commentary.Links">ISO8601 links</a></li> 
 *  <li><a class="externalLink" href="http://en.wikipedia.org/wiki/ISO8601">
 *    Wikipedia - ISO Calendar</a></li> 
 * </ul> 
 * <div class="section"> 
 *  <h3 id="Month_based">Month based</h3> 
 *  <p> yyyy-mm-dd<b>T</b>HH:MM:SS.SSS<br> This is the most common format of 
 *    ISO8601 and separates the fields by dashes. The fields are:
 *  </p> 
 *  <ul> 
 *   <li>four digit year</li> 
 *   <li>two digit month, where 01 is Janurary and 12 is December</li> 
 *   <li>two digit day of month, from 01 to 31</li> 
 *   <li>two digit hour, from 00 to 23</li> 
 *   <li>two digit minute, from 00 to 59</li> 
 *   <li>two digit second, from 00 to 59</li> 
 *   <li>three decimal places for milliseconds if required</li> 
 *  </ul> This format is used in XML standards for passing dates and times. 
 * </div> 
 * <div class="section"> 
 *  <h3 id="Day_of_Year_based">Day of Year based</h3> 
 *  <p> yyyy-ddd<b>T</b>HH:MM:SS.SSS<br> This format of ISO8601 
 *    has the following fields:
 *  </p> 
 *  <ul> 
 *   <li>four digit year</li> 
 *   <li>three digit day of year, from 001 to 366</li> 
 *   <li>two digit hour, from 00 to 23</li> 
 *   <li>two digit minute, from 00 to 59</li> 
 *   <li>two digit second, from 00 to 59</li> 
 *   <li>three decimal places for milliseconds if required</li> 
 *  </ul> 
 * </div> 
 * <div class="section"> 
 *  <h3 id="Week_based">Week based</h3> 
 *  <p> yyyy-<b>W</b>ww-d<b>T</b>HH:MM:SS.SSS<br> This format of ISO8601 
 *    has the following fields: </p> 
 *  <ul> 
 *   <li>four digit weekyear, see rules below</li> 
 *   <li>two digit week of year, from 01 to 53</li> 
 *   <li>one digit day of week, from 1 to 7 
 *                      where 1 is Monday and 7 is Sunday</li> 
 *   <li>two digit hour, from 00 to 23</li> 
 *   <li>two digit minute, from 00 to 59</li> 
 *   <li>two digit second, from 00 to 59</li> 
 *   <li>three decimal places for milliseconds if required</li> 
 *  </ul> Weeks are always complete, and the first week of a year is the one 
 *    that includes the first Thursday of the year. This definition can mean 
 *    that the first week of a year starts in the previous year, and the last 
 *    week finishes in the next year. The weekyear field is defined to refer 
 *    to the year that owns the week, which may differ from the actual year. 
 * </div> 
 * 
 */
public class Date extends java.util.Date implements JsonSerializable {
  // Default Serial Version UID
  private static final long serialVersionUID = 1L;

  // JodaTime date variable
  private final DateTime _dts;
  
  // Original string
  private final String _sd;
  
  /**
   * Convert ISO 8601 date string into date
   * 
   * @param sd Date string. Can be in format 
   * 
   * @throws ParseException
   */
  public Date(String date) {
    // Remember original string
    _sd = date;
    
    _dts = new DateTime(date, DateTimeZone.UTC);
    this.setTime(_dts.getMillis());
  }
  
  @Override
  public String toString() {
    return _sd;
  }

  @Override
  public void serialize(JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeString(_sd);
  }

  @Override
  public void serializeWithType(JsonGenerator gen,
      SerializerProvider serializers, TypeSerializer typeSer)
          throws IOException {
    gen.writeString(_sd);
  }
 
}
