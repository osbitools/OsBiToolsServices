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

package com.osbitools.ws.rest.auth.shared.common;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.util.ContextSelectorStaticBinder;
import ch.qos.logback.core.OutputStreamAppender;

/**
 * Test Constants
 *
 */
public class AuthSharedTestConstants {

  public static final String TEST_URL = "/foo";

  public static final String TEST_USER_NAME = "foo";

  public static final String TEST_ROLES = "ROLE_USER,ROLE_FOO";

  public static final ByteArrayOutputStream LOGS = new ByteArrayOutputStream(
      TEST_USER_NAME.length());

  public static final Logger LOG;

  static {
    // Build dynamic in-memory logger
    PatternLayoutEncoder encoder = new PatternLayoutEncoder();
    encoder.setPattern("%msg");
    encoder.setCharset(Charset.forName("UTF-8"));
    encoder.setContext(ContextSelectorStaticBinder.getSingleton()
        .getContextSelector().getDefaultLoggerContext());
    encoder.start();

    OutputStreamAppender<ILoggingEvent> appender = new OutputStreamAppender<ILoggingEvent>();
    appender.setName("streamAppender");
    appender.setContext(ContextSelectorStaticBinder.getSingleton()
        .getContextSelector().getDefaultLoggerContext());
    appender.setEncoder(encoder);
    appender.setOutputStream(LOGS);
    appender.start();

    ch.qos.logback.classic.Logger logger = ContextSelectorStaticBinder
        .getSingleton().getContextSelector().getDefaultLoggerContext()
        .getLogger("Test");
    logger.setLevel(Level.INFO);

    logger.addAppender(appender);

    LOG = LoggerFactory.getLogger("Test");
  }
}
