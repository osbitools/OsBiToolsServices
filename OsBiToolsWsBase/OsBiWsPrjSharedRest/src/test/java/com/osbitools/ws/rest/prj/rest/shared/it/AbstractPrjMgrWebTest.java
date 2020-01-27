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

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.springframework.http.HttpHeaders;

import com.osbitools.ws.rest.prj.shared.it.utils.GenericPrjMgrTest;
import com.osbitools.ws.rest.prj.shared.it.utils.IDemoFileUtils;
import com.osbitools.ws.rest.shared.base.common.FileDownloadResponse;
import com.osbitools.ws.rest.shared.base.it.WebResponse;
import com.osbitools.ws.rest.shared.web.it.SharedRestWebTestUnit;

/**
 * Shared functions for Multi-thread test
 * 
 */
public abstract class AbstractPrjMgrWebTest extends SharedRestWebTestUnit {

  // Random generator
  SecureRandom random = new SecureRandom();

  // WebApp path
  protected static final String PROJ_APP_NAME = "projects";

  // TODO Delete obsoleted
  // protected final String getProjPath() = TestRestBaseConstants.SRV_BASE_URL +
     // _proj_app_name + "/";

  protected static final String ENTITY_APP_NAME = "entities";

  //TODO Delete obsoleted
  // protected final String _entity_path = TestRestBaseConstants.SRV_BASE_URL +
  //    _entity_app_name + "/";

  protected static final String _EX_FILES_APP_NAME = "ex_files";

  //TODO Delete obsoleted
  // protected final String _ex_files_path = TestRestBaseConstants.SRV_BASE_URL +
  //    _ex_files_app_name + "/";
  
  private String _dprefix;
  private Pattern _p;

  // File Utils Test handler
  public IDemoFileUtils FUTILS;

  // Entity Utils handler

  public AbstractPrjMgrWebTest() {
    try {
      FUTILS = GenericPrjMgrTest.getDemoFileUtilsHandler();
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  protected String getProjPath() {
    return getSrvUrl() + PROJ_APP_NAME + "/";
  }
  
  protected String getEntityPath() {
    return getSrvUrl() + ENTITY_APP_NAME + "/";
  }
  
  protected String getExFilePath() {
    return getSrvUrl() + _EX_FILES_APP_NAME + "/";
  }
  
  @Before
  public void testEmptyDirStart() throws Exception {
    // Generate temp dir prefix
    _dprefix = new BigInteger(24, random).toString(16);
    _p = Pattern.compile(".*" + _dprefix + ".*");

    assertEquals("START: Found project with prefix '" + _dprefix + "'", "",
        getProjDirList());
  }

  @After
  public void testEmptyDirEnd() throws Exception {
    assertEquals("END: Found project with prefix '" + _dprefix + "'", "",
        getProjDirList());
  }

  String getProjDirList() throws Exception {
    WebResponse resp = readGet(getProjPath());
    assertEquals("Project Get Result Code", 200, resp.getCode());

    String dstr = resp.getMsg();
    assertNotNull("Project Get Result Non-Null Message", dstr);

    String res = "";
    String[] dlist = dstr.substring(0,dstr.length() - 1).substring(1).split(",");
    for (String ds : dlist)
      if (_p.matcher(ds).matches())
        res += "," + ds;

    return res.equals("") ? "" : res.substring(1);
  }

  public String getDirPrefix() {
    return _dprefix;
  }
  
  protected void testFileDownload(String url, byte[] data, String fname, String contentType) {
    Set<String> headers = new HashSet<String>(Arrays.asList(new String[] {
        HttpHeaders.CONTENT_TYPE, HttpHeaders.CONTENT_DISPOSITION
    }));
    FileDownloadResponse resp = downloadFile(url, headers);
    assertEquals(200, resp.getCode());
    assertArrayEquals("File " + fname + " after download doesn't match ", data,
       resp.getData());
    
    // Test headers set
    assertEquals(contentType + ";charset=utf-8", 
          resp.getHeaders().get(HttpHeaders.CONTENT_TYPE));
    assertEquals("attachment;filename=" + fname,
        resp.getHeaders().get(HttpHeaders.CONTENT_DISPOSITION));
  }
}
