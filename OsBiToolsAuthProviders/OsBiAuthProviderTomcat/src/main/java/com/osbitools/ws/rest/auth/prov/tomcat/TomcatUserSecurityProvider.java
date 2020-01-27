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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.catalina.startup.CatalinaBaseConfigurationSource;
import org.apache.catalina.users.MemoryUserDatabase;
import org.apache.tomcat.util.file.ConfigFileLoader;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Authentication Provider based on Tomcat xml user configuration files
 * 
 */

@Component
public class TomcatUserSecurityProvider implements AuthenticationProvider {

	@Autowired
	private Logger _log;

	@Value("${auth.provider.tomcat.path}")
	String path;

	// Pointer on users database
	private MemoryUserDatabase _users = new MemoryUserDatabase();

	@PostConstruct
	public void init() {
		// Convert relative path into absolute because
		// Tomcat is using CATALINA_BASE_FILE prefix for relative path

		// Resolve home directory (if any)
		path = path.substring(0, 1).equals("~") ?
		// Replace ~ with user.home
				System.getProperty("user.home") + path.substring(1) :
				// Use as is
				path;

		File fname = new File(path);
		if (!fname.exists()) {
			_log.error("File [" + fname.getAbsolutePath() + "] not found.");
			return;
		}

		_users.setPathname(fname.getName());

		// Catalina configuration exists flag
		boolean fcat = false;
		try {
			fcat = (ConfigFileLoader.getSource() != null);
		} catch (IllegalStateException e) {
			fcat = false;
		}

		try {
			// Check if Catalina environment set
			if (fcat) {
				// Copy users file into existing environment
				Path dir = Paths.get(ConfigFileLoader.getSource().getURI(fname.getName()));
				_log.info("Detected existing Catalina configuration in the [" + dir + "]");
				Files.copy(fname.toPath(), dir);
			} else {
				// Set new environment
				File dir = fname.getParentFile();
				_log.info("Setting new Catalina configuration into [" + dir + "]");
				ConfigFileLoader.setSource(new CatalinaBaseConfigurationSource(dir, null));

			}
			_users.open();
			_log.info("Successfuly loaded user's database from [" + path + "]");
		} catch (Exception e) {
			_log.error(e.getMessage());

			// Reset user database handle
			_users = null;
		}
	}

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		// Quick check
		if (_users == null)
			throw new AuthenticationServiceException("User's database wasn't loaded");

		String name = auth.getName();
		_log.info("Authenticating user [" + name + "]");

		User user = _users.findUser(name);
		if (user == null) {
			String msg = "User [" + name + "] not found in user's database";
			_log.error(msg);
			throw new BadCredentialsException(msg);
		}

		String password = auth.getCredentials() != null ? auth.getCredentials().toString() : null;
		if (StringUtils.isEmpty(password)) {
			String msg = "Password for user [" + name + "] is " + (password == null ? "null" : "empty");
			_log.error(msg);
			throw new BadCredentialsException(msg);
		}

		if (!user.getPassword().equals(password)) {
			String msg = "Password for user [" + name + "] doesn't match configured.";
			_log.error(msg);
			throw new BadCredentialsException(msg);
		}

		// Get array of roles
		Iterator<Role> roles = user.getRoles();
		ArrayList<GrantedAuthority> rlist = new ArrayList<>();
		while (roles.hasNext())
			rlist.add(new SimpleGrantedAuthority(roles.next().getRolename()));

		_log.info("User [" + name + "] successfuly authenticated");
		return new UsernamePasswordAuthenticationToken(name, password, rlist);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	MemoryUserDatabase getUsers() {
		return _users;
	}
}
