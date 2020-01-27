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

package com.osbitools.ws.rest.prj.shared.config;

import java.net.URL;

import com.osbitools.ws.shared.config.BaseAppWsConfigProperties;

/**
 * Wrapper class for Project Manager application properties.
 * 
 */

public class PrjWsConfigProperties extends BaseAppWsConfigProperties {

  private Boolean minified;

  private String gitRemoteName;

  private URL gitRemoteUrl;

  private Integer maxUploadFileSize;

  public Boolean getMinified() {
    return minified;
  }

  public void setMinified(Boolean minified) {
    this.minified = minified;
  }

  public String getGitRemoteName() {
    return gitRemoteName;
  }

  public void setGitRemoteName(String gitRemoteName) {
    this.gitRemoteName = gitRemoteName;
  }

  /**
   * @return the gitRemoteUrl
   */
  public URL getGitRemoteUrl() {
    return gitRemoteUrl;
  }

  /**
   * @param gitRemoteUrl the gitRemoteUrl to set
   */
  public void setGitRemoteUrl(URL gitRemoteUrl) {
    this.gitRemoteUrl = gitRemoteUrl;
  }

  public Integer getMaxUploadFileSize() {
    return maxUploadFileSize;
  }

  public void setMaxUploadFileSize(Integer maxUploadFileSize) {
    this.maxUploadFileSize = maxUploadFileSize;
  }
}
