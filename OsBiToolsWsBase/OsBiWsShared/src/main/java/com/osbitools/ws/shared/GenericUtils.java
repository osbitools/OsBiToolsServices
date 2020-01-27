/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2016-27-05
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.shared;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;

import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.base.WsSrvException;

/**
 * Class with static Generic File utilities
 * 
 */
public class GenericUtils {

  // End Of File
  static final int EOF = -1;

  // Buffer size for I/O operations
  static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

  /**
   * Validate entry
   * 
   * @param base Base Directory
   * @param name Entry Name
   * @param lvl Allowed level of sub-directories
   * @param fexists Should file exist or not flag
   * @return Entity name if it passes validation
   * 
   * @throws WsSrvException
   */
  public static File validateEntry(String base, String name, int lvl,
      Boolean fexists) throws WsSrvException {
    return validateEntry(base, name, lvl, fexists, "", null);
  }

  /**
   * Validate entry
   * 
   * @param base Base Directory
   * @param name Entry Name
   * @param lvl Allowed level of sub-directories
   * @return Entity name if it passes validation
   * 
   * @throws WsSrvException
   */
  public static File validateEntry(String base, String name, int lvl)
      throws WsSrvException {
    return validateEntry(base, name, lvl, null, "", null);
  }

  /**
   * Validate entry
   * 
   * @param base Base Directory
   * @param name Entry Name
   * @param lvl Allowed level of sub-directories
   * @param fexists Should file exist or not flag
   * @param suffix File suffix
   * @return Entity name if it passes validation
   * 
   * @throws WsSrvException
   */
  public static File validateEntry(String base, String name, int lvl,
      Boolean fexists, String suffix) throws WsSrvException {
    return validateEntry(base, name, lvl, fexists, suffix, null);
  }

  /**
   * Validate entry
   * 
   * @param base Base Directory
   * @param name Entry Name
   * @param lvl Allowed level of sub-directories
   * @param fexists Should file exist or not flag
   * @param suffix File suffix
   * @param sdir Sub-directory
   * @return Entity name if it passes validation
   * 
   * @throws WsSrvException
   */
  public static File validateEntry(String base, String name, int lvl,
      Boolean fexists, String suffix, String sdir) throws WsSrvException {

    File f = new File(validateEntryName(base, name, lvl, sdir) + suffix);

    // Check if directory exists or not
    if (fexists != null && f.exists() != fexists) {
      //-- 1
      //-- 2
      throw (fexists)
          ? new WsSrvException(1,
              "Entry \\\"" + f.getAbsolutePath() + "\\\" not found")
          : new WsSrvException(2,
              "Entry \\\"" + f.getAbsolutePath() + "\\\" already exists");
    }

    return f;
  }

  /**
   * Validate entry name
   * 
   * @param base Base Directory
   * @param name Entry Name
   * @return Entity name if it passes validation
   * 
   * @throws WsSrvException
   */
  public static String validateEntryName(String base, String name)
      throws WsSrvException {
    return validateEntryName(base, name, Constants.MAX_PROJ_LVL + 1, null);
  }

  /**
   * Validate entry name
   * 
   * @param base Base Directory
   * @param name Entry Name
   * @param lvl Allowed level of sub-directories
   * @return Entity name if it passes validation
   * 
   * @throws WsSrvException
   */
  public static String validateEntryName(String base, String name, int lvl)
      throws WsSrvException {
    return validateEntryName(base, name, lvl, null);
  }

  /**
   * Validating Entry Name
   * 
   * @param base Base Directory
   * @param name Entry Name
   * @param lvl Allowed level of sub-directories
   * @param sdir Sub-directory
   * @return Entity name if it passes validation
   * 
   * @throws WsSrvException
   */
  public static String validateEntryName(String base, String name, int lvl,
      String sdir) throws WsSrvException {

    // 1. Check if name correct
    String[] path = name.split("\\.");
    if (path.length > lvl)
      //-- 3
      throw new WsSrvException(3,
          "Only " + lvl + " level structure supported but found " +
              path.length + " in name \\\"" + name + "\\\"");

    String res = base;
    if (path.length > 0) {
      for (int i = 0; i < path.length; i++) {
        // Add subdirectory entry for external files
        if (i == path.length - 1 && sdir != null)
          res += File.separator + sdir;

        res += File.separator + checkName(path[i]);
      }
    } else {
      res += checkName(name);
    }

    return res;
  }

  /**
   * Check Entity or Project name against ID Pattern
   * @param name Entity or Project name
   * @return Entity or Project name if it matches pattern
   * 
   * @throws WsSrvException
   */
  private static String checkName(String name) throws WsSrvException {
    if (!Constants.ID_PATTERN.matcher(name).matches())
      //-- 4
      throw new WsSrvException(4, "Invalid name \\\"" + name + "\\\"");

    return name;
  }

