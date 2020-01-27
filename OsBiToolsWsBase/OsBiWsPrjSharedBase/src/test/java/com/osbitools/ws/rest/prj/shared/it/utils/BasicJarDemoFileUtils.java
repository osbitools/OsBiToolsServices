/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2015-03-15
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.it.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import com.osbitools.ws.rest.prj.shared.common.TestPrjMgrBaseConstants;
import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.shared.common.ITestResourceConfig;
import com.osbitools.ws.shared.common.JarTestResourceUtils;
import com.osbitools.ws.shared.common.JarUtils;

/**
 * Generic utilities read demo demo resources from jar file
 * 
 */
public abstract class BasicJarDemoFileUtils extends BasicDemoFileUtils {

  private final ITestResourceConfig _ds_cfg;

  public BasicJarDemoFileUtils(ITestResourceConfig cfg) {
    _ds_cfg = cfg;
  }

  /**
   * Return name of 2 common files used for test from top project directory
   * 
   * @return
   */
  public abstract String[][] getTestEntities();

  public abstract String getMainDemoFileJson();

  public abstract String[][] getDemoEntities();

  @Override
  public void copyDemoFileToFile(String name, String fname) throws IOException {
    JarTestResourceUtils.copyDemoFileToFile(name, fname, _ds_cfg);
  }

  /**
   * {@link com.osbitools.ws.shared.prj.utils.BasicJarDemoFileUtils#copyDemoProj(String)}
   */
  @Override
  public void copyDemoProj(String base) throws IOException {
    copyDemoProj(base, "");

    // Add bad.xml file
    BaseUtils.copyToFile("Bad File", base + File.separator + "bad.xml");
  }

  @Override
  public void copyDemoProj(String base, String dir) throws IOException {
    try {
      JarTestResourceUtils.copyJarDemoProj(base, dir, _ds_cfg);
    } catch (URISyntaxException e) {
      throw new IOException(e);
    }
  }

  @Override
  public String[][] getDemoSet() {
    String[][] entities = getDemoEntities();
    String[][] res = new String[entities.length][2];
    for (int i = 0; i < entities.length; i++) {
      String fname = entities[i][0];
      res[i][0] = fname;
      try {
        // Demo files located in top folder
        res[i][1] = readFileAsText(fname);
      } catch (IOException e) {
        res[i][1] = e.getMessage();
      }
    }

    return res;
  }

  @Override
  public String[][] getTestSet() {
    String[][] fnames = getTestEntities();
    String[][] res = new String[fnames.length][2];
    for (int i = 0; i < fnames.length; i++) {
      String fname = fnames[i][0];
      res[i][0] = fname;
      try {
        res[i][1] = readFileAsText(fname);
      } catch (IOException e) {
        res[i][1] = e.getMessage();
      }
    }

    return res;
  }

  @Override
  public String[][] getJsonDemoSet() {
    String[][] entities = getDemoEntities();
    String[][] res = new String[entities.length][2];
    for (int i = 0; i < entities.length; i++) {
      res[i][0] = entities[i][0];
      res[i][1] = entities[i][1];
    }

    return res;
  }

  @Override
  public InputStream readDemoFileAsStream() throws IOException {
    return JarTestResourceUtils.readMainDemoFileAsStream(_ds_cfg);
  }

  @Override
  public InputStream readDemoFileAsStream(String fname) throws IOException {
    return JarTestResourceUtils.readDemoFileAsStream(fname, _ds_cfg);
  }

  @Override
  public String readDemoFileAsText() throws IOException {
    return JarTestResourceUtils.readMainDemoFileAsText(_ds_cfg);
  }

  @Override
  public String readFileAsText(String fname) throws IOException {
    return JarTestResourceUtils.readDemoFileAsText(fname, _ds_cfg);
  }

  @Override
  public String[] getDemoSrcSet() throws IOException {
    String[][] fnames = getTestEntities();
    return new String[] {
        JarUtils.readJarFileAsText(
            _ds_cfg.getTopResDir() + "/" + fnames[0][0] + "." + getBaseExt()),

        JarUtils.readJarFileAsText(
            _ds_cfg.getTopResDir() + "/" + fnames[1][0] + "." + getBaseExt()) };
  }

  @Override
  public String readDemoFileRespAsText(boolean minified) throws IOException {
    return TestPrjMgrBaseConstants.ENTYTY_PREFIX + getMainDemoFileJson();
  }

  @Override
  public String getProjSrcDir() {
    return _ds_cfg.getTopResDir();
  }

  @Override
  public String[][] getJsonTestSet() {
    String[][] ts = getTestEntities();
    String[][] res = new String[ts.length][2];
    for (int i = 0; i < ts.length; i++) {
      res[i][0] = ts[i][0];
      res[i][1] = ts[i][1];
    }
    return res;
  }
}
