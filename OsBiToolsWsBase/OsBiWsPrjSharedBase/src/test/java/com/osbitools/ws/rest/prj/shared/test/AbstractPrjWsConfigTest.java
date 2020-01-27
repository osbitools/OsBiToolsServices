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
 * 
 */

package com.osbitools.ws.rest.prj.shared.test;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import com.osbitools.ws.rest.prj.shared.config.AbstractPrjWsConfig;
import com.osbitools.ws.shared.config.AbstractWsConfigTest;

public abstract class AbstractPrjWsConfigTest<T extends AbstractPrjWsConfig>
    extends AbstractWsConfigTest<T> {

  protected final URL url1;
  protected final URL url2;

  protected Integer maxUploadFileSize1 = 100;
  protected Integer maxUploadFileSize2 = 10;

  protected final Boolean minified1 = Boolean.FALSE;
  protected final Boolean minified2 = Boolean.TRUE;

  protected final String gitRemoteName1 = "test_remote";
  protected final String gitRemoteName2 = "test_local";

  public AbstractPrjWsConfigTest() throws MalformedURLException {
    url1 = new URL("file:///etc");
    url2 = new URL("file:///tmp");
  }

  @Override
  protected void testInitBeanProps() throws Exception {
    super.testInitBeanProps();

    assertEquals("minified parameter test failed", minified1.toString(),
        config.getPropByName("minified"));
    assertEquals("maxUploadFileSize parameter test failed", maxUploadFileSize1.toString(),
        config.getPropByName("max_upload_file_size"));
    assertEquals("gitRemoteName parameter test failed", gitRemoteName1,
        config.getPropByName("git_remote_name"));
    assertEquals("gitRemoteUrl parameter test failed", url1.toString(),
        config.getPropByName("git_remote_url"));
  }

  @Override
  protected void setInitConfig() {
    super.setInitConfig();

    config.setMinified(minified1);
    config.setMaxUploadFileSize(maxUploadFileSize1);
    config.setGitRemoteName(gitRemoteName1);
    config.setGitRemoteUrl(url1);
  }

  @Override
  protected void invertConfig() {
    super.invertConfig();

    config.setMinified(!config.getMinified());
    config.setMaxUploadFileSize(maxUploadFileSize2);
    config.setGitRemoteName(gitRemoteName2);
    config.setGitRemoteUrl(url2);
  }

  protected void testConfigProperties(Properties props, Boolean debug, String lang,
      boolean minified, Integer maxUploadFileSize, String gitRemoteName, URL gitRemoteUrl) {
    testConfigProperties(props, debug, lang);

    assertEquals("minified parameter test failed", minified,
        Boolean.parseBoolean(props.getProperty("minified")));
    assertEquals("max_upload_file_size parameter test failed", maxUploadFileSize.toString(),
        props.getProperty("max_upload_file_size"));
    assertEquals("git_remote_name parameter test failed", gitRemoteName,
        props.getProperty("git_remote_name"));
    assertEquals("git_remote_url parameter test failed", gitRemoteUrl.toString(),
        props.getProperty("git_remote_url"));
  }

  @Override
  protected void setProperties(Properties props) {
    super.setProperties(props);

    props.setProperty("minified", config.getMinified().toString());
    props.setProperty("max_upload_file_size", config.getMaxUploadFileSize().toString());
    props.setProperty("git_remote_name", config.getGitRemoteName());
    props.setProperty("git_remote_url", config.getGitRemoteUrl().toString());
  }

  @Override
  protected void testInitConfigBean() {
    testConfigBean(debug1, lang1, minified1, maxUploadFileSize1, gitRemoteName1, url1);
  }

  @Override
  protected void testIvertedConfigBean() {
    testConfigBean(debug2, lang2, minified2, maxUploadFileSize2, gitRemoteName2, url2);
  }

  @Override
  protected void testInitConfigProperties(Properties props) {
    testConfigProperties(props, debug1, lang1, minified1, maxUploadFileSize1, gitRemoteName1,
        url1);
  }

  @Override
  protected void testInvertedConfigProperties(Properties props) {
    testConfigProperties(props, debug2, lang2, minified2, maxUploadFileSize2, gitRemoteName2,
        url2);
  }

  protected void testConfigBean(boolean debug, String lang, boolean minified,
      Integer maxUploadFileSize, String gitRemoteName, URL gitRemoteUrl) {
    testConfigBean(debug, lang);
    assertEquals("minified parameter test failed", minified, config.getMinified());
    assertEquals("maxUploadFileSize parameter test failed", maxUploadFileSize,
        config.getMaxUploadFileSize());
    assertEquals("gitRemoteName parameter test failed", gitRemoteName,
        config.getGitRemoteName());
    assertEquals("gitRemoteUrl parameter test failed", gitRemoteUrl, config.getGitRemoteUrl());
  }

  @Override
  protected String getString() {
    return super.getString() + " minified=" + minified1 + "; max_upload_file_size=" +
        maxUploadFileSize1 + ";" + " git_remote_name=test_remote; git_remote_url=" +
        url1.toString() + ";";
  }
}