  /**
   * Retrieve file extension
   * 
   * @param name File Name
   * @return File Extension
   * 
   * @throws WsSrvException
   */
  public static String getFileExt(String name) throws WsSrvException {
    int len = name.length();

    if (len < 3)
      //-- 5
      throw new WsSrvException(5, "Invalid name \\\"" + name + "\\\"");

    int pos = name.lastIndexOf(".");
    if (pos == -1)
      //-- 6
      throw new WsSrvException(6,
          "Extension not found in name \\\"" + name + "\\\"");

    return name.substring(pos + 1);
  }

  /**
   * Check file extension and if it found and same as expected than
   * return file name without extension
   * 
   * @param name File Name
   * @param ext File Extension
   * @return File name without extension
   * 
   * @throws WsSrvException
   */
  public static String checkFileExt(String name, String ext)
      throws WsSrvException {
    try {
      return BaseUtils.checkFileExt(name, ext);
    } catch (Exception e) {
      //-- 7
      throw new WsSrvException(7, e.getMessage());
    }
  }

  /**
   * Check if file extension supported
   * 
   * @param name File name
   * @param extl List of supported extensions
   * @return File name without extension
   * 
   * @throws WsSrvException
   */
  public static String checkFileExt(String name, String ext,
      HashSet<String> extl) throws WsSrvException {
    try {
      return BaseUtils.checkFileExt(name, ext, extl);
    } catch (Exception e) {
      //-- 8
      throw new WsSrvException(8, e.getMessage());
    }
  }

  /**
   * Convert web path to real path and check if file exists or not
   * 
   * @param base Base configuration directory
   * @param name Web Path Name
   * @param ext File extension
   * @return File pointer if check successful
   * 
   * @throws WsSrvException
   */
  public static File checkFile(String base, String name, String ext)
      throws WsSrvException {
    return checkFile(base, name, ext, null, true);
  }

  public static File checkFile(String base, String name, String ext,
      Boolean overwrite) throws WsSrvException {
    return checkFile(base, name, ext, null, overwrite);
  }

  /**
   * Convert web path to real path and check if file exists or not
   * 
   * @param base Base configuration directory
   * @param name Web Path Name
   * @param extl List of allowed extensions
   * @param sdir Sub-directory (if any) where look for file
   * @param overwrite True Expect overwrite file so it should exist
   * @return File pointer if check successful
   * 
   * @throws WsSrvException
   */
  public static File checkFile(String base, String name, String ext,
      String sdir, Boolean overwrite) throws WsSrvException {
    name = doCheckAddFileNameExt(name, ext);
    String dsn = checkFileExt(name, ext);

    return checkFileEx(base, dsn, ext, sdir, overwrite);
  }

  /**
   * Convert web path to real path and check if file exists or not
   * 
   * @param base Base configuration directory
   * @param name Web Path Name
   * @param extl List of allowed extensions
   * @param sdir Sub-directory (if any) where look for file
   * @param overwrite True Expect overwrite file so it should exist
   * @return  File pointer if check successful
   * 
   * @throws WsSrvException
   */
  public static File checkFile(String base, String name, HashSet<String> extl,
      String sdir, Boolean overwrite) throws WsSrvException {
    String ext = getFileExt(name);
    String dsn = checkFileExt(name, ext, extl);

    return checkFileEx(base, dsn, ext, sdir, overwrite);
  }

  /**
   * Check File Extended
   * 
   * @param base Base Directory
   * @param dsn DSN file representation i.e dir.dir.file_ma,e
   * @param ext File Extension
   * @param sdir Sub Directory
   * @param overwrite Overwrite flag
   * @return File pointer if check successful
   * 
   * @throws WsSrvException
   */
  public static File checkFileEx(String base, String dsn, String ext,
      String sdir, Boolean overwrite) throws WsSrvException {
    // 0. Get full path
    File f = validateEntry(base, dsn, Constants.MAX_PROJ_LVL + 1, overwrite,
        "." + ext, sdir);

    // 1. Check that f is a file
    if ((overwrite == null || overwrite) && f.exists() && !f.isFile())
      //-- 9
      throw new WsSrvException(9,
          "Entry \\\"" + f.getAbsolutePath() + "\\\" is not a file");

    return f;
  }

