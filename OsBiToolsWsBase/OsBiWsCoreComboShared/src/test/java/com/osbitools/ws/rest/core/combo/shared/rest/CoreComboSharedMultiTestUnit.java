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

package com.osbitools.ws.rest.core.combo.shared.rest;

import org.slf4j.Logger;
import org.springframework.boot.test.web.client.TestRestTemplate;

/**
 * Class used for Multi threading test
 * 
 */
public class CoreComboSharedMultiTestUnit extends CoreComboSharedTestUnit {

  private static TestRestTemplate REST_TEMPLATE;
  
  private static Logger LOGGER;
  
  private static int LOCAL_PORT;
  
  @Override
  protected TestRestTemplate getRestTemplate() {
    return REST_TEMPLATE;
  }

  @Override
  protected int getPort() {
    return LOCAL_PORT;
  }

  @Override
  protected Logger getLogger() {
    return LOGGER;
  }
}
