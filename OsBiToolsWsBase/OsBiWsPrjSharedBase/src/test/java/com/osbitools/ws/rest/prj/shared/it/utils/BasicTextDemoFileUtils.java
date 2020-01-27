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

import java.io.*;
import java.net.URISyntaxException;

import org.springframework.util.StringUtils;

import com.osbitools.ws.rest.prj.shared.common.TestPrjMgrBaseConstants;
import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.shared.Utils;
import com.osbitools.ws.shared.common.GenericTestUtils;

/**
 * Generic utilities read demo text resources for unit test
 * 
 */
public abstract class BasicTextDemoFileUtils extends BasicDemoFileUtils {

  private static final String DEMO_FILE = "Demo 1\\Демо 1";

  private static final String[][] TEST_FILES = new String[][] {

      new String[] { "test1.txt", "Test 1\n" },

      new String[] { "test2.txt", "Test 2\n" }, };

  static public String[][] DEMO_FILES = new String[][] {

      new String[] { "test.demo1", "Test Demo 1\nTest Demo 1\n" },
      new String[] { "test.demo2", "Test Demo 2\nTest Demo 2\n" },
      new String[] { "test.demo3", "Test Demo 3\nTest Demo 3\n" },
      new String[] { "test.demo4", "Test Demo 4\nTest Demo 4\n" },
      new String[] { "test.demo5", "Test Demo 5\nTest Demo 5\n" } };

  public static final String[][] PROJ_SORT_LIST =
      { new String[] { "test", "demo1,demo2,demo3,demo4,demo5" } };

  /**
   * Read Main Demo File
   * 
   * @return String with main demo file
   * @throws IOException
   */
  @Override
  public String readDemoFileAsText() throws IOException {
    return DEMO_FILE;
  }

  
  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.it.utils.IDemoFileUtils#readFileAsText(java.lang.String)
   */
  @Override
  public String readFileAsText(String fname) throws IOException {
    return getFileText(fname);
  }

  private static String getFileText(String fname) {
    String res = "";
    String[] t = fname.split("\\.");

    res = t[0].substring(0, 1).toUpperCase() +
        t[0].substring(1, t[0].length() - 1);

    return res + t[t.length - 1].substring(0, 1).toUpperCase() +
        t[t.length - 1].substring(1, t[t.length - 1].length() - 1) + " " +
        t[t.length - 1].substring(t[t.length - 1].length() - 1) + "\n";
  }

  /**
   * Read main demo file as input stream
   * 
   * @return InputStream with main demo file
   * @throws IOException
   */
  @Override
  public InputStream readDemoFileAsStream() throws IOException {
    return new ByteArrayInputStream(DEMO_FILE.getBytes());
  }

  /**
   * Read Demo file as input stream
   * 
   * @param fnaem
   *          File Name
   * @return InputStream with file text
   * @throws IOException
   */
  @Override
  public InputStream readDemoFileAsStream(String fname) {
    return new ByteArrayInputStream(getFileText(fname).getBytes());
  }

  /**
   * Copy Demo File into external file
   * 
   * @param name
   *          Input File Name
   * @param fname
   *          Output File Name
   * @throws IOException
   */
  @Override
  public void copyDemoFileToFile(String name, String fname) throws IOException {
    BaseUtils.copyToFile(getFileText(name), fname);
  }

  @Override
  public String[][] getDemoSet() {
    // Top level maps
    return DEMO_FILES;
  }

  @Override
  public String[][] getProjList() {
    return PROJ_SORT_LIST;
  }

  /**
   * Return 2 common files used for test
   * 
   * @return
   */
  @Override
  public String[][] getTestSet() {
    // Top level maps
    return TEST_FILES;
  }

  @Override
  public String[][] getJsonTestSet() {
    int len = TEST_FILES.length;
    String[][] res = new String[len][2];
    for (int i = 0; i < len; i++) {
      res[i][0] = TEST_FILES[i][0];
      res[i][1] = getJsonStr(TEST_FILES[i][1]);
    }

    return res;
  }

  @Override
  public String[][] getJsonDemoSet() {
    int len = DEMO_FILES.length;
    String[][] res = new String[len][2];
    for (int i = 0; i < len; i++) {
      res[i][0] = DEMO_FILES[i][0];
      res[i][1] = getJsonStr(DEMO_FILES[i][1]);
    }

    return res;
  }

  private String getJsonStr(String str) {
    // TODO Update common function
    // Utils.escJsonStr(TEST_FILES[i][1]) +
    return str.replaceAll("\n", "\\\\n");
  }
  
  @Override
  public void copyDemoProj(String dest) throws IOException {
    copyDemoProj(dest, "");
  }

  /**
   * Copy all demo maps into physical directory
   * 
   * @param base
   *          Top root directory to copy files
   * @param dir
   *          Sub-directory from top resource tree
   * @throws IOException
   * @throws URISyntaxException
   */
  @Override
  public void copyDemoProj(String dest, String dir) throws IOException {
    String sdir = StringUtils.isEmpty(dir) ? "" : File.separator + dir;

    GenericTestUtils.copyDirectory(new File(getProjSrcDir() + sdir),
        new File(dest + sdir));
  }

  @Override
  public String[] getDemoSrcSet() {
    return new String[] { TEST_FILES[0][1], TEST_FILES[1][1] };
  }

  @Override
  public String readDemoFileRespAsText(boolean minified) throws IOException {
    return TestPrjMgrBaseConstants.ENTYTY_PREFIX + "\"" +
        Utils.escJsonStr(DEMO_FILE) + "\"";
  }

  @Override
  public int readDemoFileSize() throws IOException {
    return DEMO_FILE.length() + 4;
  }
}
