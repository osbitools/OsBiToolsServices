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

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.rest.prj.shared.it.utils.LangSetFileTest;
import com.osbitools.ws.rest.prj.shared.utils.GitUtils;
import com.osbitools.ws.rest.shared.base.it.WebResponse;
import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.shared.LsConstants;
import com.osbitools.ws.base.WsSrvException;

/**
 * Test ll_set web service using web access
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT,
    value = { "spring.config.name=test" })
public class LangSetTestWeb extends AbstractPrjMgrWebTest {

  private static final String TEST_PRJ = "test";

  private static final String LL_SET_APP_NAME = "ll_set";

  // Git repository for test project
  private static Git _git;

  protected String getLangLabelSetPath() {
    return getSrvUrl() + LL_SET_APP_NAME + "/" + TEST_PRJ;
  }

  @Before
  public void clearLangSetFile() {
    File f = new File(FUTILS.getProjWorkDir() + "test" + File.separator +
        LsConstants.LANG_SET_FILE);

    if (f.exists())
      assertTrue("Unable delete ll_set from test project", f.delete());
  }

  @Test
  public void testBadReadWrite() throws Exception {
    // Read lang_set from invalid project
    final String prefix = "xxx";

    testWebResponse(
        new WebResponse("@\\{\"request_id\":,\"error\":\\{\"id\":261," +
            "\"message\":\"Error reading language labels\"," +
            "\"info\":\"Project \\[" + TEST_PRJ + prefix + "\\] not found.\"," +
            "\"details\":\\[\"Directory .*" + TEST_PRJ + prefix +
            " doesn't exists\"\\]\\}\\}@"),
        readGet(getLangLabelSetPath() + prefix));

    // Try upload empty file without comments
    testWebResponse(new WebResponse(400), readPost(getLangLabelSetPath(), null,
        MediaType.APPLICATION_JSON_UTF8_VALUE));

    // Try upload empty file with comments
    testWebResponse(
        new WebResponse(400),
        readPost(getLangLabelSetPath() + "?comment=qqq", "",
            MediaType.APPLICATION_JSON_UTF8_VALUE));

    // Try upload invalid file
    testWebResponse(
        new WebResponse(400),
        readPost(getLangLabelSetPath() + "?comment=test%20me", "bad",
            MediaType.APPLICATION_JSON_UTF8_VALUE));
  }

  @Test
  public void testGoodReadWrite() throws Exception {
    // Open git
    _git =
        Git.open(new File(FUTILS.getProjWorkDir() + File.separator + ".git"));

    String fname1 = "test/" + LsConstants.LANG_SET_FILE;

    testWebResponse(HTTP_RESP_OK, readGet(getLangLabelSetPath()));

    // Empty lang set and commit it to see the changes
    BaseUtils.saveFile(new File(FUTILS.getProjWorkDir() + File.separator +
        "test" + File.separator + LsConstants.LANG_SET_FILE), "");

    GitUtils.commitFile(_git, fname1, "Reseting file", "test");

    // Try read empty file
    testWebResponse(
        new WebResponse("{\"request_id\":,\"error\":{" + "\"id\":264," +
            "\"message\":\"Error reading language labels\"," +
            "\"details\":[\"Premature end of file.\"]}}"),
        readGet(getLangLabelSetPath()));

    testLangSetFile(fname1, "First Demo Load at " + (new Date()).getTime(),
        LangSetFileTest.LL_SET_JSON);

    // Upload ll_set_test and check
    testLangSetFile(fname1, "Second Demo Load at " + (new Date()).getTime(),
        LangSetFileTest.LL_SET_TEST_JSON);
  }

  private void testLangSetFile(String fname, String comment, String expected)
      throws WsSrvException, IOException {
    // Upload file
    testWebResponse("File upload error", HTTP_RESP_OK, readPost(
        getLangLabelSetPath() + "?comment=" + comment.replaceAll(" ", "%20"),
        expected, MediaType.APPLICATION_JSON_UTF8_VALUE));

    // Test it's the same after upload
    testWebResponse("File doesn't match after upload",
        new WebResponse(expected), readGet(getLangLabelSetPath()));

    // Read commited log for file and compare last entry with uploaded file
    ArrayList<RevCommit> commits = GitUtils.getLogByFileName(_git, fname);

    assertTrue("No commits", commits.size() > 0);

    RevCommit commit = commits.get(0);
    assertEquals("Commit message doesn't match", comment,
        commit.getFullMessage());
  }
}
