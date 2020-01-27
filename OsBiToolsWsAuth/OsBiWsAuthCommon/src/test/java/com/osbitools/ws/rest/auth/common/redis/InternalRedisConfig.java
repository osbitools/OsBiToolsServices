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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.SocketUtils;

/**
 * Configuration for Internal Redis
 * 
 */
@Configuration
public class InternalRedisConfig {
  /**
   * Find random TCP port for internal Redis Use localhost as defalut host
   *
   * @return RedisConnectionFactory
   */
  @Bean
  @Primary
  public RedisConnectionFactory redisConnectionFactory() {
    return new JedisConnectionFactory(new RedisStandaloneConfiguration("localhost",
        SocketUtils.findAvailableTcpPort()));
  }
}
