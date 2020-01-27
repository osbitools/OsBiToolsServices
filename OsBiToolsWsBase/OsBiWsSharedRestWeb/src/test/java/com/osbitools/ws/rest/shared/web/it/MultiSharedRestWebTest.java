/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2014-10-11
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.shared.web.it;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.shared.common.TestConstants;
import com.osbitools.ws.rest.shared.base.it.GenericRestMultiTest;

/**
 * Multi Thread Test for Shared Module
 * 
 */

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT,
    value = { "spring.config.name=test" })
public class MultiSharedRestWebTest extends GenericRestMultiTest {

  @Override
  public Class<?> getTestClass() {
    return SharedRestWebBase.class;
  }

  @Override
  public long getWaitTime() {
    return TestConstants.WAIT_TIME * 10;
  }

}
