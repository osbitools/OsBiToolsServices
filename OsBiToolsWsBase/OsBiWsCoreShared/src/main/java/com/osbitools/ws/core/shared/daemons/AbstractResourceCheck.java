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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.core.shared.common.CoreConstants;
import com.osbitools.ws.core.shared.config.CoreWsConfig;

/**
 * 
 * Template for Resource Checking thread
 * 
 */

public abstract class AbstractResourceCheck<T1, T2 extends ResourceInfo<T1>> extends Thread {

  @Autowired
  @Qualifier("core_ws_cfg")
  private CoreWsConfig _cfg;

  // Logger
  @Autowired
  @Qualifier("log")
  private Logger _log;

  // List of files to scan
  private ConcurrentHashMap<String, ResourceInfo<T1>> _flist =
      new ConcurrentHashMap<String, ResourceInfo<T1>>();

  // Handle for XML Unmarshaller
  private final Unmarshaller _um;

  // Init flag
  boolean _finit = false;

  // Class name of included resources
  private final Class<T2> _rclass;

  // Constructor that used for resource reflection
  private final Constructor<T2> _cs;

  protected AbstractResourceCheck(Class<T2> resClassName)
      throws JAXBException, NoSuchMethodException, SecurityException {
    _rclass = resClassName;
    _cs = _rclass.getConstructor(ResourceInfo.class);

    _um = JAXBContext.newInstance(getBindPkg()).createUnmarshaller();
  }

  @Override
  @PostConstruct
  public void start() {
    super.start();
    _log.info("Started " + _rclass.getSimpleName());
  }

  abstract String getBindPkg();

  @SuppressWarnings("unchecked")
  T2 doAfterResourceLoaded(ResourceInfo<T1> info) throws WsSrvException {
    T2 res;
    if (info != null && info.getClass().equals(_rclass)) {
      res = (T2) info;
      info.init();
    } else {
      // Reflection
      try {
        res = _cs.newInstance(info);
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
          | InvocationTargetException e) {
        //-- 137
        throw new WsSrvException(137, e);
      }
    }

    return res;
  }

  public abstract void scan(File dir, String prefix);

  abstract int[][] getVersions(T1 res);

  @SuppressWarnings("unchecked")
  public T2 getResource(String key) {
    ResourceInfo<T1> info = _flist.get(key);

    return (info == null || info.isBad()) ? null : (T2) info;
  }

  /**
   * Return array with all loaded resources
   * 
   * @return array all loaded resources
   */
  protected String[] getAllResources() {
    Set<String> list = _flist.keySet();
    return list.toArray(new String[list.size()]);
  }

  public int getResListSize() {
    return _flist.size();
  }

  void clear() {
    _flist.clear();
  }

  public String getBasePath() {
    return _cfg.getDsDir();
  }

  protected void checkResourceFile(String key, String path, String prjName) {
    ResourceInfo<T1> info = _flist.get(key);

    try {
      if (info == null) {
        // Load new file
        info = read(path, prjName);

        _flist.put(key, info);
        if (info != null)
          info("Successfully loaded file '" + key + "'.");
      } else {
        // Check updated
        info.setUpdated();

        // Check datestamp
        if (info.isModified()) {
          // Reload info
          update(info);

          info("Resource file '" + key + "' has been reloaded.");
        }
      }
    } catch (FileNotFoundException e) {
      debug("File '" + path + "' not found.");
      return;
    } catch (WsSrvException e) {
      error(e);
      return;
    }
  }

  @Override
  public void run() {
    while (!isInterrupted()) {
      doCheck();

      // Sleep
      try {
        Thread.sleep(_cfg.getRescanInterval() != null 
            ? _cfg.getRescanInterval()
            : CoreConstants.DEFAULT_RESCAN_TIME);
      } catch (InterruptedException e) {
        break;
      }

      _finit = true;
    }
  }

