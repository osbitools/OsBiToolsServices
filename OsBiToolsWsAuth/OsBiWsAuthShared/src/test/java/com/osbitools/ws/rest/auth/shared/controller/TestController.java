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

package com.osbitools.ws.rest.auth.shared.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.osbitools.ws.rest.auth.common.Constants;
import com.osbitools.ws.rest.auth.shared.common.AuthSharedTestConstants;
import com.osbitools.ws.rest.auth.shared.service.TestLoggerService;

/**
 * Test Controller for Common Security Layer test
 * 
 */
@RestController
public class TestController {

  @Autowired
  private TestLoggerService _lsrv;
  
  @RequestMapping(AuthSharedTestConstants.TEST_URL)
  public String userInfo(Authentication authentication,
      HttpServletRequest req) {
    // Insert user name was used from login from standard http session
    String message = Constants.USER_NAME_KEY + ":" +
        req.getSession(false).getAttribute(Constants.USER_NAME_KEY).toString() +
        "|";

    if (authentication != null) {
      // Insert user name was used from login from authentication object
      message += "authenticationName:" + authentication.getName() + "|roles:";
      String roles = "";
      for (GrantedAuthority authority : authentication.getAuthorities())
        roles += "," + authority.getAuthority();

      message += roles.substring(1);
    }

    return message + "|sessionId:" + req.getSession(false).getId();
  }
  
  @RequestMapping("/log")
  @ResponseStatus(HttpStatus.OK)
  public void logRequest() {
    _lsrv.logRequest();
  }
}
