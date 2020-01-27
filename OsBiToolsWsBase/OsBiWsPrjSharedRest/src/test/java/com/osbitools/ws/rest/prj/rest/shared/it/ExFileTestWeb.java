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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLEncoder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.rest.shared.base.it.WebResponse;
import com.osbitools.ws.shared.common.GenericTest;

/**
 * Test DsMap Single Thread processing using web access
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT,
    value = { "spring.config.name=test" })
public class ExFileTestWeb extends AbstractPrjMgrWebTest {

  @Test
  public void testExFileBadId() throws Exception {

    for (String rdir : FUTILS.getResDirList()) {
      String ext = getFirstExt(rdir);
      String fpath = getExFilePath() + rdir + "/";

      // Swith project and resource directory in url
      testWebResponse("Get invalid directory list", new WebResponse("{" + 
            "\"request_id\":," + 
            "\"error\":{" + 
              "\"id\":291," + 
              "\"message\":\"Error retrieving external file list\"," + 
              "\"info\":\"File filter not found for directory 'test'\"" + 
          "}}"),
          readGet(fpath + "test"));

      for (int i = 0; i < GenericTest.BAD_ID_LIST.length; i++) {
        String id = GenericTest.BAD_ID_LIST[i];
        String ide = URLEncoder.encode(id, "UTF-8");

        testWebResponse("Get '" + id + "'", new WebResponse(404), readGet(fpath + ide));

        testWebResponse("Get '" + id + "." + id + "'",
            new WebResponse(404),
            readGet(fpath + ide + UTF8_DOT + ide));

        testWebResponse("Get '" + id + "." + id + "." + ext + "'",
            new WebResponse(404),
            readGet(fpath + ide + UTF8_DOT + ide + UTF8_DOT + ext));
        
        testWebResponse("Get '." + id + "'",
            new WebResponse(404),
            readGet(fpath + UTF8_DOT + ide));

        testWebResponse("Get ../'" + id + "'",
            new WebResponse(400),
            readGet(fpath + URLEncoder.encode("../", "UTF-8") + ide));
      }
    }
  }

  @Test
  public void testExFileBadNames() throws Exception {

    for (String rdir : FUTILS.getResDirList()) {
      String ext = getFirstExt(rdir);
      String fpath = getExFilePath() + rdir + "/";

      for (int i = 0; i < GenericTest.BAD_ID_NAMES.length; i++) {
        String id = GenericTest.BAD_ID_NAMES[i];
        String ide = URLEncoder.encode(id, "UTF-8");

        testWebResponse("Get '" + id + "'",
            new WebResponse(404),
            readGet(fpath + "test." + ide + "." + ext));

        testWebResponse("#" + i + " Get '" + id + "." + id + "'",
            new WebResponse(404),
            readGet(fpath + "test." + ide + "." + ide));

        testWebResponse("Get '." + id + "'",
            new WebResponse(404),
            readGet(fpath + UTF8_DOT + ide));

        testWebResponse("Get ../'" + id + "'",
            new WebResponse(400),
            readGet(fpath + "test." + URLEncoder.encode("../", "UTF-8") + ide));
      }

      String fname = "test.xxx." + ext;
      testWebResponse("Wrong result reading file '" + fname +
                                          "' that doesn't exists",
          new WebResponse("@\\{\"request_id\":,\"error\":\\{" +
              "\"id\":1,\"message\":\"Entry not found\"," +
              "\"info\":\"Entry \\\\\\\\\\\\\".*" +
                  "xxx." + ext + "\\\\\\\\\\\\\" not found\"\\}\\}@"),
          readGet(fpath + fname));
    }
  }

  @Test
  public void testExFilePutGet() throws Exception {

    // Static project name
    String pname = "test";

    testWebResponse("Wrong result reading file_info that doesn't exists", 
        new WebResponse("{" +
            "\"request_id\":,\"error\":{" +
              "\"id\":258," +
              "\"message\":\"Invalid file extension\"," +
              "\"info\":\"File extension 'xxx' is not supported\"" +
            "}}"),
        readGet(getExFilePath() + "file_info/" + "zzz/" + pname + ".yyy.xxx"));

    for (String rdir : FUTILS.getResDirList()) {
      String fpath = getExFilePath() + pname + "/" + rdir;

      // Read list of test files
      testWebResponse("Read list of test files", new WebResponse(
                              FUTILS.getExtFileListJson(rdir)),readGet(fpath));

      String[][] tset = FUTILS.getExtFileTestFiles(rdir);

      for (int i = 0; i < tset.length; i++) {
        String[] ftest = tset[i];

        testWebResponse("Read test file_info for " + ftest[0], new WebResponse(ftest[1]),
            readGet(getExFilePath() + "file_info/" + rdir + "/" + pname + "." + ftest[0]));
      }

    }
  }

  @Test
  public void testExFileCreateSimple() throws Exception {
    final String tname = "test";

    // Project directory
    String pdir = tname + "_" + getDirPrefix();

    for (String dname : FUTILS.getResDirList()) {
      String[] extl = FUTILS.getExtList(dname);
      String fpath = getExFilePath() + dname + "/";

      // Create temp directory
      testWebResponse("Fail creating '" + pdir + "'", HTTP_RESP_OK,
          readPut(getProjPath() + pdir, ""));

      String prefix = pdir + ".";

      // Try creating file with wrong directory 
      testWebResponse(
          new WebResponse("@\\{\"request_id\":\\d*,\"error\":\\{" +
              "\"id\":234,\"message\":\"Unsupported resource type\"," +
              "\"info\":\"Unknown resource directory 'zzz'\"\\}\\}@"),
          uploadFile(getExFilePath() + "zzz/" + prefix + tname + "." + extl[0],
              tname + "." + extl[0],
                  new ByteArrayInputStream("zzz".getBytes())));

      // Try creating file with wrong extension
      testWebResponse(
          new WebResponse("@\\{\"request_id\":\\d*,\"error\":\\{" +
              "\"id\":8,\"message\":\"Invalid File Extension\"," +
              "\"info\":\"Expected .* extensions? but " +
              "found \\\\\\\\\\\\\"zzz\\\\\\\\\\\\\"\"\\}\\}@"),
          uploadFile(getExFilePath() + dname + "/" + prefix + tname + ".zzz",
              tname + "." + extl[0],
                  new ByteArrayInputStream("zzz".getBytes())));

      // Try creating empty file
      testWebResponse(
          new WebResponse("{\"request_id\":,\"error\":{\"id\":299," +
              "\"message\":\"Error updating file\"," +
              "\"info\":\"Failed to store empty uploaded file\"}}"),
          readPost(fpath + prefix + tname + "." + extl[0]));

      // Repeat for each file extension supported by resource 
      for (String ext : FUTILS.getExtList(dname)) {
        byte[] fbody = FUTILS.getDemoExFileAsBytes(dname, ext);

        String fname = tname + "." + ext;
        String dsn = pdir + "." + fname;
        String npath = fpath + dsn;

        // Upload multi-part file
        InputStream in = new ByteArrayInputStream(fbody);
        testWebResponse(new WebResponse(FUTILS.getDemoExFileUploadResp(dname, ext)),
            uploadFile(npath, fname, in));

        // Try upload file with same name
        in.reset();
        testWebResponse(new WebResponse("@\\{\"request_id\":\\d*,\"error\":\\{\"id\":2," +
            "\"message\":\"Entry already exists\"," + "\"info\":\"Entry \\\\\\\\\\\\\".*" +
            pdir + "/" + dname + "/" + tname + "." + ext +
            "\\\\\\\\\\\\\" already exists\"\\}\\}@"),
                uploadFile(npath, fname, in));

        // Upload file with same name and rewrite flag
        in.reset();
        testWebResponse(new WebResponse(FUTILS.getDemoExFileUploadResp(dname, ext)),
            uploadFile(npath + "?overwrite=true", fname, in));

        // Read uploaded file info
        testWebResponse("Invalid file_info for demo file " + fname,
            new WebResponse("@\\{\"size\":" + fbody.length +
                ",\"read\":true,\"write\":true,\"last_modified\":\".*\"\\}@"),
            readGet(getExFilePath() + "file_info/" + dname + "/" + dsn));

        // Read uploaded file body
        testFileDownload(npath, fbody, fname, FUTILS.getMimeType(fname));
        
        // Delete file
        testWebResponse(HTTP_RESP_OK, readDelete(npath));
      }

      testWebResponse("Temp project " + pdir + " is not empty", new WebResponse("[]"),
          readGet(getExFilePath() + pdir + "/" + dname));

      testWebResponse("Fail deleting '" + pdir + "'", HTTP_RESP_OK,
          readDelete(getProjPath() + pdir));

    }
  }

  private String getFirstExt(String rname) {
    String[] extl = FUTILS.getExtList(rname);
    if (extl == null)
      return null;

    return extl[0];
  }
}
