/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-11-07
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.daemons;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.core.shared.TestCoreSharedAppConfig;
import com.osbitools.ws.core.shared.common.CoreSharedTestConstants;
import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.core.shared.config.CoreWsConfigProperties;
import com.osbitools.ws.core.shared.daemons.AbstractResourceCheck;
import com.osbitools.ws.core.shared.daemons.LsResource;
import com.osbitools.ws.shared.binding.ll_set.LangLabelsSet;

@DirtiesContext
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@ContextConfiguration(
    classes = { CoreWsConfigProperties.class, TestCoreSharedAppConfig.class, LsFilesCheck.class })
public class LangLabelsDaemonTest
    extends AbstractResourceDaemonTest<LangLabelsSet, LsResource> {

  @Autowired
  @Qualifier("core_ws_cfg")
  private CoreWsConfig _cfg;

  // Pointer on LangLabels File check daemon
  @Autowired
  @Qualifier("lcheck")
  public void setLsFilesCheckRef(AbstractResourceCheck<LangLabelsSet, LsResource> check) {
    initCheckRef(check);
  }

  //Destination file
  private static final String _fname = CoreSharedTestConstants.LS_SET_TEST_FILE_PATH;

  public static LsFileTestResConfig LS_CFG = new LsFileTestResConfig();

  private static final String fkey = CoreSharedTestConstants.TEST_LS_FILE_KEY;

  public LangLabelsDaemonTest() {
    super(fkey, LangLabelsSet.class,
        new String[] { LsConstants.LANG_SET_FILE + "_test", LsConstants.LANG_SET_FILE },
        _fname, LS_CFG);
  }

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    init();
  }

  @Test
  public void testSingleReload() throws Exception {
    testSingleResourceReload();
  }

  @Test
  public void multiTestReload() throws Exception {
    multiTestResourceReload(LangLabelsDaemonTest.class.getName());
  }
}
