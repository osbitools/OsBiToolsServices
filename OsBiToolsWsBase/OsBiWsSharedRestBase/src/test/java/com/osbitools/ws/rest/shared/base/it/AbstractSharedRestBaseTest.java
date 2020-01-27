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

package com.osbitools.ws.rest.shared.base.it;

import org.junit.Before;
import org.junit.Test;

/**
 * Basic Web Tests
 * 
 * @param <T> type of Basic Web Unit
 * 
 */

public abstract class AbstractSharedRestBaseTest<T extends AbstractSharedRestBaseTestUnit> {

  private T testUnit;
  
  protected abstract T initTestUnit();
  
  @Before
  public void setUpTestUnit() {
    testUnit = initTestUnit();
  }
  
  @Test
  public void testPublicUrls() throws Exception {
    testUnit.doTestPublicUrls();
  }

  /**
   * @return the testUnit
   */
  protected T getTestUnit() {
    return testUnit;
  }

}
