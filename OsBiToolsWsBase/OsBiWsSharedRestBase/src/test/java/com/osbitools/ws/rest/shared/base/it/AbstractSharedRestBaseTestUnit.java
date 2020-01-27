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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import com.osbitools.ws.rest.shared.base.common.FileDownloadResponse;
import com.osbitools.ws.rest.shared.config.common.TestRestConstants;
import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.shared.common.TestConstants;

/**
 * Base class for web test unit
 * 
 */

public abstract class AbstractSharedRestBaseTestUnit {

  //Encoded Dot (.)
  public static String UTF8_DOT;

  public static final String VERSION_APP_NAME = "version";

  // TODO Delete obsoleted
  // public static String VERSION_PATH = 
  // TestRestBaseConstants.SRV_BASE_URL + VERSION_APP_NAME;

  public static final WebResponse HTTP_RESP_OK = new WebResponse("");

  public static final WebResponse HTTP_RESP_BAD_REQUEST =
      new WebResponse(400, "Bad Request");

  public static final WebResponse HTTP_RESP_NOT_FOUND =
      new WebResponse(404, "Not Found");

  public static final WebResponse HTTP_RESP_NOT_ALLOWED =
      new WebResponse(405, "ERROR #99 - Method Not Allowed." +
          " INFO: No additional details for this error");

  private static final String NAME_ZZZ = "zzz";

  // Template for expected result from authorized pen test 
  //    where only get method implemented
  protected static final HashMap<String, WebResponse> AUTH_GET_TEST_RES =
      new HashMap<String, WebResponse>();

  static {
    AUTH_GET_TEST_RES.put("put",
        new WebResponse(405, "Request method 'PUT' not supported"));
    AUTH_GET_TEST_RES.put("post",
        new WebResponse(405, "Request method 'POST' not supported"));
    AUTH_GET_TEST_RES.put("delete",
        new WebResponse(405, "Request method 'DELETE' not supported"));
  }

  public AbstractSharedRestBaseTestUnit() {
    try {
      UTF8_DOT = URLEncoder.encode(".", "UTF-8");
    } catch (UnsupportedEncodingException e) {
      fail(e.getMessage());
    }
  }

  public void doTestPublicUrls() throws Exception {
    testNoNameParam();
  }

  protected String getSrvBaseUrl() {
    return "http://" + TestRestConstants.HTTP_HOST + ":" + getLocalPort() +
        "/";
  }

  protected String getSrvCtxUrl() {
    return getSrvBaseUrl() + TestRestConstants.HTTP_WEB_CTX;
  }

  protected String getSrvUrl() {
    return getSrvCtxUrl() + CommonConstants.BASE_URL + "/";
  }

  protected String getVersionPath() {
    return getSrvUrl() + VERSION_APP_NAME;
  }

  private int getLocalPort() {
    return TestRestConstants.HTTP_PORT;
  }

  /************************************************************/
  /*                SERVICE FUNCTIONS                         */
  /************************************************************/

  protected WebResponse readGet(String url) {
    return readHttpData("GET", url, "");
  }

  protected WebResponse readPost(String url) {
    return readHttpData("POST", url);
  }

  protected WebResponse readPost(String url, String params) {
    return readPost(url, params.getBytes());
  }

  protected WebResponse readPost(String url, byte[] params) {
    return readHttpData("POST", url, params, "text/plain; charset=UTF-8");
  }

  protected WebResponse readPost(String url, String params, String ctype) {
    return readHttpData("POST", url, 
        params != null ? params.getBytes() : null, ctype);
  }

  protected WebResponse readPut(String url) {
    return readHttpData("PUT", url);
  }

  protected WebResponse readPut(String url, String params) {
    return readHttpData("PUT", url, params);
  }

  protected WebResponse readPut(String url, String params, String ctype) {
    return readHttpData("PUT", url, params.getBytes(), ctype);
  }

  protected WebResponse readDelete(String url) {
    return readHttpData("DELETE", url);
  }

  protected WebResponse readHttpData(String method, String url) {
    // Use default text/plain content type
    return readHttpData(method, url, null, null);
  }

