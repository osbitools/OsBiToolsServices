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

package com.osbitools.ws.rest.auth.server.shared.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.rest.auth.server.shared.common.AuthServerTestConstants;
import com.osbitools.ws.rest.auth.server.shared.utils.AbstractAuthServerSharedTest;

/**
 * Integration tests
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, value = {
    "spring.config.name=test" })
public class AuthServerSharedTest extends AbstractAuthServerSharedTest {

  @Override
  protected String[] getCredentials() {
    return new String[] { AuthServerTestConstants.TEST_USER_NAME, AuthServerTestConstants.TEST_USER_PWD };
  }

  @Override
  protected String getTestUser() {
    return AuthServerTestConstants.TEST_USER_NAME;
  }

  @Override
  protected String getTestUserRole() {
    return "ROLE_" + AuthServerTestConstants.TEST_USER_ROLE;
  }

  
}
