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

package com.osbitools.ws.rest.pd.shared.test;

import com.osbitools.ws.rest.pd.shared.common.PdAppSharedTestConstants;
import com.osbitools.ws.rest.prj.rest.shared.rest.AbstractPrjMgrSharedTest;

public class PdAppSharedTest extends AbstractPrjMgrSharedTest {

  @Override
  protected String[][] getConfigTest() {
    return PdAppSharedTestConstants.CONFIG_LIST;
  }
}
