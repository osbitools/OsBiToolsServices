/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
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

package com.osbitools.ws.shared.common;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Common Constants for all OsBiTools  Services
 * 
 */

public class CommonConstants {

  // Base URL
  public static final String BASE_URL = "/api/v2";

  // Default language
  public static final String DEFAULT_LANG = "en";

  //Array of known locales
  public static Map<String, Locale> LOCALES = new LinkedHashMap<String, Locale>();

  static {
    LOCALES.put("en", Locale.US);
    LOCALES.put("fr", Locale.CANADA_FRENCH);
    LOCALES.put("ru", new Locale("ru", "RU"));
    LOCALES.put("es", new Locale("es", "ES"));
    LOCALES.put("de", new Locale("de", "DE"));
  }
}
