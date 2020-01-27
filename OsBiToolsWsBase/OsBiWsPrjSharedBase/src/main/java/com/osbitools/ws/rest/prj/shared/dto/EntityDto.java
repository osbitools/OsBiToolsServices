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

package com.osbitools.ws.rest.prj.shared.dto;

/**
 * DTO File for Entity Response
 * 
 */

public class EntityDto<T> {

  private T entity;

  private Boolean hasLog;

  private Boolean hasDiff;

  /**
   * Default contstructor
   */
  public EntityDto() {
  }

  public EntityDto(T entity, Boolean hasLog, Boolean hasDiff) {
    this.entity = entity;
    this.hasLog = hasLog;
    this.hasDiff = hasDiff;
  }

  /**
   * @return the entity
   */
  public T getEntity() {
    return entity;
  }

  /**
   * @param entity
   *          the entity to set
   */
  public void setEntity(T entity) {
    this.entity = entity;
  }

  /**
   * @return the hasLog
   */
  public Boolean getHasLog() {
    return hasLog;
  }

  /**
   * @param hasLog
   *          the hasLog to set
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
   * @param hasDiff
   *          the hasDiff to set
   */
  public void setHasDiff(Boolean hasDiff) {
    this.hasDiff = hasDiff;
  }
}
