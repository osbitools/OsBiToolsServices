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
import java.io.FileWriter;
import java.math.BigInteger;
import java.net.URLEncoder;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.rest.prj.shared.common.TestPrjMgrBaseConstants;
import com.osbitools.ws.rest.shared.base.it.WebResponse;

/**
 * Test DsMap Single Thread processing using web access
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT,
    value = { "spring.config.name=test" })
public class GitTestWeb extends AbstractPrjMgrWebTest {

  @Autowired
  private Git _git;

  private static String GIT_APP_NAME = "git";

  protected String getGitPath() {
    return getSrvUrl() + GIT_APP_NAME + "/";
  }

  @Test
  public void testGitCommit() throws Exception {
    int len = 2;
    
    String fbase = "entity";
    String dsname = "test" + getDirPrefix();

    // Create temp directory
    testWebResponse("Fail creating '" + dsname + "'", HTTP_RESP_OK,
        readPut(getProjPath() + "/" + dsname, ""));

    String prefix = dsname + ".";

    String[] src = new String[len];
    String[][] commits = new String[2][len];
    String[] comments = new String[] { "Created", "Updated" };

    String[][] jset = FUTILS.getJsonDemoSet();

    for (int i = 0; i < len; i++)
      src[i] = jset[i][1];

    for (int i = 0; i < len; i++) {
      String fname = fbase + i;

      // Creating a new entity
      testWebResponse("Fail creating entity " + prefix + fname, HTTP_RESP_OK, readPut(
          getEntityPath() + prefix + fname, src[0], MediaType.APPLICATION_JSON_UTF8_VALUE));

      // Commit entity
      WebResponse resp = readPut(getGitPath() + "commit/" + prefix + fname + "?comment=" +
          URLEncoder.encode(comments[0] + " #" + i, "UTF-8"), "");
      commits[i][0] = resp.getMsg();
      assertEquals(200, resp.getCode());

      // Updating entity. It expected to have log
      testWebResponse("Fail updating entity " + fname, HTTP_RESP_OK, readPost(
          getEntityPath() + prefix + fname, src[1], MediaType.APPLICATION_JSON_UTF8_VALUE));

      // Commit another revision
      resp = readPut(getGitPath() + "commit/" + prefix + fname + "?comment=" +
          URLEncoder.encode(comments[1] + " #" + i, "UTF-8"), "");
      commits[i][1] = resp.getMsg();
      assertEquals(200, resp.getCode());

      // Read all commits for file
      String log = "";
      int cnt = 1;
      for (int j = cnt; j >= 0; j--) {
        String rev_id = commits[i][j];

        // Find commit time by revision id
        Iterable<RevCommit> rcl;
        // Split revision id in 5 parts
        int[] rvl = new int[5];
        for (int k = 0; k < 5; k++)
          rvl[k] = new BigInteger(rev_id.substring(k * 8, (k + 1) * 8), 16).intValue();
        ObjectId id = new ObjectId(rvl[0], rvl[1], rvl[2], rvl[3], rvl[4]);
        assertEquals(rev_id, id.name());
        rcl = _git.log().add(id).setMaxCount(1).call();

        RevCommit rv = null;
        for (RevCommit rc : rcl) {
          // Expecting a single record
          if (rv != null)
            fail("Multiple logs found for revision id : " + rev_id);
          rv = rc;
        }

        if (rv == null)
          fail("No logs found for revision id : " + rev_id);

        // Retrieve original commit time and convert it into ISO-8601 standard
        log += ",{\"id\":\"" + rev_id + "\"," + "\"comment\":\"" + comments[j] + " #" + i +
            "\",\"commit_time\":" + rv.getCommitTime() + "," + "\"committer\":\"anonymous\"}";

        // Read actual file. Diff is not expected
        testWebResponse("#" + i + ":" + j + " entity doesn't match",
            new WebResponse("{" + TestPrjMgrBaseConstants.ENTYTY_PREFIX +
                FUTILS.getJsonPrefix() + jset[j][1] + FUTILS.getJsonSuffix() +
                ",\"has_log\":true,\"has_diff\":false}"),
            readGet(getGitPath() + "revisions/" + rev_id + "/" + prefix + fname));
      }

      WebResponse wlog = readGet(getGitPath() + "revisions/" + prefix + fname);
      testWebResponse(new WebResponse("[" + log.substring(1) + "]"), wlog);
      assertEquals(200, wlog.getCode());
    }

    for (int i = 0; i < len; i++)
      testWebResponse(HTTP_RESP_OK, readDelete(getEntityPath() + prefix + fbase + i));

    testWebResponse("Temp project " + dsname + " is not empty", new WebResponse("[]"),
        readGet(getProjPath() + dsname));

    testWebResponse("Fail deleting '" + dsname + "'", HTTP_RESP_OK,
        readDelete(getProjPath() + dsname));

  }

  @Test
  public void testGitRemote() throws Exception {
    final String tname = "qw.txt";

    // Create & Commit test file
    String fname = FUTILS.getProjWorkDir() + tname;
    File f = new File(fname);
    FileWriter out = new FileWriter(f);
    out.write("QWERTY");
    out.close();
    _git.add().addFilepattern(tname).call();
    _git.commit().setMessage("qw.txt").call();

    // Push to remote
    testWebResponse("Push git remote request failed.", HTTP_RESP_OK,
        readPost(getGitPath() + "push", ""));

    // Fetch from remote. Temporarily glitching
    testWebResponse("Trying fail push git remote",
        new WebResponse("{\"request_id\":,\"error\":{\"id\":250," +
            "\"message\":\"Error pull from remote git\"," +
            "\"info\":\"Error pull from remote [" + TestPrjMgrBaseConstants.REMOTE_REPO_NAME +
            "]\"," + "\"details\":[\"Nothing to fetch.\"]}}"),
        readPost(getGitPath() + "pull", ""));
  }

}
