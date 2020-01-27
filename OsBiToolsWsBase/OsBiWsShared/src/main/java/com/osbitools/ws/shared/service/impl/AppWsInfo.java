/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2016-26-05
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.jar.Manifest;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

import com.osbitools.ws.shared.service.WsInfo;

/**
 * Web App Info class
 * 
 */

public class AppWsInfo implements WsInfo {

  @Autowired
  @Qualifier("log")
  private Logger _log;

  // Non Maven flag
  private boolean _non_maven;

  // Detected version
  private String _version;

  // App name
  private String _name;

  public static final String MANIFEST_PATH = "/META-INF/MANIFEST.MF";

  /**
   * Default constructor
   * 
   * @param name Application name
   */
  public AppWsInfo(String name) {
    _name = name;
  }

  /**
   * Open and check version from Manifest.mf
   * 
   * @param ctx
   *          Servlet Context
   * @throws IOException
   */
  @PostConstruct
  public void init() {
    String classPath = this.getClass()
        .getResource(this.getClass().getSimpleName() + ".class").toString();

    InputStream is = null;
    try {
      is = classPath.startsWith("jar")
          ? new URL(classPath.substring(0, classPath.lastIndexOf("!") + 1) +
              MANIFEST_PATH).openStream()
          : this.getClass().getResourceAsStream(MANIFEST_PATH);
    } catch (IOException e) {
      _log.error("Error reading Version from MANIFEST.MF: " + e.getMessage());
    }

    _non_maven = (is == null);

    if (!_non_maven) {
      Manifest m;
      try {
        m = new Manifest(is);
        _version = m.getMainAttributes().getValue("Version");
      } catch (IOException e) {
        _version = "Unknown";
      }
    }

    _log.info("Starting Web Service " + (_name != null ? _name : "local") +
        (hasMavenVersion() ? " v." + _version : ""));
  }

  public boolean hasMavenVersion() {
    return !(_non_maven || StringUtils.isEmpty(_version));
  }

  private String getVersion() {
    return _version;
  }

  @Override
  public String getBuildVersion() {
    return getVersion();
  }

  @Override
  public String getWsVersion() {
    return getVersion();
  }
}
