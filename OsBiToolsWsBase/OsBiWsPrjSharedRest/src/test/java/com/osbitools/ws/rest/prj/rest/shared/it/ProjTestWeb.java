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

import java.net.URLEncoder;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.rest.shared.base.it.WebResponse;
import com.osbitools.ws.shared.common.GenericTest;

/**
 * Test Project Single Thread processing using web access
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT,
    value = { "spring.config.name=test" })
public class ProjTestWeb extends AbstractPrjMgrWebTest {

  @Test
  public void testProjectBadNames() throws Exception {
    for (int i = 0; i < GenericTest.BAD_ID_LIST.length; i++) {
      String id = GenericTest.BAD_ID_LIST[i];
      String ide = URLEncoder.encode(id, "UTF-8");

      testWebResponse("Get '" + id + "'", new WebResponse(404),
          readGet(getProjPath() + ide));

      testWebResponse("Get '" + id + "." + id + "'", new WebResponse(404),
          readGet(getProjPath() + ide + URLEncoder.encode(".", "UTF-8") + ide));

      testWebResponse("Get '." + id + "'", new WebResponse(404),
          readGet(getProjPath() + URLEncoder.encode(".", "UTF-8") + ide));

      testWebResponse("Get ../'" + id + "'", new WebResponse(400),
          readGet(getProjPath() + URLEncoder.encode("../", "UTF-8") + ide));
    }
  }

  @Test
  public void testProjectSelect() throws Exception {
    for (String[] tp : FUTILS.getProjList()) {
      WebResponse data = readGet(getProjPath() + tp[0]);

      WebResponse resp = new WebResponse(data.getMsg());

      // Check new project content
      String expected = "";
      for (String s : tp[1].split(","))
        expected += "," + "\"" + s + "\"";

      testWebResponse("Fail getting test project",
          new WebResponse("[" + expected.substring(1) + "]"), resp);
    }
  }

  @Test
  public void testProjectCreateSimple() throws Exception {
    String ds = "test" + getDirPrefix();
    String dst = ds + "_tmp";

    testWebResponse("Fail creating '" + ds + "'", HTTP_RESP_OK,
        readPut(getProjPath() + ds, ""));

    // Check new project content
    testWebResponse("Fail getting '" + ds + "' project", new WebResponse("[]"),
        readGet(getProjPath() + ds));

    assertEquals("Project with prefix '" + getDirPrefix() + "' not found",
        "\"" + ds + "\"", getProjDirList());

    // Try create project with same name
    testWebResponse("Create '" + ds + "'",
        new WebResponse(200,
            "@\\{\"request_id\":,\"error\":\\{\"id\":2," +
                "\"message\":\"Entry already exists\"," +
                "\"info\":\"Entry \\\\\\\\\\\\\".*" + ds +
                "\\\\\\\\\\\\\" already exists\"\\}\\}@"),
        readPut(getProjPath() + ds, ""));

    // Try rename project with empty parameter
    testWebResponse(new WebResponse(405), readPost(getProjPath() + ds + "/", ""));

    // Try rename project to same name
    testWebResponse(
        new WebResponse(200,
            "{\"request_id\":,\"error\":{\"id\":203," +
                "\"message\":\"Error renaming project\"," +
                "\"info\":\"Cannot rename " + ds + " to itself\"}}"),
        readPost(getProjPath() + ds + "/" + ds, ""));

    // Renaming project to new name
    testWebResponse("Fail renaming " + ds + "->" + dst, HTTP_RESP_OK,
        readPost(getProjPath() + ds + "/" + dst, ""));

    // Deleting project with wrong name
    testWebResponse(
        new WebResponse(200,
            "@\\{\"request_id\":,\"error\":\\{\"id\":1," +
                "\"message\":\"Entry not found\"," +
                "\"info\":\"Entry \\\\\\\\\\\\\".*" + ds +
                "\\\\\\\\\\\\\" not found\"\\}\\}@"),
        readDelete(getProjPath() + ds));

    // Deleting project
    testWebResponse("Fail deleting " + dst, HTTP_RESP_OK,
        readDelete(getProjPath() + dst));
  }

  @Test
  public void testProjectCreateComplex() throws Exception {
    int dnum = 3; // Directory cycles
    String dst = "";
    String dname = "test" + getDirPrefix();

    for (int i = 0; i < dnum; i++) {
      String dsi = dname + i;

      testWebResponse("Create '" + dname + "'", HTTP_RESP_OK,
          readPut(getProjPath() + dname, ""));

      // Check new project content
      testWebResponse("Get '" + dname + "' project", new WebResponse("[]"),
          readGet(getProjPath() + dname));

      // Try create project with same name
      testWebResponse("Create '" + dname + "'",
          new WebResponse(200,
              "@\\{\"request_id\":,\"error\":\\{\"id\":2," +
                  "\"message\":\"Entry already exists\"," +
                  "\"info\":\"Entry \\\\\\\\\\\\\".*" + dname +
                  "\\\\\\\\\\\\\" already exists\"\\}\\}@"),
          readPut(getProjPath() + "/" + dname, ""));

      if (i > 0)
        // Try rename project to existing name
        testWebResponse("Renaming " + dname + "->" + dname + (i - 1),
            new WebResponse(200,
                "@\\{\"request_id\":,\"error\":\\{\"id\":2," +
                    "\"message\":\"Entry already exists\"," +
                    "\"info\":\"Entry \\\\\\\\\\\\\".*" + dname + (i - 1) +
                    "\\\\\\\\\\\\\" already exists\"\\}\\}@"),
            readPost(getProjPath() + dname + "/" + dname + (i - 1), ""));

      // Try rename project
      testWebResponse("Renaming " + dname + "->" + dsi, HTTP_RESP_OK,
          readPost(getProjPath() + dname + "/" + dsi, ""));

      dst += "," + "\"" + dsi + "\"";

      assertEquals(
          "Fail checking all projects with prefix '" + getDirPrefix() + "'",
          dst.substring(1), getProjDirList());

      // Wait 1 sec to detect different create time for directory
      Thread.sleep(1000);
    }

    // Delete all working projects
    for (int i = 0; i < dnum; i++)
      testWebResponse("Fail deleting " + dname + i, HTTP_RESP_OK,
          readDelete(getProjPath() + "/" + dname + i));
  }

}
