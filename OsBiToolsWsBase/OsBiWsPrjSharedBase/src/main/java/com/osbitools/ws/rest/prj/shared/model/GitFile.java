/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-01-01
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.model;

/**
 * DTO File for Entity Response
 * 
 */

public class GitFile {

  private byte[] data;

  private Boolean hasLog;

  private Boolean hasDiff;

  public GitFile(byte[] data, Boolean hasLog, Boolean hasDiff) {
    this.data = data;
    this.hasLog = hasLog;
    this.hasDiff = hasDiff;
  }

  /**
   * @return the data
   */
  public byte[] getData() {
    return data;
  }

  /**
   * @param data the data to set
   */
  public void setData(byte[] data) {
    this.data = data;
  }

  /**
   * @return the hasLog
   */
  public Boolean getHasLog() {
    return hasLog;
  }

  /**
   * @param hasLog the hasLog to set
   */
  public void setHasLog(Boolean hasLog) {
    this.hasLog = hasLog;
  }

  /**
   * @return the hasDiff
   */
  public Boolean getHasDiff() {
    return hasDiff;
  }

  /**
   * @param hasDiff the hasDiff to set
   */
  public void setHasDiff(Boolean hasDiff) {
    this.hasDiff = hasDiff;
  }
}