  /**
   * Check directory
   * 
   * @param base Base configuration directory
   * @param name Web Path Name
   * @return Directory name if it passes check
   * 
   * @throws WsSrvException
   */
  public static File checkDir(String base, String name) throws WsSrvException {
    // 0. Get full path for old directory
    File dir = validateEntry(base, name, Constants.MAX_PROJ_LVL, true);

    // 1. Check if Directory exists
    if (!dir.isDirectory())
      //-- 10
      throw new WsSrvException(10,
          "Entry \\\"" + dir.getAbsolutePath() + "\\\" is not a directory");

    return dir;
  }

  /**
   * Saving file
   * 
   * @param f Destination File
   * @param in Input Stream
   * 
   * @throws WsSrvException
   */
  public static void saveFile(File f, InputStream in) throws WsSrvException {
    OutputStream out = null;
    try {
      try {
        out = new FileOutputStream(f);
      } catch (FileNotFoundException e) {
        //-- 11
        throw new WsSrvException(11,
            "Error creating file \"" + f.getAbsolutePath() + "\"", e);
      }

      try {
        BaseUtils.copy(in, out);
      } catch (IOException e) {
        //-- 12
        throw new WsSrvException(12,
            "Error creating file \"" + f.getAbsolutePath() + "\"", e);
      }
    } finally {
      if (out != null)
        try {
          out.close();
        } catch (IOException e) {
          // Ignore
        }
    }
  }

  /**
   * Saving file
   * 
   * @param f Destination File
   * @param text Input String
   * 
   * @throws WsSrvException
   */
  public static void saveFile(File f, String text) throws WsSrvException {
    Writer out = null;
    try {
      try {
        out = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
      } catch (UnsupportedEncodingException e) {
        //-- 13
        throw new WsSrvException(13, "Error creating file \"" +
            f.getAbsolutePath() + "\" with UTF-8 encoding", e);
      } catch (FileNotFoundException e) {
        //-- 14
        throw new WsSrvException(14,
            "Error creating file \"" + f.getAbsolutePath() + "\"", e);
      }

      try {
        out.write(text);
      } catch (IOException e) {
        //-- 15
        throw new WsSrvException(15,
            "Error creating file \"" + f.getAbsolutePath() + "\"", e);
      }
    } finally {
      if (out != null)
        try {
          out.close();
        } catch (IOException e) {
          // Ignore
        }
    }
  }

  /**
   * Read file into byte array
   * 
   * @param f File handle
   * @return byte array
   * 
   * @throws WsSrvException
   */
  public static byte[] readFile(File f) throws WsSrvException {
    try {
      return BaseUtils.readFile(f);
    } catch (IOException e) {
    //-- 16
      throw new WsSrvException(16, e);
    }
  }

  /**
   * Read input stream into byte array
   * 
   * @param in Input Stream
   * @return byte array
   * 
   * @throws IOException
   */
  public static byte[] readInputStream(InputStream in) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    byte[] bytes = new byte[8192];
    int length;

    while ((length = in.read(bytes)) > 0)
      out.write(bytes, 0, length);

    in.close();
    out.close();

