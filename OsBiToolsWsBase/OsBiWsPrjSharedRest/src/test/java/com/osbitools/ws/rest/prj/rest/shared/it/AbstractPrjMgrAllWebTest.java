/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2015-04-18
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.rest.shared.it;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Run test suit. Total are 5 API's
 * 
 */

@RunWith(Suite.class)
@SuiteClasses({ EntityTestWeb.class, ExFileTestWeb.class, GitTestWeb.class,
    LangSetTestWeb.class, ProjTestWeb.class })
public class AbstractPrjMgrAllWebTest {

}
