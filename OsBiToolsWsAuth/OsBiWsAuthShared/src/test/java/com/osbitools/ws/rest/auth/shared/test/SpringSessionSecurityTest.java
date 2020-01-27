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

package com.osbitools.ws.rest.auth.shared.test;

import static org.junit.Assert.*;

import java.net.HttpCookie;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.rest.auth.common.Constants;
import com.osbitools.ws.rest.auth.shared.common.AuthSharedTestConstants;
import com.osbitools.ws.rest.auth.shared.session.RedisSessionWrapper;

/**
 * Test cases for Common Security Layer
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(value = {
    "spring.config.name=test" }, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringSessionSecurityTest {

  // Default session timeout - 2 sec
  private static final int TEST_SESSION_TIMEOUT = 2;

  @Autowired
  private RedisOperationsSessionRepository repository;

  @Autowired
  private TestRestTemplate template;

  // Session id
  String sessionId;

  @Test
  public void testAccessForbidden() {
    ResponseEntity<String> response = checkAccessFobidden(
        makeRequestHttpEntity());
    String cookie = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    List<HttpCookie> cookies = HttpCookie.parse(cookie);
    // Check that no cookie set Session cookies
    // TODO For SpringBoot 2.x this functionality isn't working and by default
    // anonymous session created
    // 
    // assertNull(cookie);

    // Try same again but with anonymouse session cookie
    checkAccessFobidden(makeRequestHttpEntity(cookies.get(0).getValue()));
  }

  @Test
  public void testInvalidToken() {
    String uuid = UUID.randomUUID().toString();

    // Connect with some random session cookie
    ResponseEntity<String> response = checkAccessFobidden(
        makeRequestHttpEntity(uuid));

    // Check for same session cookie returns
    String cookie = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    assertNotNull(cookie);

    String str = Constants.COOKIE_SESSION_NAME + "=";
    assertEquals(0, cookie.indexOf(str));
    assertEquals(-1, cookie.indexOf(str + uuid));
  }

  @Test
  public void testAuthOk() throws InterruptedException {
    checkAuthOk();
  }

  @Test
  public void testSessionExpired() throws InterruptedException {
    // Create session and login
    HttpHeaders headers = checkAuthOk();

    // Wait 100 msec and repeat. Session still valid
    Thread.sleep(100);

    checkTestConnection(headers, HttpStatus.OK, getResponseStr());

    // Wait 1 sec and repeat. Session should expire
    Thread.sleep(TEST_SESSION_TIMEOUT * 1000);

    // Connect using same session
    checkTestConnection(headers, HttpStatus.FORBIDDEN, "^" +
        "\\{\"timestamp\":\\d*,\"status\":403,\"error\":\"Forbidden\",\"message\":\"Access Denied\",\"path\":\"\\" +
        AuthSharedTestConstants.TEST_URL + "\"\\}\"$");
  }

  @Test
  public void testLogger() {
    HttpHeaders headers = checkAuthOk();

    // Check no login user detected
    assertEquals(0, AuthSharedTestConstants.LOGS.size());

    // Log user
    checkConnection(headers, HttpStatus.OK, null, "/log");

    // Check login user detected
    assertEquals(AuthSharedTestConstants.TEST_USER_NAME.length(),
        AuthSharedTestConstants.LOGS.size());
    assertEquals(AuthSharedTestConstants.TEST_USER_NAME, new String(
        AuthSharedTestConstants.LOGS.toByteArray(), Charset.forName("UTF-8")));
  }

  private HttpEntity<String> makeRequestHttpEntity() {
    return makeRequestHttpEntity(null);
  }

  /**
   * Create HTTP Headers and set cookie with Session Id
   * 
   * @param id
   * @return
   */
  private HttpEntity<String> makeRequestHttpEntity(String id) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));

    if (id != null)
      headers.set(HttpHeaders.COOKIE, Constants.COOKIE_SESSION_NAME + "=" + id);

    HttpEntity<String> entity = new HttpEntity<String>(headers);

    return entity;
  }

  private ResponseEntity<String> checkAccessFobidden(
      HttpEntity<String> entity) {
    // Anonymous connection
    ResponseEntity<String> resp = template.exchange(
        AuthSharedTestConstants.TEST_URL, HttpMethod.GET, entity, String.class);

    // Expected 403
    assertEquals(HttpStatus.FORBIDDEN, resp.getStatusCode());

    return resp;
  }

  public HttpHeaders checkAuthOk() {
    // Create security context for test
    List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
    for (String role : AuthSharedTestConstants.TEST_ROLES.split(","))
      roles.add(new SimpleGrantedAuthority(role));

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        new User(AuthSharedTestConstants.TEST_USER_NAME, "[PROTECTED]", roles),
        "[PROTECTED]", roles);
    SecurityContext context = new SecurityContextImpl();
    context.setAuthentication(token);

    repository.setRedisKeyNamespace(Constants.SESSION_NAMESPACE);

    // For test purposes set low inactive interval 1 sec
    repository.setDefaultMaxInactiveInterval(TEST_SESSION_TIMEOUT);
    RedisSessionWrapper<Session> wrapper = new RedisSessionWrapper<Session>(
        repository);

    Session session = wrapper.create(context);
    sessionId = session.getId();

    // Prepare http headers
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.COOKIE,
        Constants.COOKIE_SESSION_NAME + "=" + sessionId);

    checkTestConnection(headers, HttpStatus.OK, getResponseStr());

    return headers;
  }

  private String getResponseStr() {
    return Constants.USER_NAME_KEY + ":" +
        AuthSharedTestConstants.TEST_USER_NAME + "|authenticationName:" +
        AuthSharedTestConstants.TEST_USER_NAME + "|roles:" +
        AuthSharedTestConstants.TEST_ROLES + "|sessionId:" + sessionId;
  }

  private void checkTestConnection(HttpHeaders headers, HttpStatus status,
      String bstr) {
    checkConnection(headers, status, bstr, AuthSharedTestConstants.TEST_URL);
  }

  private void checkConnection(HttpHeaders headers, HttpStatus status,
      String bstr, String url) {
    HttpEntity<String> entities = new HttpEntity<String>(headers);

    // Connect with previously prepared session id
    ResponseEntity<String> response = template.exchange(url, HttpMethod.GET,
        entities, String.class);

    // Check response from success connection
    assertEquals(status, response.getStatusCode());

    if (bstr == null)
      assertNull(response.getBody());
    else {
      assertTrue(response.hasBody());

      // Should be something in a body
      String body = response.getBody();
      assertNotNull(body);

      if (bstr.charAt(0) == '^')
        // Check regex
        Pattern.compile(bstr).matcher(body).matches();
      else
        // Check exact string
        assertEquals(bstr, body);
    }

    // No returned cookies expected
    assertNull(response.getHeaders().getFirst(HttpHeaders.COOKIE));
    assertNull(response.getHeaders().getFirst(HttpHeaders.SET_COOKIE));

  }

}

@SpringBootApplication
class SpringSessionTestApp {

}
