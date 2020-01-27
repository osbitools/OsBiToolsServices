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

package com.osbitools.ws.rest.auth.shared.session;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;

import com.osbitools.ws.rest.auth.common.Constants;
import com.osbitools.ws.rest.auth.shared.common.AuthSharedTestConstants;

/**
 * RedisSessionWrapper
 * 
 * @see https://github.com/spring-projects/spring-session/issues/79
 * 
 * @param <S> Session Type
 */
public class RedisSessionWrapper<S extends Session> {

  private SessionRepository<S> repository;

  @SuppressWarnings("unchecked")
  public RedisSessionWrapper(RedisOperationsSessionRepository repository) {
    this.repository = (SessionRepository<S>) repository;
  }

  public S create(SecurityContext context) {
    S s = repository.createSession();
    // Inject test attributes
    s.setAttribute(Constants.USER_NAME_KEY, AuthSharedTestConstants.TEST_USER_NAME);
    s.setAttribute(
        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
        context);
    repository.save(s);

    return s;
  }
}
