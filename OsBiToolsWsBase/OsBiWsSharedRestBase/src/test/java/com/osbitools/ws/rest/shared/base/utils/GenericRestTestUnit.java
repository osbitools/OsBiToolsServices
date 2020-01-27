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

package com.osbitools.ws.rest.shared.base.utils;

import static org.junit.Assert.*;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.osbitools.ws.rest.shared.config.common.TestRestConstants;
import com.osbitools.ws.shared.common.CommonConstants;

/**
 * Base class for web test unit
 * 
 */
public abstract class GenericRestTestUnit {

  //Encoded Dot (.)
  public static String UTF8_DOT;

  public static final String VERSION_APP_NAME = "version";

  public static String VERSION_PATH = "/" + VERSION_APP_NAME;

  // Something fuzzy
  private static final String SMTHING_FUZZY = "zzz";

  // Random port value
  private static Integer port;
  
  // Template for expected result from authorized pen test where only get method implemented
  public static final HashMap<String, Integer> ONLY_GET_ALLOW_TEST_RES =
      new HashMap<String, Integer>();

  static {
    ONLY_GET_ALLOW_TEST_RES.put("put", HttpStatus.METHOD_NOT_ALLOWED.value());
    ONLY_GET_ALLOW_TEST_RES.put("post", HttpStatus.METHOD_NOT_ALLOWED.value());
    ONLY_GET_ALLOW_TEST_RES.put("delete", HttpStatus.METHOD_NOT_ALLOWED.value());
  }

  public GenericRestTestUnit() {
    try {
      UTF8_DOT = URLEncoder.encode(".", "UTF-8");
    } catch (UnsupportedEncodingException e) {
      fail(e.getMessage());
    }
  }

  @Before
  public synchronized void initPort() {
    if (port == null) {
      port = getPort();
      getLogger().info("Starting on port " + port);
    }
  }
  
  /**
   * Get random port web app starting on
   * 
   * @return the port
   */
  protected abstract int getPort();
  
  /**
   * Get Logger
   * 
   * @return the Logger
   */
  protected abstract Logger getLogger();
  
  /**
   * Get Rest Template
   * 
   * @return RestTemplate
   */
  protected abstract TestRestTemplate getRestTemplate();

  /**
   * Get base path for web application
   * 
   * @return base path for web application
   */
  protected abstract String[] getWepAppPath();

  /**
   * Get mask showing what path parameter are used
   * The return values is one of the next:
   * 
   * 0b01 (1) - Single Get with no Pen Test Path parameters i.e /test
   * 0b10 (2)- Has Path parameters i.e /test/{var} and full/partial CRUD implementation
   * 0b11 (3) - Has both above
   * 
   * @param url Pen Test URL
   * @param method Http Method
   * 
   * @return Name of request parameter
   */
  protected abstract byte getPenTestPathParamMask(String url, String method);

  /**
   * Return http error code for the case when getPenTestPathParamMask return 2
   * 
   * @param url Pen Test URL
   * @param method Http Method
   * @param hasPathParam Flag that indicated that path parameter present
   * 
   * @return Http Error Code
   */
  protected abstract int getNoPenTestPathParamCode(String url, String method,
      boolean hasPathParam);

  /**
   * Flag showing that pen test using query parameter
   * 
   * @param url
   * @param method
   *          HTTP Method
   * @return True if url & method using url query parameters
   */
  protected abstract boolean hasPenTestQueryParam(String url, String method);

  /**
   * Get set of expected error responses for pen test. If some method not
   * included into error set than normal HTTP response expected
   * 
   * @param url
   *          URL
   * @return HashMap with error responses
   */
  protected abstract HashMap<String, Integer> getExpectedAuthPenTestRes(String url);

  @Test
  public void testPublicUrls() throws Exception {
    testNoNameParam();
  }

  @Test
  public void testVersion() throws IOException {
    checkWebResponse(VERSION_PATH, readAppVersion());
  }

  /************************************************************/
  /* SERVICE FUNCTIONS */
  /************************************************************/

  /**
   * Read App Version from WebApp version file
   * 
   * @throws IOException
   */
  protected String readAppVersion() throws IOException {
    File f = new File(TestRestConstants.WEBAPP_VERSION_PATH);
    if (!f.exists())
      fail("File '" + TestRestConstants.WEBAPP_VERSION_PATH + " not found");

    BufferedReader vf = new BufferedReader(new FileReader(f));
    String version = vf.readLine();
    vf.close();

    assertNotNull("Version in " + TestRestConstants.WEBAPP_VERSION_PATH + " file is null",
        version);
    assertNotEquals(
        "Version in " + TestRestConstants.WEBAPP_VERSION_PATH + " file is empty", version);

    return version;
  }

  public void checkWebResponse(String url, String expected) {
    checkWebResponse(null, url, expected);
  }

