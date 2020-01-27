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

package com.osbitools.ws.rest.me.shared.it;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.plexus.util.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.me.shared.it.utils.CsvExFileMgrTest;
import com.osbitools.ws.me.shared.it.utils.MeTestFileUtils;
import com.osbitools.ws.rest.prj.rest.shared.it.AbstractPrjMgrWebTest;
import com.osbitools.ws.rest.shared.base.it.WebResponse;
import com.osbitools.ws.shared.Constants;

/**
 * Test DsMap Single Thread processing using web access
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT,
    value = { "spring.config.name=test" })
public class CsvExFileTest extends AbstractPrjMgrWebTest {

  private final String _bpath = getExFilePath() + "csv/";
  private final String _cpath = getExFilePath() + "col_list/csv/";
  private final String _fpath = getExFilePath() + "file_info/csv/";

  @Test
  public void testTestCsvFile() throws Exception {
    String dname = "test";
    String dpath = getExFilePath() + dname + "/";

    // Read list of test files for invalid resource folder
    testWebResponse("Read list of test files",
        new WebResponse("{\"request_id\":,\"error\":{\"id\":291," +
            "\"message\":\"Error retrieving external file list\"," +
            "\"info\":\"File filter not found for directory 'qq'\"}}"),
        readGet(dpath + "qq/"));

    // Read list of test files
    testWebResponse("Read list of test files",
        new WebResponse(MeTestFileUtils.CSV_FILE_LIST_JSON),
        readGet(dpath + "csv/"));

    for (int i = 0; i < CsvExFileMgrTest.TEST_CSV_FILES.length; i++) {
      String[] ftest = CsvExFileMgrTest.TEST_CSV_FILES[i];

      // Get parameters
      String sparam = "";
      HashMap<String, String> params = MeTestFileUtils.TEST_CSV_COL_PARAMS[i];
      for (String key : params.keySet())
        sparam += "&" + Constants.PARAM_VAL_SUFIX + key + "=" + params.get(key);

      testWebResponse("Read column list for " + ftest[0] + " " + sparam + ":",
          new WebResponse(ftest[1]), readGet(_cpath + dname + "." + ftest[0] +
              (sparam.equals("") ? "" : "?" + sparam.substring(1))));

      testWebResponse("Read test file_info for " + ftest[0],
          new WebResponse(ftest[2]), readGet(_fpath + dname + "." + ftest[0]));
    }

    // Read column list for non-existing file
    String nfile = "xxx.csv";
    testWebResponse("Wrong result reading columns that doesn't exists",
        new WebResponse(200,
            "@\\{\"request_id\":,\"error\":\\{" +
                "\"id\":1,\"message\":\"Entry not found\"," +
                "\"info\":\"Entry \\\\\\\\\\\\\".*" + nfile +
                "\\\\\\\\\\\\\" not found\"\\}\\}@"),
        readGet(_cpath + dname + "." + nfile));

    // Read file info for non-existing file
    testWebResponse("Wrong result reading file_info that doesn't exists",
        new WebResponse(200,
            "@\\{\"request_id\":,\"error\":\\{" +
                "\"id\":1,\"message\":\"Entry not found\"," +
                "\"info\":\"Entry \\\\\\\\\\\\\".*" + nfile +
                "\\\\\\\\\\\\\" not found\"\\}\\}@"),
        readGet(_fpath + dname + "." + nfile));
  }

  @Test
  public void testCsvFileCreateSimple() throws Exception {
    String dsname = "test" + getDirPrefix();

    // Create temp directory
    testWebResponse("Fail creating '" + dsname + "'", HTTP_RESP_OK,
        readPut(getProjPath() + dsname, ""));

    String prefix = dsname + ".";
    String fname = "test.csv";
    String tname = prefix + fname;

    // Try creating file with wrong extension
    testWebResponse(
        new WebResponse("@\\{\"request_id\":\\d*,\"error\":\\{\"id\":234," +
            "\"message\":\"Unsupported resource type\"," +
            "\"info\":\"Unknown resource directory 'qq'\"\\}\\}@"),
        uploadFile(getExFilePath() + "qq/" + tname, tname,
            new ByteArrayInputStream("zzz".getBytes())));

    // Try creating empty file
    testWebResponse(
        new WebResponse("{\"request_id\":,\"error\":{\"id\":299," +
            "\"message\":\"Error updating file\"," +
            "\"info\":\"Failed to store empty uploaded file\"}}"),
        readPost(_bpath + tname));

    // Test standard file upload with default parameters
    testCsvFileWriteRead("HEADER1,HEADER2\none,two\n",
        "{\"col_list\":[" +
            "{\"name\":\"HEADER1\",\"java_type\":\"java.lang.String\"}," +
            "{\"name\":\"HEADER2\",\"java_type\":\"java.lang.String\"}]}",
        tname, fname);

    // Test standard file upload with non-standard delimiter and non-first-column headers
    testCsvFileWriteRead("one;two\n",
        "{\"col_list\":[" +
            "{\"name\":\"COL1\",\"java_type\":\"java.lang.String\"}," +
            "{\"name\":\"COL2\",\"java_type\":\"java.lang.String\"}]}",
        tname, fname,
        Constants.PARAM_VAL_SUFIX + "col_first_row=false&" +
            Constants.PARAM_VAL_SUFIX + "delim=" +
            URLEncoder.encode(";", "UTF-8"));

    testWebResponse("Temp project " + dsname + " is not empty",
        new WebResponse("[]"), readGet(getProjPath() + dsname));

    testWebResponse("Fail deleting '" + dsname + "'", HTTP_RESP_OK,
        readDelete(getProjPath() + dsname));
  }

  private void testCsvFileWriteRead(String body, String response, String tname,
      String fname) throws ClientProtocolException, IOException {
    testCsvFileWriteRead(body, response, tname, fname, null);
  }

  private void testCsvFileWriteRead(String body, String response, String tname,
      String fname, String params) throws ClientProtocolException, IOException {
    // Upload multi-part file
    byte[] fbody = body.getBytes();
    InputStream in = new ByteArrayInputStream(fbody);
    testWebResponse(new WebResponse(response),
        uploadFile(
            _bpath + tname + (!StringUtils.isEmpty(params) ? "?" + params : ""),
            fname, in));

    // Read uploaded file info
    testWebResponse("Invalid file_info for demo file " + fname,
        new WebResponse("@\\{\"size\":" + fbody.length +
            ",\"read\":true,\"write\":true,\"last_modified\":\".*\"\\}@"),
        readGet(_fpath + tname));

    // Read uploaded file body
    testFileDownload(_bpath + tname, fbody, fname, "text/csv");

    // Delete file
    testWebResponse(HTTP_RESP_OK, readDelete(_bpath + tname));

  }
}
