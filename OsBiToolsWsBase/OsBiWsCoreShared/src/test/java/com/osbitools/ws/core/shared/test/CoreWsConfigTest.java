/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-01-01
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.test;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.shared.config.AbstractWsConfigTest;

public class CoreWsConfigTest extends AbstractWsConfigTest<CoreWsConfig> {

  protected final Boolean trace1 = Boolean.TRUE;
  protected final Boolean trace2 = Boolean.FALSE;

  protected final Integer rescan1 = 200;
  protected final Integer rescan2 = 300;

  @Override
  protected CoreWsConfig getNewConfigBean(String fileName) {
    return new CoreWsConfig(fileName, getLogger());
  }

  @Override
  protected CoreWsConfig getExternalConfigBean(String fileName, String dir) {
    return new CoreWsConfig(fileName, dir, debug1.toString(), lang1, trace1.toString(),
        rescan1.toString(), getLogger());
  }

  @Override
  protected void testInitBeanProps() throws Exception {
    super.testInitBeanProps();

    assertEquals(trace1.toString(), config.getPropByName("trace"));
    assertEquals(rescan1.toString(), config.getPropByName("rescan"));
  }

  @Override
  protected void testInitConfigBean() {
    testConfigBean(debug1, lang1, trace1, rescan1);
  }

  @Override
  protected void invertConfig() {
    super.invertConfig();

    config.setTrace(!config.getTrace());
    config.setRescan(rescan2);
  }

  @Override
  protected void testIvertedConfigBean() {
    testConfigBean(debug2, lang2, trace2, rescan2);
  }

  @Override
  protected void setInitConfig() {
    super.setInitConfig();
    config.setTrace(trace1);
    config.setRescan(rescan1);
  }

  protected void testConfigBean(boolean debug, String lang, boolean trace, Integer rescan) {
    testConfigBean(debug, lang);
    assertEquals("trace test parameter failed", trace, config.getTrace());
    assertEquals("rescan test parameter failed", rescan, config.getRescan());
  }

  protected void testConfigProperties(Properties props, Boolean debug, String lang,
      Boolean trace, Integer rescan) {
    testConfigProperties(props, debug, lang);

    assertEquals("trace test parameter failed", trace.toString(), props.getProperty("trace"));
    assertEquals("rescan test parameter failed", rescan.toString(),
        props.getProperty("rescan"));
  }

  @Override
  protected void testInitConfigProperties(Properties props) {
    testConfigProperties(props, debug1, lang1, trace1, rescan1);
  }

  @Override
  protected void testInvertedConfigProperties(Properties props) {
    testConfigProperties(props, debug2, lang2, trace2, 300);
  }

  @Override
  protected void setProperties(Properties props) {
    super.setProperties(props);
    props.setProperty("trace", config.getTrace().toString());
    props.setProperty("rescan", config.getRescan().toString());
  }

  @Override
  public String getString() {
    return super.getString() + " trace=" + trace1 + "; rescan=" + rescan1 + ";";
  }

}
