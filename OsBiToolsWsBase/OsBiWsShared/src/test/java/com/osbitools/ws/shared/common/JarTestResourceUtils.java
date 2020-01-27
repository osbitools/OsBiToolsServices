/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2015-05-04
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import com.osbitools.ws.base.BaseUtils;

/**
 * Generic utilities read test file resources out of jar file
 * 
 */
public abstract class JarTestResourceUtils {
  
  /**
   * Copy all demo files from jar file into physical directory
   * 
   * @param base Top root directory to copy files
   * @param dir Sub-directory from top resource tree 
   *          defined by Package Name to read test files from
   * @param cfg Resource Configuration object
   * @throws IOException
   * @throws URISyntaxException
   */
  public static void copyJarDemoProj(String base, String dir, 
             ITestResourceConfig cfg) throws IOException, URISyntaxException {
    JarUtils.copyJarDir(base, cfg.getTopResDir(), dir);
  }
  
  /**
   * Read Main Demo File from jar file into string
   * 
   * @return String with Main Demo File
   * @param cfg Resource Configuration object
   * @return String with file content
   * @throws IOException 
   */
  public static String readMainDemoFileAsText(ITestResourceConfig cfg) 
                                                    throws IOException {
    return readDemoFileAsText(cfg.getMainDemoFileName(), cfg);
  }

  /**
   * Read Demo File Name from jar file
   * @param fname 
   * @param cfg Resource Configuration object
   * @return String with Main Demo File
   * @throws IOException 
   */
  public static String readDemoFileAsText(String fname, 
                            ITestResourceConfig cfg) throws IOException {
    return JarUtils.readJarFileAsText(cfg.getTopResDir() + "/" + fname);
  }
  
  /**
   * Read Main Demo File from jar file into input stream
   * 
   * @param cfg Resource Configuration object
   * @return InputStream with Main Demo File
   * @throws IOException 
   */
  public static InputStream readMainDemoFileAsStream(
                        ITestResourceConfig cfg) throws IOException {
    return readDemoFileAsStream(cfg.getMainDemoFileName(), cfg);
  }
  
  /**
   * Read File by Name from jar file
   * 
   * @param fname File Name
   * @param cfg Resource Configuration object
   * @return InputStream with Main Demo File
   * @throws IOException 
   */
  public static InputStream readDemoFileAsStream(String fname, 
                          ITestResourceConfig cfg) throws IOException {
    return JarUtils.readJarFileAsStream(cfg.getTopResDir() + "/" + fname);
  }
  
  /**
   * Copy Demo File Name into external file
   * 
   * @param name File Name inside jar file
   * @param fname File Name output file name
   * @param cfg Resource Configuration object
   * @throws IOException
   */
  public static void copyDemoFileToFile(String name, String fname, 
                              ITestResourceConfig cfg) throws IOException {
    BaseUtils.copyToFile(readDemoFileAsStream(name, cfg), fname);
  }
}