  private void doCheck() {
    // Quick check if directory set
    if (_cfg.getDsDir() == null) {
      warn("DsDir is not set. Skipping the file scan");
      // Skip
      return;
    }
    
    File dir = new File(_cfg.getDsDir());
    if (!dir.exists()) {
      error("Nothing to do. Directory '" + _cfg.getDsDir() + "' doesn't exists.");
      return;
    }

    // Reset updated flag
    for (Entry<String, ResourceInfo<T1>> entry : _flist.entrySet())
      entry.getValue().resetUpdated();

    scan(new File(_cfg.getDsDir()), "");

    // Check and delete entries that not updated
    Object[] fnames = _flist.keySet().toArray();

    for (Object fname : fnames) {
      if (!_flist.get(fname).isUpdated()) {
        _flist.remove(fname);
        info("File '" + fname + "' not found and resource '" + fname + "' has been removed.");
      }
    }
  }

  ResourceInfo<T1> readFile(String fileName, String prjName)
      throws FileNotFoundException, WsSrvException {
    return readFile(new File(fileName), prjName);
  }

  ResourceInfo<T1> readFile(File f, String prjName)
      throws FileNotFoundException, WsSrvException {
    ResourceInfo<T1> res = new ResourceInfo<T1>(readResouce(f), f, prjName);

    checkVersions(res);
    return res;
  }

  @SuppressWarnings("unchecked")
  T1 readResouce(File f) throws FileNotFoundException {
    Object res = null;
    JAXBElement<?> jaxb;

    try {
      jaxb = (JAXBElement<?>) _um.unmarshal(new FileInputStream(f));
      res = jaxb.getValue();
    } catch (JAXBException e) {
      error("Error loading '" + f.getAbsolutePath() + "'. " + e.getMessage());
    }

    return (T1) res;
  }

  public synchronized ResourceInfo<T1> read(String fileName, String prjName)
      throws FileNotFoundException, WsSrvException {
    ResourceInfo<T1> info = readFile(fileName, prjName);
    return info.isBad() ? info : doAfterResourceLoaded(info);
  }

  public synchronized void update(ResourceInfo<T1> info)
      throws FileNotFoundException, WsSrvException {
    // Get new resource
    T1 obj = readResouce(info.getFileHandle().getAbsoluteFile());

    if (obj != null) {
      info.setResource(obj);

      // Check if version supported
      checkVersions(info);

      doAfterResourceLoaded(info);
    }
  }

  public boolean isInit() {
    return _finit;
  }

  /*************************/

  /** Log Wrappers **/
  /*************************/

  void debug(String msg) {
    _log.debug(msg);
  }

  void error(String msg) {
    _log.error(msg);
  }

  void error(WsSrvException e) {
    error(("ERROR #" + e.getErrorCode() + " " + e.getErrorInfo()));
    String[] dmsg = e.getDetailMsgs();
    if (!BaseUtils.isEmpty(dmsg)) {
      for (String s : dmsg)
        error("ERROR DETAILS:" + s);
    }
  }

  void info(String msg) {
    _log.info(msg);
  }

  void warn(String msg) {
    _log.warn(msg);
  }

  void checkVersions(ResourceInfo<T1> info) throws WsSrvException {
    T1 rs = info.getResource();
    if (rs == null)
      return;

    int[][] versions = getVersions(rs);
    checkVersionsEx(versions[0], versions[1]);
  }

  public void checkVersionsEx(int[] xmlVer, int[] appVer) throws WsSrvException {
    if (xmlVer[0] > appVer[0])
      //-- 101
      throw new WsSrvException(101, "Major xml file version: " + xmlVer[0] +
          " is higher than supported version: " + appVer[0]);
    else if (xmlVer[1] > appVer[1])
      //-- 102
      throw new WsSrvException(102, "Minor xml file version: " + xmlVer[1] +
          " is higher than supported version: " + appVer[1]);
  }

  @Override
  @PreDestroy
  public void interrupt() {
    super.interrupt();
    try {
      super.join();
    } catch (InterruptedException e) {
      _log.error(e.getMessage());
    }
    _log.info("Terminated " + _rclass.getSimpleName());
  }
}
