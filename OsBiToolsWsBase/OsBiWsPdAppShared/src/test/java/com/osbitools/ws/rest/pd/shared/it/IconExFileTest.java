/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-08-11
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.pd.shared.it;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.rest.prj.rest.shared.it.AbstractPrjMgrWebTest;
import com.osbitools.ws.rest.shared.base.it.WebResponse;

/**
 * Test Demo Icons source read
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT,
    value = { "spring.config.name=test" })
public class IconExFileTest extends AbstractPrjMgrWebTest {

  private final String _bpath = getExFilePath() + "src/";

  @Test
  public void testImgFilesRead() throws Exception {
    // Icons directory hardcoded for now
    String dname = "icons";
    String path = _bpath + "/" + dname + "/test.gear.";

    for (String ext : FUTILS.getExtList(dname)) {
      String src = FUTILS.getDemoExFileUploadResp(dname, ext);

      // Read gear file
      testWebResponse(new WebResponse(src), readGet(path + ext));
    }
  }
}
