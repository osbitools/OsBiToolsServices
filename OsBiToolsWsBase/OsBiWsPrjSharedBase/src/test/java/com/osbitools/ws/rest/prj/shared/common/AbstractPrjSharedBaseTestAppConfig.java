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

package com.osbitools.ws.rest.prj.shared.common;

import com.osbitools.ws.rest.prj.shared.service.EntityService;
import com.osbitools.ws.rest.prj.shared.service.ExFileService;
import com.osbitools.ws.rest.prj.shared.service.impl.TestTextEntityServiceImpl;
import com.osbitools.ws.rest.prj.shared.service.impl.TestTextExFileServiceImpl;

/**
 * Configuration class for Spring Boot Test Launcher
 * 
 */

public abstract class AbstractPrjSharedBaseTestAppConfig
    extends AbstractPrjSharedWsTestAppConfig {

  @Override
  public EntityService<?> getEntityService() {
    return new TestTextEntityServiceImpl();
  }
  
  @Override
  public ExFileService<?> getExFileService() {
    return new TestTextExFileServiceImpl();
  }
}
