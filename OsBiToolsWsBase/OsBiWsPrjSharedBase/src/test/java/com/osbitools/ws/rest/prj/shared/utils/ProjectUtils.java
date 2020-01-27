/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-11-17
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.prj.shared.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.*;

/**
 * Class with static Project utilities
 * 
 */

public class ProjectUtils extends GenericUtils {

  public static String getAllProjects(String base) {

    String[] dlist = getAllProjectList(base);

    String res = "";
    for (String dname : dlist)
      res += "," + dname;

    return res.equals("") ? "" : res.substring(1);
  }

  public static String getAllProjectsEntities(String base, String ext)
      throws WsSrvException {

    String[] dlist = getAllProjectList(base);

    String res = "";
    for (String dname : dlist)
      for (String fname : getProjectList(base, dname, ext))
        res += "," + dname + "." +
            fname.substring(0, fname.length() - ext.length() - 1);

    return res.equals("") ? "" : res.substring(1);
  }

  public static String getProject(String base, String name, String ext)
      throws WsSrvException {
    // 1. Finally Read Directory Content
    String[] dlist = getProjectList(base, name, ext);

    String res = "";
    for (String fname : dlist)
      res += "," + fname.substring(0, fname.length() - ext.length() - 1);

    return res.equals("") ? "" : res.substring(1);
  }

  public static String createProject(String base, String name)
      throws WsSrvException {
    // 0. Get full path
    File dir = validateEntry(base, name, Constants.MAX_PROJ_LVL, false);

    // 1. Create directory
    if (!dir.mkdir())
      throw new WsSrvException(204,
          "Error creating directory \\\"" + dir.getAbsolutePath() + "\\\"");

    return dir.getAbsolutePath();
  }

  public static String renameProject(String base, String oldName,
      String newName) throws WsSrvException {

    // 0. Get full path for old directory
    File dir1 = checkDir(base, oldName);

    // 1. Get full path for new directory
    File dir2 = validateEntry(base, newName, Constants.MAX_PROJ_LVL, false);

    // 2. Rename old directory
    if (!dir1.renameTo(dir2))
      //-- 207
      throw new WsSrvException(207, "Unable rename directory \\\"" +
          dir1.getAbsolutePath() + "\\\" is a file");

    return dir1.getAbsolutePath();
  }

  public static void delProject(String base, String name)
      throws WsSrvException {

    // 0. Get full path
    File dir = validateEntry(base, name, Constants.MAX_PROJ_LVL, true);

    // 1. Check that directory
    if (!dir.isDirectory())
      //-- 205
      throw new WsSrvException(205,
          "Entry \\\"" + dir.getAbsolutePath() + "\\\" is not directory");

    // 2. Recursively delete directory
    delDir(dir);
  }

  public static void delDir(File dir) throws WsSrvException {
    // 1. Delete all included entries
    File[] dlist = dir.listFiles();
    for (File d : dlist) {
      if (d.isDirectory()) {
        delDir(d);
      } else {
        if (!d.delete())
          throw new WsSrvException(222,
              "Unable delete file '" + d.getAbsolutePath() + "'");
      }
    }

    // 2. Delete directory itself
    if (!dir.delete())
      //-- 206
      throw new WsSrvException(206,
          "Unable delete directory \\\"" + dir.getAbsolutePath() + "\\\"");
  }

  private static String[] getAllProjectList(String base) {
    File dir = new File(base);
    File[] dlist = dir.listFiles();
    // List with directories only
    ArrayList<String> flist = new ArrayList<String>();

    if (dlist == null || dlist.length == 0)
      return new String[] {};

    for (File ftemp : dlist) {
      // Check if directory
      if (ftemp.isDirectory() && ftemp.getName().charAt(0) != 46)
        flist.add(ftemp.getName());
    }

    String[] res = flist.toArray(new String[flist.size()]);

    // Sort result
    Arrays.sort(res);

    return res;
  }

  private static String[] getProjectList(String base, String name, String ext)
      throws WsSrvException {
    // 0. Get full path
    File dir = checkDir(base, name);

    // 1. Finally Read Directory Content
    String[] dlist = dir.list(new ExtFileFilter(ext));

    if (dlist == null || dlist.length == 0)
      return dlist == null ? new String[] {} : dlist;

    // Sort result
    Arrays.sort(dlist);

    return dlist;
  }

  public static Git createGitRepo(File dir) throws Exception {
    Repository repo = FileRepositoryBuilder.create(dir);
    repo.create(false);

    Git git = new Git(repo);

    // Commit first revision
    git.commit().setMessage("Repository created").call();

    return git;
  }
}
