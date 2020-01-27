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

package com.osbitools.ws.core.shared.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import com.osbitools.ws.shared.DsConstants;
import com.osbitools.ws.shared.config.BaseAppWsConfig;
import com.osbitools.ws.shared.config.PropertyItem;

/**
 * Ws Configuration class for Core REST Service
 * 
 */

public class CoreWsConfig extends BaseAppWsConfig {
  
  // Serial Version UID
  private static final long serialVersionUID = 1L;
 
  // Trace flag. Used to trace execution time for datasources creation.
  @PropertyItem
  private Boolean trace;

  // Rescan Interval flag
  @PropertyItem
  private Integer rescan;

  // Real Time Baskets
  @PropertyItem
  private List<String> rtBaskets;
  
  // Base DataSet Map directory
  private String _bdir;

  /**
   * Default constructor
   */
  public CoreWsConfig() {
    super();
  }

  /**
   * Class Constructor with external property file only
   * 
   * @param fileName Name of external file
   */
  public CoreWsConfig(String fileName) {
    super(fileName);
  }

  public CoreWsConfig(String fileName, Logger logger) {
    super(fileName, logger);
  }

  public CoreWsConfig(String fileName, String homeDir, Logger log) {
    super(fileName, homeDir, log);
  }

  /**
   * Bean constructor with all parameters to configure
   * 
   * @param homeDir Home Directory
   * @param debug Debug flag
   * @param trace Trace flag
   * @param defLang Default Language
   * @param log Pointer on Logger instance
   */
  public CoreWsConfig(String fileName, String homeDir, String debug, String lang, String trace,
      String rescan, Logger log) throws NumberFormatException {
    super(fileName, homeDir, debug, lang, log);

    setTrace(trace);
    setRescan(rescan);
    this.trace = Boolean.parseBoolean(trace);
    this.rescan = Integer.parseInt(rescan);
  }

  @Override
  public void setHomeDir(String homeDir) {
    super.setHomeDir(homeDir);
    _bdir = getHomePath() + File.separator + DsConstants.DS_DIR;
  }

  @Override
  public String getPrefix() {
    return "core_ws_cfg";
  }
  
  public Integer getRescanInterval() {
    return rescan;
  }

  public Integer getRescan() {
    return rescan;
  }

  public void setRescan(Integer rescanInterval) {
    this.rescan = rescanInterval;
  }

  public void setRescan(String rescanInterval) {
    this.rescan = Integer.parseInt(rescanInterval);
  }

  public Boolean getTrace() {
    return trace;
  }

  public void setTrace(Boolean trace) {
    this.trace = trace;
  }

  public void setTrace(String trace) {
    setTrace(Boolean.parseBoolean(trace));
  }

  public String getDsDir() {
    return _bdir;
  }

  @Override
  public String[] getHomeSubDirList() {
    return DsConstants.DS_DIR_LIST;
  }

  /**
   * @return the rtBaskets
   */
  public List<String> getRtBaskets() {
    return rtBaskets;
  }

  /**
   * @param rtBaskets the rtBaskets to set
   */
  public void setRtBaskets(List<String> rtBaskets) {
    this.rtBaskets = rtBaskets;
  }

  /**
   * @param rtBaskets the rtBaskets to set
   */
  public void setRtBaskets(String rtBaskets) {
    if (rtBaskets != null)
      this.rtBaskets = Arrays.asList(rtBaskets.split(","));
  }
  
  @Override
  public String toString() {
    return super.toString() + " trace=" + trace + "; rescan=" + rescan + ";" + 
        ((rtBaskets != null) ? rtBaskets.stream().map(String::valueOf).
            collect(Collectors.joining("<")) : "");
  }
}