  protected WebResponse readHttpData(String method, String url, String params) {
    return readHttpData(method, url, params.getBytes());
  }

  protected WebResponse readHttpData(String method, String url, byte[] params) {
    // Use default text/plain content type
    return readHttpData(method, url, params, "text/plain; charset=UTF-8");
  }

  protected WebResponse readHttpData(String method, String url, byte[] params,
      String ctype) {
    WebResponse res;
    InputStreamReader in = null;
    HttpURLConnection conn = null;
    Boolean fout = ctype != null && params != null && params.length > 0;

    try {
      TestConstants.LOG.debug("Connecting " + url);
      conn = (HttpURLConnection) (new URL(url)).openConnection();
      conn.setDoOutput(fout);
      conn.setRequestMethod(method);

      if (ctype != null)
        conn.setRequestProperty("Content-Type", ctype);

      if (params != null)
        conn.setRequestProperty("Content-Length",
            String.valueOf(params.length));
      
      // Initiate connection
      conn.connect();

      if (fout) {
        OutputStream os = null;

        try {
          os = conn.getOutputStream();
          os.write(params);
        } catch (IOException e) {
          return new WebResponse(conn);
        } finally {
          if (os != null)
            os.close();
        }
      }

      // Response code
      int code;

      try {
        in = new InputStreamReader(conn.getInputStream());
      } catch (IOException e) {
        return new WebResponse(conn);
      }

      // Read response
      try {
        code = conn.getResponseCode();
      } catch (IOException e) {
        return null;
      }

      try {
        StringWriter out = new StringWriter();
        BaseUtils.copy(in, out);

        String msg = out.toString();
        out.close();

        in.close();

        res = new WebResponse(code,
            msg.replaceFirst("\"request_id\":\\d*", "\"request_id\":"));

        // Read and remember cookie for POST method
        if (method == "POST") {
          res.setCookie(conn.getHeaderField("Set-Cookie"));
        }

      } catch (IOException e) {
        return new WebResponse(code);
      }

    } catch (IOException e) {
      TestConstants.LOG.error("HTTP Request failed. " + e.getMessage());
      return null;
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          // Do nothing
        }
      }