    return out.toByteArray();
  }

  /**
   * Get list of file inside resource subdirectory
   * 
   * @param base Top root directory
   * @param fileName Name of file including directory/project path
   * @param resDirName Name of resource subdirectory
   * @param extList List of file extensions to lookup inside resource subdirectory
   * @return List of files
   * 
   * @throws WsSrvException
   */
  public static String getResDirExtList(String base, String fileName,
      String resDirName, FilenameFilter filter) throws WsSrvException {
    String res = "";

    // Remove mask and get directory name
    String dname = fileName.substring(0, fileName.length() - 2);

    if (dname.equals(""))
      //-- 17
      throw new WsSrvException(17, "Invalid entry '" + fileName + "'");

    // 0. Get full path
    File dir = checkDir(base, dname);

    File extd = new File(dir.getAbsolutePath() + File.separator + resDirName);

    if (extd.exists()) {
      String[] flist = extd.list(filter);
      Arrays.sort(flist);
      for (String f : flist)
        res += "," + f;
    }

    return res.equals("") ? "" : res.substring(1);
  }

  /**
   * Get directory of file
   * 
   * @param base Base path
   * @param name File Name
   * @return Directory Name
   * 
   * @throws WsSrvException
   */
  public static File getFileDir(String base, String name)
      throws WsSrvException {
    // Strip extension
    String fname = name.substring(0, name.length() - 4);

    int pos = fname.lastIndexOf(".");
    if (pos < 0)
      return new File(base);
    else
      return checkDir(base, fname.substring(0, pos));
  }

  /**
   * Copy input into output
   * 
   * @param input Input Reader
   * @param output Output Writer
   * 
   * @throws IOException
   */
  public static void copy(Reader input, Writer output) throws IOException {
    char[] buffer = new char[DEFAULT_BUFFER_SIZE];

    int n = 0;
    while (EOF != (n = input.read(buffer)))
      output.write(buffer, 0, n);
  }

  /**
   * Rename File
   * 
   * @param base Base Directory
   * @param oldName Old File Name
   * @param newName New File Name
   * @param ext File Extension
   * @throws WsSrvException
   */
  public static void renameFile(String base, String oldName, String newName,
      String ext) throws WsSrvException {
    renameFile(base, oldName, newName, ext, null);
  }

  /**
   * Rename File
   * 
   * @param base Base Directory
   * @param oldName Old File Name
   * @param newName New File Name
   * @param extl Set of file extensions
   * @param sdir Sub Directory
   * 
   * @throws WsSrvException
   */
  public static void renameFile(String base, String oldName, String newName,
      HashSet<String> extl, String sdir) throws WsSrvException {
    // Check old file
    File fOld = checkFile(base, oldName, extl, sdir, true);
    renameFileCheck(oldName, newName, fOld);
    renameFile(base, fOld, checkFile(base, newName, extl, sdir, false), sdir);
  }

  /**
   * Check if file can be renamed
   * 
   * @param oldName Old File Name
   * @param newName New File Name
   * @param of Old File
   * 
   * @throws WsSrvException
   */
  public static void renameFileCheck(String oldName, String newName, File of)
      throws WsSrvException {
    try {
      BaseUtils.renameFileCheck(oldName, newName, of);
    } catch (IOException e) {
    //-- 18
      throw new WsSrvException(18, e.getMessage());
    }
  }

  /**
   * Rename File
   * 
   * @param base Base Directory
   * @param oldName Old File Name
   * @param newName New File Name
   * @param sdir Sub Directory
   * 
   * @throws WsSrvException
   */
  public static void renameFile(String base, File oldName, File newName,
      String sdir) throws WsSrvException {
    try {
      BaseUtils.renameFile(base, oldName, newName, sdir);
    } catch (IOException e) {
    //-- 19
      throw new WsSrvException(19, e.getMessage());
    }
  }

  /**
   * Rename File
   * 
   * @param base Base Directory
   * @param oldName Old File Name
   * @param newName New File Name
   * @param ext File Extension
   * @param sdir Sub Directory
   * 
   * @throws WsSrvException
   */
  public static void renameFile(String base, String oldName, String newName,
      String ext, String sdir) throws WsSrvException {
    File fOld = checkFile(base, oldName, ext, sdir, true);
    renameFileCheck(oldName, newName, fOld);
    renameFile(base, fOld, checkFile(base, newName, ext, sdir, false), sdir);
  }

  /**
   * Delete file
   * 
   * @param base Base Directory
   * @param name File Name
   * @param ext File Extension
   * 
   * @throws WsSrvException
   */
  public static void deleteFile(String base, String name, String ext)
      throws WsSrvException {
    deleteFile(base, name, ext, null);
  }

  /**
   * Delete file
   * 
   * @param base Base Directory
   * @param name File Name
   * @param extl Set of File Extensions
   * @param sdir Sub Directory
   * 
   * @throws WsSrvException
   */
  public static void deleteFile(String base, String name, HashSet<String> extl,
      String sdir) throws WsSrvException {
    // Get full path
    File f = checkFile(base, name, extl, sdir, true);

    // 1. Delete file
    deleteFile(f);
  }

  /**
   * Delete file
   * 
   * @param base Base Directory
   * @param name File Name
   * @param extl File Extensions
   * @param sdir Sub Directory
   * 
   * @throws WsSrvException
   */
  public static void deleteFile(String base, String name, String ext,
      String sdir) throws WsSrvException {
    // 0. Get full path
    File f = checkFile(base, name, ext, sdir, true);

    // 1. Delete file
    deleteFile(f);
  }

  /**
   * Delete file
   * 
   * @param f File
   * 
   * @throws WsSrvException
   */
  public static void deleteFile(File f) throws WsSrvException {
    try {
      BaseUtils.deleteFile(f);
    } catch (IOException e) {
    //-- 20
      throw new WsSrvException(20, e.getMessage());
    }
  }

  
  /**
   * Check if file already has extension and add it if not
   * 
   * @param name File Name
   * @param ext Base Extension
   * @return
   */
  private static String doCheckAddFileNameExt(String name, String ext) {
    if (!hasExt(name, ext))
      name += "." + ext;
    
    return name;
  }
  
  /**
   * Check if file has expected extension
   * 
   * @param name File Name
   * @param ext Expected Extension
   * 
   * @return True if extension found and equal expected
   */
  public static boolean hasExt(String name, String ext) {
    int pos = name.lastIndexOf(".");
    return (pos != -1 && name.substring(pos + 1).equals(ext));
  }
  
}
