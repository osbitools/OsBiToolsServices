/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2018-01-01
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared.common;

import java.io.File;
import java.io.IOException;

import org.springframework.util.StringUtils;

import com.osbitools.ws.base.BaseUtils;

/**
 * Generic Test Utilities
 * 
 */
public class GenericTestUtils {

  /**
   * Copy source directory into the destination directory recursively
   * @param src
   * @param dst
   * @throws IOException 
   */
  public static void copyDirectory(File src, File dst) throws IOException {
    if (StringUtils.isEmpty(src))
      throw new NullPointerException(
          "Source Directory cannot be null or empty");

    if (src.exists()) {
      if (!src.isDirectory())
        throw new IllegalArgumentException(
            "Source file '" + src + "' exists but it is not a directory");
    } else if (!src.mkdirs()) {
      throw new IOException("Unable create Source Directory '" + src + "'");
    }
    if (StringUtils.isEmpty(dst))
      throw new NullPointerException(
          "Destination Directory cannot be null or empty");

    if (dst.exists() && dst.isDirectory() == false)
      throw new IllegalArgumentException(
          "Destination file '" + dst + "' but it is not a directory");

    // Get source listing
    for (String fname : src.list()) {
      // Check if destination file or folder
      String sname = src.getAbsolutePath() + File.separator + fname;
      String dname = dst.getAbsolutePath() + File.separator + fname;

      if (new File(sname).isDirectory()) {
        //Check && Create destination directory
        File df = new File(dname);

        if (!df.exists()) {
          if (!df.mkdir())
            throw new IOException(
                "Unable create destination sub-directory '" + dname + "'");
        } else if (!df.isDirectory()) {
          // Delete dest file and create directory
          if (!df.delete())
            throw new IOException("Unable delete destination file '" + dname +
                "' that is the same as destination directory name");

          if (!df.mkdir())
            throw new IOException(
                "Unable create destination sub-directory '" + dname + "'");
        }

        // Copy sub-directory recursively
        copyDirectory(new File(sname), df);
      } else {
        // Simply copy
        BaseUtils.copyFile(sname, dname);
      }
    }
  }
  
  public static void sleep(int msec) {
    try {
      Thread.sleep(msec);
    } catch (InterruptedException e) {
      // Ignore
    }
  }
}
