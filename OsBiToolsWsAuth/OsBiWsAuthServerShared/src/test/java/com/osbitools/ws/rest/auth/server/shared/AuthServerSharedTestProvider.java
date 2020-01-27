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

package com.osbitools.ws.rest.auth.server.shared;

import java.util.Arrays;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.osbitools.ws.rest.auth.server.shared.common.AuthServerTestConstants;

/**
 * Authentication Provider based on Tomcat xml user configuration files
 * 
 */

public class AuthServerSharedTestProvider implements AuthenticationProvider {
  
  @Override
  public Authentication authenticate(Authentication auth)
      throws AuthenticationException {
    if (! (auth.getName().equals(AuthServerTestConstants.TEST_USER_NAME) &&
        auth.getCredentials().toString().equals(AuthServerTestConstants.TEST_USER_PWD)))
      throw new BadCredentialsException("Invalid user and/or password");

    return new UsernamePasswordAuthenticationToken(AuthServerTestConstants.TEST_USER_NAME, 
        AuthServerTestConstants.TEST_USER_PWD, Arrays.asList(new SimpleGrantedAuthority[] {
            new SimpleGrantedAuthority("ROLE_" + AuthServerTestConstants.TEST_USER_ROLE)
        }));
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
  
}
