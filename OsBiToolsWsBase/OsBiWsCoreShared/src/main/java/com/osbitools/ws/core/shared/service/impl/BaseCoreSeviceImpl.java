/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2016-06-09
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.core.shared.daemons.DsFilesCheck;
import com.osbitools.ws.core.shared.daemons.LsFilesCheck;
import com.osbitools.ws.core.shared.service.BaseCoreService;
import com.osbitools.ws.shared.service.AbstractBaseService;

public class BaseCoreSeviceImpl extends AbstractBaseService<CoreWsConfig>
    implements BaseCoreService {

  @Autowired
  private DsFilesCheck _dcheck;

  @Autowired
  private LsFilesCheck _lcheck;

  @Autowired
  private CoreWsConfig _cfg;

  DsFilesCheck getDsFilesCheck() {
    return _dcheck;
  }

  public LsFilesCheck getLsFilesCheck() {
    return _lcheck;
  }

  @Override
  public String getDefaultLang() {
    return _cfg.getLang();
  }

  @Override
  public boolean getTrace() {
    return _cfg.getTrace();
  }

  @Override
  public CoreWsConfig getWsConfig() {
    return _cfg;
  }
}
