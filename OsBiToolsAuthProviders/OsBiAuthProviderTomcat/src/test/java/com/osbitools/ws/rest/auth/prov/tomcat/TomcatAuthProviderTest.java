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

package com.osbitools.ws.rest.auth.prov.tomcat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.catalina.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tomcat Authentication Provider Test
 * 
 */

@SpringBootTest(classes = { TomcatUserSecurityProvider.class,
    TomcatAuthProviderConfig.class }, value = { "spring.config.name=test" })
@RunWith(SpringRunner.class)
public class TomcatAuthProviderTest {

  @Autowired
  private TomcatUserSecurityProvider _prov;

  @Test
  public void testCorrect() {

    // Find user
    for (String name : new String[] { "user", "admin" }) {
      User user = _prov.getUsers().findUser(name);
      assertNotNull(user);
      assertEquals("Password for user [" + name + "] doesn't match",
          name + "_pwd", user.getPassword());
      assertEquals("Group for user [" + name + "] doesn't match",
          name + "_group", user.getRoles().next().getRolename());

      // Test provider
      _prov.authenticate(
          new UsernamePasswordAuthenticationToken(name, name + "_pwd", null));
    }
  }

  @Test
  public void testIncorrect() {

    try {
      // Test correct user but null password
      _prov.authenticate(
          new UsernamePasswordAuthenticationToken("user", null, null));
      fail("AuthenticationException expected");
    } catch (AuthenticationException e) {
      // Ignore
    }

    try {
      // Test correct user but empty password
      _prov.authenticate(
          new UsernamePasswordAuthenticationToken("user", "", null));
      fail("AuthenticationException expected");
    } catch (AuthenticationException e) {
      // Ignore
    }

    try {
      // Test correct user but invalid password
      _prov.authenticate(
          new UsernamePasswordAuthenticationToken("user", "zzz", null));
      fail("AuthenticationException expected");
    } catch (AuthenticationException e) {
      // Ignore
    }

    try {
      // Test invalid user
      _prov.authenticate(
          new UsernamePasswordAuthenticationToken("zzz", "xxx", null));
      fail("AuthenticationException expected");
    } catch (AuthenticationException e) {
      // Ignore
    }
  }
}
