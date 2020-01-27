/*
 * Open Source Business Intelligence Tools - http://www.osgitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-01-01
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.auth.shared.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osbitools.ws.shared.common.UserService;
import com.osbitools.ws.rest.auth.shared.common.AuthSharedTestConstants;
import com.osbitools.ws.rest.auth.shared.service.TestLoggerService;

/**
 * Test Logger interface implementation
 * 
 */
@Service
public class TestLoggerServiceImpl implements TestLoggerService {

  @Autowired
  private UserService _usrv;

  @Override
  public void logRequest() {
    AuthSharedTestConstants.LOG.info(_usrv.getLoginUser());
  }

}
