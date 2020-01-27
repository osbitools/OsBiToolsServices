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

package com.osbitools.ws.shared.common;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;

import java.nio.charset.StandardCharsets;
import com.osbitools.ws.base.BaseUtils;

/**
 * Generic utilities read resources out of jar file
 * 
 */
public class JarUtils {
  
  /**
   * Read file as a string from given resource
   * @param path Resource Path
   * @return String with file body
   * @throws IOException
   */
  public static String readJarFileAsText(String path) throws IOException {
    InputStreamReader in = new InputStreamReader(
                        readJarFileAsStream(path), StandardCharsets.UTF_8);
    StringWriter out = new StringWriter();
    
    BaseUtils.copy(in, out);
    String res = out.toString();
    
    in.close();
    out.close();
    
    return res;
  }
  
  /**
   * Read file as a Input Stream from given resource
   * @param path Resource Path
   * @return File Input Stream
   * @throws IOException
   */
  public static InputStream readJarFileAsStream(String path) 
                                                throws IOException {
    InputStream in = ClassLoader.
          getSystemClassLoader().getResourceAsStream(path);
    
    if (in == null)
      throw new IOException("Empty Resource [" + path + "]");
    
    return in;
  }
  
  /**
   * Copy directory and all sub-directories from jar file into physical directory
   * 
   * @param base Top root directory to copy files
   * @param rdir top resource directory to read files from
   * @param dir sub-directory to read files from/copy to
   * @throws IOException
   * @throws URISyntaxException
   */
  public static void copyJarDir(String base, String rdir, String dir) 
      throws IOException, URISyntaxException {
    String sdir = base + dir;
    String[] entries = readJarDirList(rdir + dir);
    for (String entry: entries) {
      if (entry.indexOf("/") == -1) {
        BaseUtils.copyToFile(readJarFileAsStream(rdir + dir + "/" + entry), 
                                                      sdir + "/" + entry);
      } else {
        (new File(sdir + entry)).mkdir();
        copyJarDir(base, rdir, dir + entry);
      }
    }
  }


  /**
   * Read files (including sub-directories) from jar directory
   * 
   * @param dir path from jar file
   * @return Array with list of entries 
   * @throws IOException
   * @throws URISyntaxException
   */
  public static String[] readJarDirList(String dir) 
                    throws IOException, URISyntaxException {
    ArrayList<String> res = new ArrayList<String>();
    URL url = ClassLoader.getSystemClassLoader().getResource(dir);
    
    if (url == null) {
      throw new IOException("Empty Dir Resource [" + dir + "]");
    } else if (url.getProtocol().equals("jar")) {
      //strip out only the JAR file
      String jpath = url.getPath().substring(5, url.getPath().indexOf("!"));
      
      JarFile jar = new JarFile(URLDecoder.decode(jpath, "UTF-8"));
      Enumeration<JarEntry> entries = jar.entries(); // gives ALL entries in jar
      while(entries.hasMoreElements()) {
        String name = entries.nextElement().getName();
        
        if (name.startsWith(dir)) { //filter according to the path
          String entry = name.substring(dir.length());
          int pos = entry.indexOf("/");
          if (pos != 0)
            continue;
          
          // Cut sub-directory path
          entry = entry.substring(pos + 1);
          
          if (!entry.equals("")) {
            // Check if it's sub-directories of given directory
            // if pos == entry.length and no other / than it's top 
            //      subdirectory name
            int pos1 = entry.indexOf("/");
            int pos2 = entry.lastIndexOf("/");
            if (pos2 == -1 || (pos2 == entry.length() - 1 && pos1 == pos2)) {
              if (pos2 == -1)
                res.add(entry);
              else
                res.add("/" + entry.substring(0, entry.length() - 1));
            }
          }
        }
      }
      
      jar.close();
    }
    
    return res.toArray(new String[res.size()]);
  }
}