      if (conn != null)
        conn.disconnect();
    }

    return res;
  }

  protected WebResponse uploadFile(String path, String fname, InputStream in)
      throws ClientProtocolException, IOException {
    return uploadFile(path, fname, in, ContentType.TEXT_PLAIN);
    
  }
  
  private WebResponse uploadFile(String path, String fname, InputStream in,
      ContentType ctype) throws ClientProtocolException, IOException {

    HttpPost post = new HttpPost(path);
    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
    StringBody fn = new StringBody(fname, ContentType.MULTIPART_FORM_DATA);

    builder.addPart("fname", fn);
    builder.addBinaryBody("file", in, ctype, fname);

    HttpClient client = HttpClientBuilder.create().build();
    HttpEntity entity = builder.build();

    post.setEntity(entity);
    HttpResponse response = client.execute(post);

    String body;
    ResponseHandler<String> handler = new BasicResponseHandler();
    try {
      body = handler.handleResponse(response);
    } catch (HttpResponseException e) {
      return new WebResponse(e.getStatusCode(), e.getMessage());
    }

    return new WebResponse(response.getStatusLine().getStatusCode(), body);
  }

  protected FileDownloadResponse downloadFile(String url) {
    return downloadFile(url, null);
  }
  
  protected FileDownloadResponse downloadFile(String url, Set<String> headerSet) {
    InputStream in = null;
    HttpURLConnection conn = null;
    FileDownloadResponse response;

    try {
      conn = (HttpURLConnection) (new URL(url)).openConnection();
      conn.setRequestMethod("GET");

      // Initiate connection
      conn.connect();
      
      try {
        in = conn.getInputStream();
      } catch (IOException e) {
        return new FileDownloadResponse(conn);
      }
      
      // Read response body
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      BaseUtils.copy(in, out);
      response = new FileDownloadResponse(out.toByteArray());
      out.close();
      
      // Check for headers to extract
      Map<String, String> headers = new HashMap<>();
          
      if (headerSet != null) {
        for (String header: headerSet)
          headers.put(header, conn.getHeaderField(header));
        response.setHeaders(headers);
      }
    } catch (IOException e) {
      return null;
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          // Do nothing
        }
      }

      if (conn != null)
        conn.disconnect();
    }

    return response;
  }

  protected void testWebResponse(WebResponse expected, WebResponse actual) {
    testWebResponse("", expected, actual);
  }

  protected void testWebResponse(String msg, WebResponse expected,
      WebResponse actual) {

    assertNotNull("Empty Expected Web Response", expected);
    assertNotNull("Empty Actual Web Response", actual);

    if (expected.getCode() != actual.getCode())
      TestConstants.LOG.error("Message: " + actual.getMsg());

    assertEquals(msg + " Code", expected.getCode(), actual.getCode());

    testMsg(msg, expected.getMsg(), actual.getMsg());
  }

  protected static void testMsg(String msg, String expected, String actual) {
    if (expected != null && !expected.isEmpty() && expected.charAt(0) == 64 &&
        expected.charAt(expected.length() - 1) == 64) {
      // Process regular expression
      Pattern p = Pattern.compile(expected.substring(1, expected.length() - 1));
      assertTrue("Checking regex " + expected + " -> " + actual,
          p.matcher(actual).matches());
    } else {
      assertEquals(msg, expected, actual);
    }
  }

  /**
   * Get base path for web application
   * 
   * @return base path for web application
   */
  protected abstract String[] getWepAppPath();

  /**
   * Flag indicated that method has Query parameters
   * 
   * @param url Request URL
   * @param method Http Method
   * 
   * @return Byte mask
   */
  protected abstract boolean hasPenTestQueryParam(String url, String method);

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
   * Get set of expected error responses for pen test. If some method not
   * included into error set than normal HTTP response expected
   * 
   * @param url
   *          URL
   * @return HashMap with error responses
   */
  protected abstract HashMap<String, WebResponse>
      getExpectedAuthPenTestRes(String url);

  protected String[] getWebAppUrl() {
    String[] urls = getWepAppPath();
    if (urls == null)
      return null;

    int len = urls.length;
    String[] res = new String[len];
    for (int i = 0; i < len; i++)
      res[i] = getSrvUrl() + urls[i];

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
      String zurl = purl + NAME_ZZZ;

      HashMap<String, WebResponse> expected = getExpectedAuthPenTestRes(url);

      for (String method : new String[] { "GET", "PUT", "POST", "DELETE" }) {
        byte ptp;
        String mt = method.toLowerCase();

        if (hasPenTestQueryParam(url, method)) {
          assertEquals("Invalid response for " + method + ": " + url,
              HTTP_RESP_BAD_REQUEST.getCode(),
              readHttpData(method, url, "").getCode());
        } else if ((ptp = getPenTestPathParamMask(url, method)) > 0) {

          if (ptp == 0b01) {
            // Only single GET implemented
            assertEquals("Invalid response for " + method + ": " + url,
                HTTP_RESP_OK.getCode(),
                readHttpData(method, url, "").getCode());
          } else {
            // Mix
            int err1 = getNoPenTestPathParamCode(url, method, false);
            int err2 = getNoPenTestPathParamCode(url, method, true);

            if (err1 > 0) {
              assertEquals("Invalid response for " + method + ": " + url, err1,
                  readHttpData(method, url, "").getCode());

              assertEquals("Invalid response for " + method + ": " + purl, err1,
                  readHttpData(method, purl, "").getCode());
            }

            if (err2 > 0)
              assertEquals("Invalid response for " + method + ": " + zurl, err2,
                  readHttpData(method, zurl, "").getCode());
          }
        } else if (expected != null && expected.containsKey(mt)) {
          assertEquals("Invalid response forL " + method + ": " + url,
              expected.get(mt).getCode(),
              readHttpData(method, url, "").getCode());
        } else {
          // Test only http returned code
          assertEquals("Invalid response forL " + method + ": " + url, 200,
              readHttpData(method, url, "").getCode());
        }
      }
    }
  }
}
