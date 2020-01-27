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
 * Date: 2018-01-01
 * 
 */

package com.osbitools.ws.rest.shared.web.it;

/**
 * Preliminary class to be used in single and multi-threaded tests
 * 
 */

public class SharedRestWebBase extends AbstractSharedRestWebTest<SharedRestWebTestUnit> {

  @Override
  protected SharedRestWebTestUnit initTestUnit() {
    return new SharedRestWebTestUnit();
  }

}
