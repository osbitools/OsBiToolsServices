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

package com.osbitools.ws.rest.auth.shared.service;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.osbitools.ws.rest.auth.common.Constants;
import com.osbitools.ws.shared.common.UserService;

/**
 * Implementation of UserService for authenticated user access
 * 
 */
@Service("user_service")
@SessionScope
public class AuthUserService implements UserService, Serializable {

  // Default Serial Version UID
  private static final long serialVersionUID = 1L;
  
  private String _luser;
  
  @Autowired
  public void setLoginUser(HttpSession session) {
    _luser = session.getAttribute(Constants.USER_NAME_KEY).toString();
  }
  
  @Override
  public String getLoginUser() {
    return _luser;
  }

}
