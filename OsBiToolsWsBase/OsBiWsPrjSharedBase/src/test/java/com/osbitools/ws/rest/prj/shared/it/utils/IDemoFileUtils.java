/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2015-04-15
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.it.utils;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * Interface for utilities read demo files
 * 
 */
public interface IDemoFileUtils {

  /**
   * Copy all demo files from source folder into physical directory
   * 
   * @param dest
   *          Destination folder
   * @throws IOException
   */
  void copyDemoProj(String dest) throws IOException;

  /**
   * Partial copy demo files from source folder sub-directory dir into physical
   * directory dir
   * 
   * @param dest
   *          Destination directory to copy files
   * @param dir
   *          Sub-directory from top resource tree
   * @throws IOException
   * @throws URISyntaxException
   */
  void copyDemoProj(String dest, String dir) throws IOException;

  /**
   * Read Main Demo File
   * 
   * @return String with main demo file
   * @throws IOException
   */
  String readDemoFileAsText() throws IOException;

  /**
   * Read Response from Main Demo File upload
   * 
   * @return String with response on upload main demo file
   * @throws IOException
   */
  String readDemoFileRespAsText(boolean minified) throws IOException;

  /**
   * Read Size of Main Demo File upload
   * 
   * @return Size of main demo file
   * @throws IOException
   */
  int readDemoFileSize() throws IOException;

  /**
   * Read file by name
   * 
   * @param fname
   *          File Name
   * @return String with demo file
   * @throws IOException
   */
  String readFileAsText(String fname) throws IOException;

  /**
   * Read main demo file as input stream
   * 
   * @return InputStream with main demo file
   * @throws IOException
   */
  InputStream readDemoFileAsStream() throws IOException;

  /**
   * Read Demo file as input stream
   * 
   * @param fnaem
   *          File Name
   * @return InputStream with file text
   * @throws IOException
   */
  InputStream readDemoFileAsStream(String fname) throws IOException;

  /**
   * Copy Demo File into external file
   * 
   * @param name
   *          Input File Name
   * @param fname
   *          Output File Name
   * @throws IOException
   */
  void copyDemoFileToFile(String name, String fname) throws IOException;

  /**
   * Get 2 dimensional array with demo file(s) and corresponded text
   * 
   * @return 2 dimensional array with demo file(s) and corresponded text
   */
  String[][] getDemoSet();

  /**
   * Get list of all files included into demo project
   * 
   * @return list of files included into demo project
   */
  String[][] getProjList();

  /**
   * Get sorted list of all demo project
   * 
   * @return sorted list of all demo project
   */
  String getProjListSorted();

  /**
   * Get sorted list of all demo project with all entities
   * 
   * @return sorted list of all demo project with all entities
   */
  String getProjEntitiesListSorted();

  /**
   * Return 2 common files used for test from top project directory
   * 
   * @return
   */
  String[][] getTestSet();

  /**
   * Return partial JSON string that su
   * 
   * @param minified
   *          Minified flag for json
   * @return json string with file content
   */
  String[][] getJsonTestSet();

  /**
   * Return Full JSON string for 2 common files 
   *            used for test from top project directory
   * 
   * @param minified
   *          Minified flag for json
   * @return json string with file content
   */
  String[][] getJsonTestSetFull();
  
  /**
   * Return partial JSON ("entity only) for all demo files 
   *                        used for test from top project directory
   * 
   * @param minified Minified flag for json
   * @return json string with file content
   */
  String[][] getJsonDemoSet();

  /**
   * Return Full JSON for all demo files 
   *                      used for test from top project directory
   * 
   * @param minified Minified flag for json
   * @return json string with file content
   */
  String[][] getJsonDemoSetFull();
  
  /**
   * Get source directory with project test & demo files
   * 
   * @return
   */
  String getProjSrcDir();

  /**
   * Get working directory with project test & demo files
   * 
   * @return
   */
  String getProjWorkDir();

  /**
   * Return list of demo files source used for test
   * 
   * @return
   */
  String[] getDemoSrcSet() throws IOException;

  /**
   * Get list of system components available for use
   * 
   * @return list of system components
   */
  String getCompList();

  /**
   * Get base file extension as xml, html etc
   * 
   * @return Base File extension
   */
  String getBaseExt();

  /**
   * Get list of files in each resource subdirectory
   * 
   * @param rname
   *          Resource Name
   * @return List of files in each resource subdirectory
   */
  String getExtFileList(String rname);

  String[][] getExtFileTestFiles(String rname);

  TestFileItem[] getExtFileTestSet(String rname, String fileExt);

  HashMap<String, String>[] getExtFileTestSetParams(String rname);

  String[] getExtList(String rname);

  String[] getResDirList();

  byte[] getDemoExFileAsBytes(String rname, String ext);

  String getDemoExFileUploadResp(String rname, String ext);

  String getExtFileListJson(String rname);

  /**
   * Get prefix JSON character, for example {
   * 
   * @return prefix JSON character
   */
  String getJsonPrefix();

  /**
   * Get suffix JSON character, for example }
   * 
   * @return suffix JSON character
   */
  String getJsonSuffix();
  
  /**
   * Get Mime Type for uploaded files
   * 
   * @param fname Name of uploaded file
   * 
   * @return MIME type
   */
  String getMimeType(String fname);
}
