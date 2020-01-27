/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 * Date: 2017-04-19
 * 
 */

package com.osbitools.ws.rest;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.osbitools.ws.rest.prj.rest.shared.common.PrjAppSharedTestAppConfig;

/**
 * Configuration class for Spring Boot Test Launcher.
 * This SpringBoot configuration class should be in most top directory 
 * to allow shared test loaded from com.osbitools.ws.rest.prj.web.shared.it.*
 * package
 * 
 */

@SpringBootApplication
public class PdAppSharedTestAppConfig extends PrjAppSharedTestAppConfig  {
  
}
