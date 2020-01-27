/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2016-06-09
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.config;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.slf4j.Logger;

import com.osbitools.ws.rest.prj.shared.common.PrjMgrBaseConstants;
import com.osbitools.ws.shared.ExtListFileFilter;
import com.osbitools.ws.shared.config.BaseAppWsConfig;
import com.osbitools.ws.shared.config.PropertyItem;

/**
 * Common configuration for all OsBiTools REST services that supports Web UI
 * 
 */

public abstract class AbstractPrjWsConfig extends BaseAppWsConfig {

  // Serial Version UID
  private static final long serialVersionUID = 1L;

  // Minified flag
  // Controls the formatting of saved files
  @PropertyItem
  private Boolean minified;

  // Remote Git name
  @PropertyItem
  private String gitRemoteName;

  // Remote Git name
  @PropertyItem
  private URL gitRemoteUrl;

  // Max upload file size, mb
  @PropertyItem
  private Integer maxUploadFileSize;

  // Read only fields for Max upload file size, bytes
  private Integer maxUploadFileSizeBytes;

  // Base DataSet Map directory
  private String _bdir;

  // List of all supported extensions
  private HashSet<String> _exts = new HashSet<String>();

  // List of file filters for each resource directory
  private final HashMap<String, FilenameFilter> _ffilters =
      new HashMap<String, FilenameFilter>();

  // Indexed list of supported extensions
  private final HashMap<String, HashSet<String>> _extl =
      new HashMap<String, HashSet<String>>();

  /**
   * Default constructor
   */
  public AbstractPrjWsConfig() {
    super();
    
    // The last call
    indexSupportedExtList();
  }
  

  public AbstractPrjWsConfig(String fileName) {
    super(fileName);

    // The last call
    indexSupportedExtList();
  }

  public AbstractPrjWsConfig(String fileName, Logger logger) {
    super(fileName, logger);

    // The last call
    indexSupportedExtList();
  }

  public AbstractPrjWsConfig(String fileName, String homeDir, Logger logger) {
    super(fileName, homeDir, logger);

    // The last call
    indexSupportedExtList();
  }

  public AbstractPrjWsConfig(String fileName, String homeDir, String debug, String lang,
      String minified, String maxUploadFileSize, String gitRemoteName, String gitRemoteUrl,
      Logger logger) throws MalformedURLException {
    super(fileName, homeDir, debug, lang, logger);

    setMinified(minified);
    setMaxUploadFileSize(maxUploadFileSize);
    setGitRemoteName(gitRemoteName);
    setGitRemoteUrl(gitRemoteUrl);

    // The last call
    indexSupportedExtList();
  }

  public abstract String getHomeSubDir();

  public abstract Map<String, String[]> getSubDirExtList();

  public String getBaseExt() {
    return PrjMgrBaseConstants.WEBUI_BASE_EXT;
  }

  private void indexSupportedExtList() {
    // Index supported extensions
    Map<String, String[]> sdirs = getSubDirExtList();

    for (String name : sdirs.keySet()) {
      String[] extl = sdirs.get(name);
      HashSet<String> sx = new HashSet<String>();

      for (String ext : extl) {
        sx.add(ext);
        if (!_exts.contains(ext))
          _exts.add(ext);
      }

      _ffilters.put(name, new ExtListFileFilter(extl));
      _extl.put(name, sx);
    }
  }

  public HashSet<String> getExtList(String name) {
    return _extl.get(name);
  }

  public boolean hasExt(String ext) {
    return _exts.contains(ext);
  }

  public FilenameFilter getExtLstFilenameFilter(String name) {
    return _ffilters.get(name);
  }

  @Override
  public void setHomeDir(String homeDir) {
    super.setHomeDir(homeDir);

    _bdir = getHomePath() + File.separator + getHomeSubDir();
  }

  public String getGitRemoteName() {
    return gitRemoteName;
  }

  public void setGitRemoteName(String gitRemoteName) {
    this.gitRemoteName = gitRemoteName;
  }

  public String getBaseDir() {
    return _bdir;
  }

  @Override
  public String[] getHomeSubDirList() {
    return new String[] { getHomeSubDir() };
  }

  public Boolean getMinified() {
    return minified;
  }

  public void setMinified(Boolean minified) {
    this.minified = minified;
  }

  public void setMinified(String minified) {
    this.minified = Boolean.parseBoolean(minified);
  }

  /**
   * @return the maxUploadFileSize
   */
  public Integer getMaxUploadFileSize() {
    return maxUploadFileSize;
  }

  /**
   * @return the maxUploadFileSizeBytes in megabytes
   */
  public Integer getMaxUploadFileSizeBytes() {
    return maxUploadFileSizeBytes;
  }

  /**
   * @param maxUploadFileSize the maxUploadFileSize to set
   */
  public void setMaxUploadFileSize(Integer maxUploadFileSize) {
    this.maxUploadFileSize = maxUploadFileSize;

    // Convert number to mb
    if (maxUploadFileSize != null)
      this.maxUploadFileSizeBytes = maxUploadFileSize * 1024 * 1024;
  }

  /**
   * @param maxUploadFileSize the maxUploadFileSize to set
   */
  public void setMaxUploadFileSize(String maxUploadFileSize) {
    setMaxUploadFileSize(Integer.parseInt(maxUploadFileSize));
  }

  public URL getGitRemoteUrl() {
    return gitRemoteUrl;
  }

  public void setGitRemoteUrl(URL gitRemoteUrl) {
    this.gitRemoteUrl = gitRemoteUrl;
  }

  public void setGitRemoteUrl(String gitRemoteUrl) throws MalformedURLException {
    setGitRemoteUrl(new URL(gitRemoteUrl));
  }

  @Override
  public String toString() {
    return super.toString() + " minified=" + getMinified() + "; max_upload_file_size=" +
        maxUploadFileSize + "; git_remote_name=" + gitRemoteName + "; git_remote_url=" +
        gitRemoteUrl + ";";
  }
}
