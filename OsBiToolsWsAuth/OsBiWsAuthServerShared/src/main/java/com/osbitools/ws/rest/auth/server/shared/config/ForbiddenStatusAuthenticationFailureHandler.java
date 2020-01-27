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

package com.osbitools.ws.rest.auth.server.shared.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * Default class for Authentication Failure Handler
 * 
 */
public class ForbiddenStatusAuthenticationFailureHandler
    implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest req,
      HttpServletResponse resp, AuthenticationException exception)
      throws IOException, ServletException {
    // set our response to Forbidden status
    resp.setStatus(HttpStatus.FORBIDDEN.value());
  }
}
