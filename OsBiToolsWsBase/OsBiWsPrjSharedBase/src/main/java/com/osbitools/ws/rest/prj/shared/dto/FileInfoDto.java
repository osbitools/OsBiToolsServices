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
 * Date: 2018-01-01
 * 
 */

package com.osbitools.ws.rest.prj.shared.dto;

import java.time.LocalDateTime;

/**
 * DTO File for File Input
 * 
 */

public class FileInfoDto {
  
  // File Size
  private Long size;
  
  // Can Read flag
  private Boolean read;

  // Can Write flag
  private Boolean write;

  // Last modified time
  private LocalDateTime lastModified;

  public FileInfoDto(Long size, Boolean read, Boolean write, LocalDateTime lastModified) {
    this.size = size;
    this.read = read;
    this.write = write;
    this.lastModified = lastModified;
  }
  
  /**
   * @return the size
   */
  public Long getSize() {
    return size;
  }

  /**
   * @param size the size to set
   */
  public void setSize(Long size) {
    this.size = size;
  }

  /**
   * @return the read
   */
  public Boolean getRead() {
    return read;
  }

  /**
   * @param read the read to set
   */
  public void setRead(Boolean read) {
    this.read = read;
  }

  /**
   * @return the write
   */
  public Boolean getWrite() {
    return write;
  }

  /**
   * @param write the write to set
   */
  public void setWrite(Boolean write) {
    this.write = write;
  }

  /**
   * @return the lastModified
   */
  public String getLastModified() {
    return lastModified.toString();
  }

  /**
   * @param lastModified the lastModified to set
   */
  public void setLastModified(String lastModified) {
    this.lastModified = LocalDateTime.parse(lastModified);
  }
  
  
}