  public void checkWebResponse(String msg, String url, String expected) {
    ResponseEntity<String> response =
        getRestTemplate().getForEntity(CommonConstants.BASE_URL + url, String.class);

    assertEquals(msg + " - Returned status doesn't match.", HttpStatus.OK.value(),
        response.getStatusCodeValue());
    String body = response.getBody();
    assertNotNull(msg + " - NULL not expected for " + url, body);

    if (expected != null && !expected.isEmpty() && expected.charAt(0) == 64 &&
        expected.charAt(expected.length() - 1) == 64) {
      // Process regular expression
      String sp = expected.substring(1, expected.length() - 1);
      Pattern p = Pattern.compile(sp);
      assertTrue(msg + " - regex not matching:\n" + sp.replaceAll("\\\\", "") + "\n" +
          body + "\n", p.matcher(body).matches());
    } else {
      assertEquals(msg, expected, body);
    }
  }

  public boolean isResponseOk(String url) {
    ResponseEntity<String> response =
        getRestTemplate().getForEntity(CommonConstants.BASE_URL + url, String.class);

    return (response.getStatusCode() == HttpStatus.OK);
  }

  protected String[] getWebAppUrl() {
    String[] urls = getWepAppPath();
    if (urls == null)
      return null;

    int len = urls.length;
    String[] res = new String[len];
    for (int i = 0; i < len; i++)
      res[i] = CommonConstants.BASE_URL + "/" + urls[i];

    return res;
  }

  /**
   * Test Empty or non-existing name parameter with authenticated response Test
   * only work if service has sub-path parameter i.e rest/do/{it}
   * 
   * The base service url (rest/do or rest/do/) should return 404 and The full
   * service url but with wrong parameter (rest/do/xx) should return 200 with
   * "map not found" message
   * 
   * @throws Exception
   * 
   */
  protected void testNoNameParam() throws Exception {
    String[] urls = getWebAppUrl();
    if (urls == null)
      // Don't bother
      return;

    for (String url : urls) {
      String purl = url + "/";
      String zurl = purl + SMTHING_FUZZY;
      HashMap<String, Integer> expected = getExpectedAuthPenTestRes(url);

      for (String method : new String[] { "GET", "PUT", "POST", "DELETE" }) {
        String mt = method.toLowerCase();

        byte ptp;
        if (hasPenTestQueryParam(url, mt)) {
          assertEquals("Invalid response for " + method + ": " + url,
              HttpStatus.BAD_REQUEST.value(),
              getRestTemplate().exchange(url, HttpMethod.valueOf(method), null, String.class)
                  .getStatusCodeValue());
        } else if ((ptp = getPenTestPathParamMask(url, method)) > 0) {
          if (ptp == 0b01) {
            // Only single GET implemented
            assertEquals("Invalid response for " + method + ": " + url, HttpStatus.OK.value(),
                getRestTemplate().exchange(url, HttpMethod.valueOf(method), null, String.class)
                    .getStatusCodeValue());
          } else {
            // Mix
            int err1 = getNoPenTestPathParamCode(url, method, false);
            int err2 = getNoPenTestPathParamCode(url, method, true);

            if (err1 > 0) {
              // Trying non-implemented method
              assertEquals("Invalid response for " + method + ": " + url, err1,
                  getRestTemplate()
                      .exchange(url, HttpMethod.valueOf(method), null, String.class)
                      .getStatusCodeValue());

              // Trying implemented method but no path parameter
              assertEquals("Invalid response for " + method + ": " + purl, err1,
                  getRestTemplate()
                      .exchange(url, HttpMethod.valueOf(method), null, String.class)
                      .getStatusCodeValue());
            }

            if (err2 > 0)
              // Trying implemented method but wrong path parameter
              assertEquals("Invalid response for " + method + ": " + zurl, err2,
                  getRestTemplate()
                      .exchange(zurl, HttpMethod.valueOf(method), null, String.class)
                      .getStatusCodeValue());
          }
        } else if (expected != null && expected.containsKey(mt)) {
          assertEquals("Invalid response for " + method + ": " + url,
              expected.get(mt).intValue(),
              getRestTemplate().exchange(url, HttpMethod.valueOf(method), null, String.class)
                  .getStatusCodeValue());
        } else {
          // Test only http returned code
          assertEquals("Invalid response for " + method + ": " + url, 200,
              getRestTemplate().exchange(url, HttpMethod.valueOf(method), null, String.class)
                  .getStatusCodeValue());
        }
      }
    }
  }

  protected int getMixNoPenTestPathParamCode(String method,
      boolean hasPathParam) {
    return method.equals("GET")
        ? (hasPathParam ? HttpStatus.NOT_FOUND.value() : HttpStatus.OK.value())
        : (hasPathParam ? HttpStatus.NOT_FOUND.value()
            : HttpStatus.METHOD_NOT_ALLOWED.value());
  }
}
