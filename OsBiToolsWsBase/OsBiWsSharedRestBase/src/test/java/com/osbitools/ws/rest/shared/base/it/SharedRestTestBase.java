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
 * Date: 2017-04-19
 * 
 */

package com.osbitools.ws.rest.shared.base.it;

/**
 * Preliminary class to be used in single and multi-threaded tests
 * 
 */

public class SharedRestTestBase extends AbstractSharedRestBaseTest<SharedRestBaseTestUnit> {

  @Override
  protected SharedRestBaseTestUnit initTestUnit() {
    return new SharedRestBaseTestUnit();
  }

}
