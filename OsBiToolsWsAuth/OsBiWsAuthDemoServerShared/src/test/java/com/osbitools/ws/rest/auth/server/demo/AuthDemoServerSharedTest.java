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

package com.osbitools.ws.rest.auth.server.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.rest.auth.server.shared.utils.AbstractAuthServerSharedTest;

/**
 * Test Configuration class with authentication manager
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, value = {
    "spring.config.name=test" })
public class AuthDemoServerSharedTest extends AbstractAuthServerSharedTest {

  
  /**
   * Test login.html is available and accessible
   */
  @Test
  public void testLoginPage() {
    ResponseEntity<String> resp = getRestTemplate().getForEntity("/login.html", String.class);
    
    assertEquals(HttpStatus.OK, resp.getStatusCode());
    assertNotNull(resp.getBody());
    assertTrue(resp.getBody().length() > 0);
  }

  @Override
  protected String[] getCredentials() {
    return new String[] { "user", "user_pwd" };
  }

  @Override
  protected String getTestUser() {
    return "user";
  }

  @Override
  protected String getTestUserRole() {
    return "user_role";
  }
}
