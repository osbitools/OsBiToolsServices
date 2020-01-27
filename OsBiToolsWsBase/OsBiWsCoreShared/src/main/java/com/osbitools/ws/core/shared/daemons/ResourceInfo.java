/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-11-07
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.daemons;

import java.io.File;

import com.osbitools.ws.base.WsSrvException;

/**
 * Resource handle together with last modified date
 *
 * @param <T> Type
 */
public class ResourceInfo<T> {

  // Resource Handle
  private T _res = null;

  // Last file modified date
  private long _modified;

  // File Handle
  File _f;

  // Updated flag
  private boolean _upd;

  // Project name
  private String _prj;

  public ResourceInfo(T resHandle, File f, String prjName) {
    _f = f;
    _upd = true;
    _prj = prjName;

    setResource(resHandle);
  }

  public ResourceInfo(ResourceInfo<T> info) throws WsSrvException {
    this(info.getResource(), info.getFileHandle(), info.getProjectName());
    init();
  }

  /*
  * Init resource info. This method must invoked after 
  *            resource info initially loaded or reloaded
  */
  public void init() throws WsSrvException {};

  public boolean isBad() {
    return _res == null;
  }

  public File getFileHandle() {
    return _f;
  }

  public synchronized T getResource() {
    return _res;
  }

  public synchronized boolean isModified() {
    // Check if file removed
    if (!_f.exists()) {
      // Reset modified flag and wait until it comes back
      _modified = 0;
      return false;
    }

    return _f.lastModified() > _modified;
  }

  public synchronized void setResource(T resHandle) {
    if (resHandle != null)
      _res = resHandle;
    _modified = _f.lastModified();
  }

  public void resetUpdated() {
    _upd = false;
  }

  public void setUpdated() {
    _upd = true;
  }

  public boolean isUpdated() {
    return _upd;
  }

  public String getProjectName() {
    return _prj;
  }
}
