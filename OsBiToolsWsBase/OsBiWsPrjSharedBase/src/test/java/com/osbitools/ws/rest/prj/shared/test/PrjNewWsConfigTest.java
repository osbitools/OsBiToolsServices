/*
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2016-23-06
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.prj.shared.test;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.rest.prj.shared.PrjSharedBaseTestAppConfig;
import com.osbitools.ws.rest.prj.shared.common.TestPrjMgrBaseConstants;
import com.osbitools.ws.rest.prj.shared.config.AbstractPrjWsConfig;

/**
 * Basic Web Tests
 * 
 */

@DirtiesContext
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@ContextConfiguration(classes = { PrjSharedBaseTestAppConfig.class })
public class PrjNewWsConfigTest
    extends AbstractAppPrjWsConfigTestNew<AbstractPrjWsConfig> {

  @Override
  protected String getWorkDir() {
    return TestPrjMgrBaseConstants.WORK_PRJ_DIR;
  }
}