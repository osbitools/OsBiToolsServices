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

package com.osbitools.ws.base;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Class with static Generic File utilities
 * 
 */
public class BaseUtils {

  // End Of File
  static final int EOF = -1;

  // Buffer size for I/O operations
  static final int DEFAULT_BUFFER_SIZE = 1024 * 8;

  /**
   * Retrieve file extension
   * 
   * @param name File Name
   * @return File Extension
   * 
   * @throws IOException
   */
  public static String getFileExt(String name) throws Exception {
    int len = name.length();

    if (len < 3)
      throw new Exception("Invalid name \\\"" + name + "\\\"");

    int pos = name.lastIndexOf(".");
    if (pos == -1)
      throw new Exception("Extension not found in name \\\"" + name + "\\\"");

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
   * @throws IOException
   */
  public static String checkFileExt(String name, String ext) throws Exception {
    int len = name.length();
    String fext = getFileExt(name);

    if (!fext.equals(ext))
      throw new Exception("Expected " + ext + " extension but found \\\"" + fext + "\\\"");

    return name.substring(0, len - ext.length() - 1);
  }

  /**
   * Check if file extension supported
   * 
   * @param name File name
   * @param extl List of supported extensions
   * @return File name without extension
   * 
   * @throws IOException
   */
  public static String checkFileExt(String name, String ext, HashSet<String> extl)
      throws Exception {
    int len = name.length();

    if (!extl.contains(ext)) {
      String sl = "";
      for (String s : extl)
        sl += "," + s;

      throw new Exception(
          "Expected " + (extl.size() > 1 ? "one of " + sl.substring(1) : ext) + " extension" +
              (extl.size() > 1 ? "s" : "") + " but found \\\"" + ext + "\\\"");
    }

    return name.substring(0, len - ext.length() - 1);
  }

  /**
   * Check system path and if ~ present replace it with "user.home" system parameter
   * 
   * @param path Path to check
   * @return Path with first ~ character replaced
   */
  public static String checkSystemPath(String path) {
    return path.substring(0, 1).equals("~")
        // Replace ~ with user.home
        ? System.getProperty("user.home") + path.substring(1)
        // Use as is
        : path;
  }

  /**
   * Saving file
   * 
   * @param f Destination File
   * @param in Input Stream
   * 
   * @throws IOException
   */
  public static void saveFile(File f, InputStream in) throws IOException {
    OutputStream out = null;
    try {
      try {
        out = new FileOutputStream(f);
      } catch (FileNotFoundException e) {
        throw new IOException("Error creating file \"" + f.getAbsolutePath() + "\"", e);
      }

      try {
        copy(in, out);
      } catch (IOException e) {
        throw new IOException("Error creating file \"" + f.getAbsolutePath() + "\"", e);
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
   * @throws IOException
   */
  public static void saveFile(File f, String text) throws IOException {
    Writer out = null;
    try {
      try {
        out = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
      } catch (UnsupportedEncodingException e) {
        throw new IOException(
            "Error creating file \"" + f.getAbsolutePath() + "\" with UTF-8 encoding", e);
      } catch (FileNotFoundException e) {
        throw new IOException("Error creating file \"" + f.getAbsolutePath() + "\"", e);
      }

      try {
        out.write(text);
      } catch (IOException e) {
        throw new IOException("Error creating file \"" + f.getAbsolutePath() + "\"", e);
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
   * Strip TAB and CR characters
   * 
   * @param msg Initial message
   * 
   * @return Stripped message
   */
  public static String stripCRT(String msg) {
    return msg.replaceAll("[\\t||\\n]", "");
  }

  /**
   * Read file into byte array
   * 
   * @param f File handle
   * @return byte array
   * 
   * @throws IOException
   */
  public static byte[] readFile(File f) throws IOException {
    return Files.readAllBytes(Paths.get(f.toURI()));
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
   * Copy input stream into output stream
   * 
   * @param input Input Stream
   * @param output Output Stream
   * 
   * @throws IOException
   */
  public static void copy(InputStream input, OutputStream output) throws IOException {
    byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];

    int n = 0;
    while (EOF != (n = input.read(buffer)))
      output.write(buffer, 0, n);
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
   * Copy source file to destination
   * 
   * @param src Source file
   * @param dst Destination file
   * 
   * @throws IOException
   */
  public static void copy(File src, File dst) throws IOException {
    FileInputStream fis = new FileInputStream(src);
    FileOutputStream fos = new FileOutputStream(dst);

    copy(fis, fos);

    fis.close();
    fos.close();
  }

  /**
   * Append text string to existing file. Throws exception if file doesn't exists
   * 
   * @param fname File name
   * @param text Text to append
   * 
   * @throws IOException
   */
  public static void appendToFile(String fname, String text) throws IOException {
    File f = new File(fname);
    if (!f.exists())
      throw new FileNotFoundException("File [" + fname + "] not found.");
    
    FileOutputStream out = new FileOutputStream(f, true);
    copyToFile(text, out);
  }
  
  /**
   * Copy input stream into the destination file
   * 
   * @param in Input Stream
   * @param fname File Name
   * 
   * @throws IOException
   */
  public static void copyToFile(InputStream in, String fname) throws IOException {
    FileOutputStream out = new FileOutputStream(fname);
    copyToFile(in, out);
  }

  /**
   * Copy input stream into the destination file
   * 
   * @param in Input Stream
   * @param f Output File
   * 
   * @throws IOException
   */
  public static void copyToFile(InputStream in, File f) throws IOException {
    FileOutputStream out = new FileOutputStream(f);
    copyToFile(in, out);
    out.close();
  }

  /**
   * Copy Input Stream into File Output Stream
   * 
   * @param in Input Stream
   * @param out File Output Stream
   * 
   * @throws IOException
   */
  public static void copyToFile(InputStream in, FileOutputStream out) throws IOException {
    copy(in, out);
    out.close();
  }

  /**
   * Copy input string into destination file name
   * 
   * @param s Input String
   * @param fname Destination File Name
   * 
   * @throws IOException
   */
  public static void copyToFile(String s, String fname) throws IOException {
    FileOutputStream out = new FileOutputStream(fname);
    copyToFile(s, out);
  }

  /**
   * Copy input string into destination file
   * 
   * @param s Input String
   * @param f Destination File
   * 
   * @throws IOException
   */
  public static void copyToFile(String s, File f) throws IOException {
    FileOutputStream out = new FileOutputStream(f);
    copyToFile(s, out);
  }

  /**
   * Copy input string into destination file output stream
   * 
   * @param s Input String
   * @param out Destination File Output Stream
   * 
   * @throws IOException
   */
  public static void copyToFile(String s, FileOutputStream out) throws IOException {
    out.write(s.getBytes());
    out.close();
  }

  /**
   * Copy file from source to destination
   * 
   * @param from File Name to copy from
   * @param to File Name to copy into
   * @return True if copied successful and False if not
   * 
   * @throws IOException
   */
  public static boolean copyFile(String from, String to) throws IOException {
    FileInputStream in = null;
    FileOutputStream out = null;

    // Add bad file to sp directory
    try {
      in = new FileInputStream(from);
      FileChannel channel = in.getChannel();

      out = new FileOutputStream(to + ".tmp");

      out.getChannel().transferFrom(channel, 0, channel.size());
      channel.close();

      out.close();
      out = null;

      // Now rename temp file to avoid collisions
      return new File(to + ".tmp").renameTo(new File(to));
    } finally {
      if (in != null)
        in.close();
      if (out != null)
        out.close();
    }
  }

  /**
   * Copy source directory to destination
   * 
   * @param src Source directory
   * @param dst Destination directory
   * 
   * @throws IOException
   */
  public static void copyDirToDir(File src, File dst) throws IOException {
    if (!src.exists())
      throw new IOException("Source [" + src + "] doesn't exists.");
    if (!src.isDirectory())
      throw new IOException("Source [" + src + "] exists but is not directory.");

    if (dst.exists()) {
      if (!dst.isDirectory())
        throw new IOException("Destination [" + dst + "] exists but is not a directory");
    } else {
      if (!dst.mkdirs())
        throw new IOException("Failed create destination directory [" + dst + "]");
    }

    if (!dst.canWrite())
      throw new IOException("Destination '" + dst + "' is read only");

    for (final File fsrc : src.listFiles()) {
      final File fdst = new File(dst, fsrc.getName());
      if (fsrc.isDirectory())
        copyDirToDir(fsrc, fdst);
      else
        copy(fsrc, fdst);
    }
  }

  /**
   * Check if directory exists and create if not
   * 
   * @param dir directory name
   * @return True if directory existed and False if not and was created
   * 
   * @throws IOException
   */
  public static boolean checkDirectory(String dir) throws IOException {
    File d = new File(dir);
    Boolean res = d.exists();
    if (!(res || d.mkdirs())) {
      res = null;
      throw new IOException("Failed create directory '" + d.getAbsolutePath() + "");
    }
    return res;
  }

  /**
   * Check if file can be renamed
   * 
   * @param oldName Old File Name
   * @param newName New File Name
   * @param of Old File
   * 
   * @throws IOException
   */
  public static void renameFileCheck(String oldName, String newName, File of)
      throws IOException {
    // Quick check
    if (oldName.equals(newName))
      throw new IOException(
          "Can not rename file \\\"" + of.getAbsolutePath() + "\\\" to itself");
  }

  /**
   * Rename File
   * 
   * @param base Base Directory
   * @param oldName Old File Name
   * @param newName New File Name
   * @param sdir Sub Directory
   * 
   * @throws IOException
   */
  public static void renameFile(String base, File oldName, File newName, String sdir)
      throws IOException {
    if (!oldName.renameTo(newName))
      throw new IOException(
          "Can not rename " + oldName.getAbsolutePath() + " -> " + newName.getAbsolutePath());
  }

  /**
   * Delete file
   * 
   * @param f File
   * 
   * @throws IOException
   */
  public static void deleteFile(File f) throws IOException {
    // 1. Delete directory
    if (!f.delete())
      throw new IOException("Unable deleting file \\\"" + f.getAbsolutePath() + "\\\"");
  }

  /**
   * Recursively delete directory content and directory itself
   * 
   * @param f File
   * 
   * @throws IOException
   */
  public static void delDirRecurse(File f) throws IOException {
    delDirRecurse(f, true);
  }

  /**
   * Recursively delete directory
   * 
   * @param f File
   * @param self Delete itself
   * 
   * @throws IOException
   */
  public static void delDirRecurse(File f, boolean self) throws IOException {
    File[] dlist = f.listFiles();
    if (dlist != null) {
      for (File d : dlist)
        if (d.isDirectory())
          delDirRecurse(d, true);
        else if (!d.delete())
          throw new IOException("Unable delete '" + d.getAbsolutePath() + "'");
    }

    if (self && !f.delete())
      throw new IOException("Unable delete '" + f.getAbsolutePath() + "'");
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

  /******************* GENERIC UTILITIES ********************************/
  /**
   * Check string for non-empty conditions
   * 
   * @param s Input String
   * 
   * @return True if string is null or equals "" 
   */
  public static boolean isEmpty(String s) {
    return ((s == null) || s.equals(""));
  }

  /**
   * Check if string array is empty
   * 
   * @param arr Input Array of String
   * 
   * @return True if array is null or size equals 0
   */
  public static boolean isEmpty(String[] arr) {
    return ((arr == null) || (arr.length == 0));
  }

  /**
   * Check if HashMap is empty
   * 
   * @param arr HaspMap
   * 
   * @return True if array is null or size equals 0
   */
  public static boolean isEmpty(HashMap<?, ?> arr) {
    return ((arr == null) || (arr.size() == 0));
  }

  /**
   * Check if boolean is False or null
   * 
   * @param arr Input Array of String
   * 
   * @return True if array is null or size equals 0
   */
  public static boolean isEmpty(Boolean b) {
    return ((b == null) || !b);
  }

  /**
   * Convert first character to upper case in input word
   * 
   * @param msg input word
   * @return word with first character upper case
   */
  public static String ucFirstChar(String msg) {
    char first = Character.toUpperCase(msg.charAt(0));
    return first + msg.substring(1);
  }

  /**
   * Convert first character to lower case in input word
   * 
   * @param msg input word
   * @return word with first character lower case
   */
  public static String ucLowerChar(String msg) {
    char first = Character.toLowerCase(msg.charAt(0));
    return first + msg.substring(1);
  }

  /**
   * Get Bean setter name from property name
   * 
   * @param name property name
   * @return bean setter name
   */
  public static String getBeanSetter(String name) {
    return "set" + getBeanMethod(name);
  }

  /**
   * Get Bean getter name from property name
   * 
   * @param name property name
   * @return bean setter name
   */
  public static String getBeanGetter(String name) {
    return "get" + getBeanMethod(name);
  }

  private static String getBeanMethod(String name) {
    String[] slist = name.split("[.|_]");

    String res = "";
    for (String sitem : slist)
      res += ucFirstChar(sitem);

    return res;
  }

  /**
   * Get Property name in snake_case from field name in camelCase
   * 
   * @param name field name
   * @return property name
   */
  public static String getPropertyName(String name, String delim) {
    // Split by capital letters
    StringBuilder str = new StringBuilder();
    List<String> slist = new ArrayList<String>();
    for (int i = 0; i < name.length(); i++) {
      char chr = name.charAt(i);
      if (chr == Character.toUpperCase(chr) && !(chr >= '0' && chr <= '9')) {
        slist.add(str.toString());
        str = new StringBuilder();
        str.append(Character.toLowerCase(chr));
      } else {
        str.append(chr);
      }
    }

    // Append last substring
    slist.add(str.toString());

    String res = slist.get(0);
    for (int i = 1; i < slist.size(); i++)
      res += ((i == 1) ? delim : "_") + ucLowerChar(slist.get(i));

    return res;
  }
}
