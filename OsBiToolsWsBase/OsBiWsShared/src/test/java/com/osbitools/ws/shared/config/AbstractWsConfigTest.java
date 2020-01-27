/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2016-30-05
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared.config;

import static org.junit.Assert.*;

import java.util.Properties;

import com.osbitools.ws.shared.common.TestConstants;

public abstract class AbstractWsConfigTest<T extends BaseAppWsConfig>
    extends BaseWsConfigTestUnits<T> {

  protected final Boolean debug1 = Boolean.TRUE;
  protected final Boolean debug2 = Boolean.FALSE;

  protected final String lang1 = "en";
  protected final String lang2 = "fr";

  @Override
  protected void testInitBeanProps() throws Exception {
    assertEquals(debug1.toString(), config.getPropByName("debug"));
    assertEquals(lang1, config.getPropByName("lang"));
  }

  @Override
  protected void setInitConfig() {
    config.setDebug(debug1);
    config.setLang(lang1);
  }

  @Override
  protected void invertConfig() {
    config.setDebug(!config.getDebug());
    config.setLang(lang2);
  }

  protected void testConfigProperties(Properties props, Boolean debug, String lang) {
    assertEquals("debug parameter test failed", debug.toString(), props.getProperty("debug"));
    assertEquals("lang parameter test failed", lang, props.getProperty("lang"));
  }

  @Override
  protected void setProperties(Properties props) {
    props.setProperty("debug", config.getDebug().toString());
    props.setProperty("lang", config.getLang());
  }

  protected void testConfigBean(Boolean debug, String lang) {
    assertEquals("debug parameter test failed", debug, config.getDebug());
    assertEquals("lang parameter test failed", lang, config.getLang());
  }

  @Override
  protected void testInitConfigBean() {
    testConfigBean(debug1, lang1);
  }

  @Override
  protected void testIvertedConfigBean() {
    testConfigBean(debug2, lang2);
  }

  @Override
  protected void testInitConfigProperties(Properties props) {
    testConfigProperties(props, debug1, lang1);
  }

  @Override
  protected void testInvertedConfigProperties(Properties props) {
    testConfigProperties(props, debug2, lang2);
  }

  @Override
  protected String getString() {
    return "home_dir=" + TestConstants.WORK_OSBI_SHARED_DIR + "; debug=" + debug1 + "; lang=" +
        lang1 + ";";
  }
}
