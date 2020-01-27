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

package com.osbitools.ws.shared.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.shared.config.BaseAppWsConfig;

/**
 * Basic Web Tests
 * 
 */

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE,
    value = { "spring.config.name=test" })
public class BaseWsConfigAppTest extends AbstractWsConfigAppTest<BaseAppWsConfig>{
  
}