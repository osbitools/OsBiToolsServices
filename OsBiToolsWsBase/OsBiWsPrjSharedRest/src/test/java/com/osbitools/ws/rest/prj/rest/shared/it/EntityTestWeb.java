/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-10-02
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.rest.shared.it;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import com.osbitools.ws.rest.prj.shared.common.TestPrjMgrBaseConstants;
import com.osbitools.ws.rest.shared.base.it.WebResponse;
import com.osbitools.ws.shared.common.GenericTest;

/**
 * Test Entity Single Thread processing using web access
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT,
    value = { "spring.config.name=test" })
public class EntityTestWeb extends AbstractPrjMgrWebTest {

  @Test
  public void testEntityBadId() throws Exception {
    for (int i = 0; i < GenericTest.BAD_ID_LIST.length; i++) {
      String id = GenericTest.BAD_ID_LIST[i];

      String ide = URLEncoder.encode(id, "UTF-8");

      testWebResponse("Get '" + id + "'", new WebResponse(404),
          readGet(getEntityPath() + ide));

      testWebResponse("Get '" + id + "." + id + "'", new WebResponse(404),
          readGet(getEntityPath() + ide + UTF8_DOT + ide));

      testWebResponse("Get '." + id + "'", new WebResponse(404),
          readGet(getEntityPath() + UTF8_DOT + ide));

      testWebResponse("Get ../'" + id + "'", new WebResponse(400),
          readGet(getEntityPath() + URLEncoder.encode("../", "UTF-8") + ide));
    }

  }

  @Test
  public void testEntityBadNames() throws Exception {
    for (int i = 0; i < GenericTest.BAD_ID_NAMES.length; i++) {
      String id = GenericTest.BAD_ID_NAMES[i];
      String ide = URLEncoder.encode(id, "UTF-8");

      // Try bad id as project name
      testWebResponse("Get '" + id + "'", new WebResponse(404, null),
          readGet(getEntityPath() + ide));

      // Try bad id as project and entity names
      testWebResponse("#" + i + " Get '" + id + "." + id + "'", new WebResponse(404),
          readGet(getEntityPath() + ide + UTF8_DOT + ide));

      testWebResponse("Get '." + id + "'", new WebResponse(404, null),
          readGet(getEntityPath() + UTF8_DOT + ide));

      testWebResponse("Get ../'" + id + "'", new WebResponse(400, null),
          readGet(getEntityPath() + URLEncoder.encode("../", "UTF-8") + ide));
    }

    testWebResponse(
        "Wrong result reading project name '" + TestPrjMgrBaseConstants.TEST_PRJ_NAME + "'",
        new WebResponse(404),
        //TODO Delete obsoleted
        /*
        new WebResponse(200,
            "{\"request_id\":,\"error\":{\"id\":6,\"message\":\"Invalid File Name\"," +
                "\"info\":\"Extension not found in name \\\\\\\"test\\\\\\\"\"}}"),
        */
        readGet(getEntityPath() + TestPrjMgrBaseConstants.TEST_PRJ_NAME));
  }

  @Test
  public void testTestEntityGood() throws Exception {

    // Test both Test Set and Demo Set
    String[][][] dset =
        new String[][][] { FUTILS.getJsonTestSetFull(), FUTILS.getJsonDemoSetFull() };

    for (String[][] jset : dset)
      for (int i = 0; i < jset.length; i++)
        testWebResponse("Fail getting " + jset[i][0] + " file", new WebResponse(jset[i][1]),
            readGet(getEntityPath() + jset[i][0]));
  }

  @Test
  public void testEntityCreateSimple() throws Exception {
    String dsname = TestPrjMgrBaseConstants.TEST_PRJ_NAME + getDirPrefix();

    // Create temp directory
    testWebResponse("Fail creating '" + dsname + "'", HTTP_RESP_OK,
        readPut(getProjPath() + dsname, ""));

    String prefix = dsname + ".";

    String[][] jset = FUTILS.getJsonTestSet();
    String[][] jfs = FUTILS.getJsonTestSetFull();

    // Try creating empty plain text file
    testWebResponse("Wrong result creating empty entity", new WebResponse(415),
        readPut(getEntityPath() + prefix + "qq", ""));

    // Try creating empty xml file
    testWebResponse("Wrong result creating empty entity", new WebResponse(400),
        readPut(getEntityPath() + prefix + "qq", "", MediaType.APPLICATION_JSON_UTF8_VALUE));

    for (String fname : new String[] { "ds1", "dst" }) {
      // Create entity
      testWebResponse("Fail creating entity " + prefix + fname, HTTP_RESP_OK,
          readPut(getEntityPath() + prefix + fname, jset[0][1],
              MediaType.APPLICATION_JSON_UTF8_VALUE));

      // Read and compare it's the same
      testWebResponse("Fail reading entity " + prefix + fname, new WebResponse(jfs[0][1]),
          readGet(getEntityPath() + prefix + fname));
    }

    // Try create project with same name
    for (String fname : new String[] { "ds1", "dst" })
      testWebResponse("Create " + fname,
          new WebResponse(200,
              "@\\{\"request_id\":,\"error\":\\{\"id\":2," +
                  "\"message\":\"Entry already exists\"," +
                  "\"info\":\"Entry \\\\\\\\\\\\\".*" + dsname + "/" + fname + "." +
                  FUTILS.getBaseExt() + "\\\\\\\\\\\\\" already exists\"\\}\\}@"),
          readPut(getEntityPath() + prefix + fname, jset[0][1],
              MediaType.APPLICATION_JSON_UTF8_VALUE));

    // Test create result
    for (String fname : new String[] { "ds1", "dst" })
      testWebResponse("Fail reading entity " + prefix + fname, new WebResponse(jfs[0][1]),
          readGet(getEntityPath() + prefix + fname));

    // Update entities
    for (String fname : new String[] { "ds1", "dst" }) {
      // First update entity
      testWebResponse("Fail updating entity " + prefix + fname, HTTP_RESP_OK,
          readPost(getEntityPath() + prefix + fname, jset[1][1],
              MediaType.APPLICATION_JSON_UTF8_VALUE));

      // Than read and compare it's the same
      testWebResponse("Fail updating entity " + prefix + fname, new WebResponse(jfs[1][1]),
          readGet(getEntityPath() + prefix + fname));
    }

    // Test update result result
    for (String fname : new String[] { "ds1", "dst" })
      testWebResponse("Fail reading entity " + prefix + fname, new WebResponse(jfs[1][1]),
          readGet(getEntityPath() + prefix + fname));

    // Try rename to itself
    testWebResponse("Renaming ds1" + " to itself",
        new WebResponse("@\\{\"request_id\":,\"error\":\\{\"id\":18," +
            "\"message\":\"Can not rename file to itself\"," +
            "\"info\":\"Can not rename file \\\\\\\\\\\\\".*" + dsname + "/ds1" + "." +
            FUTILS.getBaseExt() + "\\\\\\\\\\\\\" to itself\"\\}\\}@"),
        readPost(getEntityPath() + prefix + "ds1" + "/" + prefix + "ds1", ""));

    // Try rename project with name of non-exists file
    testWebResponse(
        new WebResponse("@\\{\"request_id\":,\"error\":\\{\"id\":1," +
            "\"message\":\"Entry not found\"," + "\"info\":\"Entry \\\\\\\\\\\\\".*" + dsname +
            "/ds2." + FUTILS.getBaseExt() + "\\\\\\\\\\\\\" not found\"\\}\\}@"),
        readPost(getEntityPath() + prefix + "ds2" + "/" + prefix + "ds2", ""));

    // Try rename project with invalid destination file == project name
    testWebResponse(new WebResponse(404),
        readPost(getEntityPath() + prefix + "ds1" + "/" + dsname, ""));

    // Try rename project with invalid destination file
    testWebResponse(new WebResponse(404),
        readPost(getEntityPath() + prefix + "ds1" + "/qq", ""));

    // Try rename project with empty parameter
    testWebResponse(new WebResponse(415),
        readPost(getEntityPath() + prefix + "ds1" + "/", ""));

    // Renaming ds1 -> ds2
    testWebResponse("Fail renaming ds1" + " -> ds2", HTTP_RESP_OK,
        readPost(getEntityPath() + prefix + "ds1" + "/" + prefix + "ds2", ""));

    // Deleting project with wrong name
    testWebResponse(
        new WebResponse("@\\{\"request_id\":,\"error\":\\{\"id\":1," +
            "\"message\":\"Entry not found\"," + "\"info\":\"Entry \\\\\\\\\\\\\".*" + dsname +
            "/ds1." + FUTILS.getBaseExt() + "\\\\\\\\\\\\\" not found\"\\}\\}@"),
        readDelete(getEntityPath() + prefix + "ds1"));

    // Deleting final entity
    testWebResponse(HTTP_RESP_OK, readDelete(getEntityPath() + prefix + "ds2"));

    // Deleting source entity
    testWebResponse(HTTP_RESP_OK, readDelete(getEntityPath() + prefix + "dst"));

    // Upload demo file as temp name
    final String fname = prefix + "tmp";
    String[][] dset = FUTILS.getDemoSet();
    String[][] djson = FUTILS.getJsonDemoSetFull();

    for (int i = 0; i < 2; i++)
      checkUploadDownload(fname, dset[i][1], djson[i][1], i > 0);

    // Delete temp upload file 
    testWebResponse(HTTP_RESP_OK, readDelete(getEntityPath() + fname));

    // Checking if temp project is empty
    testWebResponse("Temp project " + dsname + " is not empty", new WebResponse("[]"),
        readGet(getProjPath() + dsname));

    // Deleting temp project
    testWebResponse("Fail deleting '" + dsname + "'", HTTP_RESP_OK,
        readDelete(getProjPath() + dsname));
  }

  private void checkUploadDownload(String fname, String text, String json, boolean overwrite)
      throws ClientProtocolException, IOException {
    InputStream in = new ByteArrayInputStream(text.getBytes());
    testWebResponse(new WebResponse(json),
        uploadFile(getEntityPath() + "upload/" + fname + (overwrite ? "?overwrite=true" : ""),
            fname, in));

    // Download file and compare with original
    testWebResponse("Download entity " + fname + " doesn't match original.",
        new WebResponse(text),
        readGet(getEntityPath() + "download/" + fname + "." + FUTILS.getBaseExt()));
  }
}
