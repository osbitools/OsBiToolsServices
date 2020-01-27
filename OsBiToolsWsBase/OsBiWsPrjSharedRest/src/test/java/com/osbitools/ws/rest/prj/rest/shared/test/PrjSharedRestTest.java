/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 * Date: 2018-02-17
 * 
 */

package com.osbitools.ws.rest.prj.rest.shared.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test context loader
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest (value = { "spring.config.name=test" })
public class PrjSharedRestTest {

  @Test
  public void testContextLoad() {
    // Do nothing, just load context
  }
}
