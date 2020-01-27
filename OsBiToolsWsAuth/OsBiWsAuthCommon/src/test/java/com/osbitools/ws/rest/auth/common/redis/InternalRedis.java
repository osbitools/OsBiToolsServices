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

package com.osbitools.ws.rest.auth.common.redis;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

import redis.embedded.RedisServer;

/**
 * Internal Redis component
 * 
 */
@Component
public class InternalRedis {

  private static final Logger LOG = LoggerFactory
      .getLogger(InternalRedis.class);

  private int port;

  private RedisServer server;

  @Autowired
  public void setConnectionFactory(RedisConnectionFactory factory)
      throws IOException {
    this.port = ((JedisConnectionFactory) factory).getPort();
    this.server = new RedisServer(this.port);
  }

  @PostConstruct
  public void startRedis() throws IOException {
    LOG.info("Starting Internal Redis. Port: " + port);
    server.start();
  }

  @PreDestroy
  public void stopRedis() {
    server.stop();
  }
}
