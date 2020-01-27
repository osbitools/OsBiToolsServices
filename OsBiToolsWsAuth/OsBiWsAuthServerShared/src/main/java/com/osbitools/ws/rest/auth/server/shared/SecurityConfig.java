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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import com.osbitools.ws.rest.auth.common.Constants;
import com.osbitools.ws.rest.auth.server.shared.config.ForbiddenStatusAuthenticationFailureHandler;
import com.osbitools.ws.rest.auth.server.shared.config.OkStatusAuthenticationSuccessHandler;

/**
 * Security Configuration
 * 
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private AuthenticationProvider _auth;
  
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    
	      // @formatter:off
	  
        http.csrf().disable().authorizeRequests()
          .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
          
          // Exclude Spring Actuator urls
          .antMatchers(HttpMethod.GET, "/actuator/info").permitAll()
          .antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
          
          .anyRequest().authenticated()
          
          .and().exceptionHandling().authenticationEntryPoint(
                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
          
          .and().formLogin()
            .usernameParameter(Constants.USERNAME_PARAM).passwordParameter(Constants.PASSWORD_PARAM)
            .loginProcessingUrl("/login")
            .successHandler(new OkStatusAuthenticationSuccessHandler())
            .failureHandler(new ForbiddenStatusAuthenticationFailureHandler())
            
          .and().logout().logoutUrl("/logout").deleteCookies("remove").invalidateHttpSession(true);
        
        // @formatter:on
	}
	
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(_auth);
  }
}
