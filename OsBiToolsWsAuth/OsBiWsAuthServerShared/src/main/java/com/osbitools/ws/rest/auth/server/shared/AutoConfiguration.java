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

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.osbitools.ws.rest.auth.common.Constants;

/**
 * Spring Boot Launcher
 * 
 */
@Configuration
@ComponentScan("com.osbitools.ws.rest.auth.server.shared")
public class AutoConfiguration {

	/**
	 * Inject user name was used during login into standard http session
	 * 
	 * @param event
	 */
	@EventListener
	public void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
		(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession(true))
				.setAttribute(Constants.USER_NAME_KEY,
						((UsernamePasswordAuthenticationToken) event.getSource()).getName());
	}
}
