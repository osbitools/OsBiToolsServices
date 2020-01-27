/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2016-23-06
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.shared.web.it;

import org.junit.Test;

import com.osbitools.ws.rest.shared.base.it.AbstractSharedRestBaseTest;

/**
 * Basic Web Tests
 * 
 * @param <T> type of Basic Web Unit
 * 
 */

public abstract class AbstractSharedRestWebTest<T extends AbstractSharedRestWebTestUnit> 
                                                    extends AbstractSharedRestBaseTest<T> {

  @Test
  public void testConfigBadEx() throws Exception {
    getTestUnit().doTestConfigBadEx();
  }
  
  @Test
  public void testConfigGood() throws Exception {
    getTestUnit().doTestConfigGood();
  }

}
