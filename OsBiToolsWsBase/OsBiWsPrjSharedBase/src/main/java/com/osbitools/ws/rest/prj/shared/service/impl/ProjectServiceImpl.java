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

package com.osbitools.ws.rest.prj.shared.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.rest.prj.shared.service.ProjectService;
import com.osbitools.ws.shared.*;

/**
 * Class with static Project utilities
 *    
 */

@Service
public class ProjectServiceImpl extends AbstractBaseService
    implements ProjectService {

  @Override
  public String createProject(String name) throws WsSrvException {
    getReqLog().debug("Creating project '" + name + "'.");

    // 0. Get full path
    File dir = GenericUtils.validateEntry(getWsCfg().getBaseDir(), name,
        Constants.MAX_PROJ_LVL, false);

    // 1. Create directory
    if (!dir.mkdir())
      //-- 204
      throw new WsSrvException(204,
          "Error creating directory \\\"" + dir.getAbsolutePath() + "\\\"");

    // Create list of sub-directories
    for (String sname : getWsCfg().getSubDirExtList().keySet()) {
      File sdir = new File(dir, sname);
      if (!sdir.mkdir())
        //-- 209
        throw new WsSrvException(209,
            "Error creating project sub-directory \\\"" +
                sdir.getAbsolutePath() + "\\\"");
    }

    getReqLog().debug("Project '" + name + "' successfully created.");

    return dir.getAbsolutePath();
  }

  @Override
  public String[] getAllProjects() {
    getReqLog().debug("Retrieveing All Project.");
    String[] res = getAllProjectList(getWsCfg().getBaseDir());
    getReqLog().debug("All Project successfully retrieved.");

    return res;
  }

  @Override
  public String[] getAllProjectsEntities() throws WsSrvException {
    getReqLog().debug("Retrieveing All Project Entities.");
    String[] dlist = getAllProjectList(getWsCfg().getBaseDir());

    int ext_len = getWsCfg().getBaseExt().length();
    ArrayList<String> res = new ArrayList<>();
    for (String dname : dlist)
      for (String fname : getProjectList(getWsCfg().getBaseDir(), dname,
          getWsCfg().getBaseExt()))
        res.add(dname + "." + fname.substring(0, fname.length() - ext_len - 1));
    getReqLog().debug("All Project Entities successfully retrieved.");

    return res.toArray(new String[res.size()]);
  }

  @Override
  public String[] getProjectEntities(String name) throws WsSrvException {
    int ext_len = getWsCfg().getBaseExt().length();
    ArrayList<String> res = new ArrayList<>();
    for (String fname : getProjectList(getWsCfg().getBaseDir(), name,
        getWsCfg().getBaseExt()))
      res.add(fname.substring(0, fname.length() - ext_len - 1));

    return res.toArray(new String[res.size()]);
  }

  @Override
  public String renameProject(String oldName, String newName)
      throws WsSrvException {
    // 0. Get full path for old directory
    File dir1 = GenericUtils.checkDir(getWsCfg().getBaseDir(), oldName);

    // 1. Get full path for new directory
    File dir2 = GenericUtils.validateEntry(getWsCfg().getBaseDir(), newName,
        Constants.MAX_PROJ_LVL, false);

    // 2. Rename old directory
    if (!dir1.renameTo(dir2))
      //-- 205
      throw new WsSrvException(205, "Unable rename directory \\\"" +
          dir1.getAbsolutePath() + "\\\" is a file");

    return dir1.getAbsolutePath();
  }

  @Override
  public void deleteProject(String name) throws WsSrvException {
    // 0. Get full path
    File dir = GenericUtils.validateEntry(getWsCfg().getBaseDir(), name,
        Constants.MAX_PROJ_LVL, true);

    // 1. Check that directory
    if (!dir.isDirectory())
      //-- 206
      throw new WsSrvException(206,
          "Entry \\\"" + dir.getAbsolutePath() + "\\\" is not directory");

    // 2. Recursively delete directory
    delDir(dir);

  }

  private void delDir(File dir) throws WsSrvException {
    // 1. Delete all included entries
    File[] dlist = dir.listFiles();
    for (File d : dlist) {
      if (d.isDirectory()) {
        delDir(d);
      } else {
        if (!d.delete())
          //-- 207
          throw new WsSrvException(207,
              "Unable delete file '" + d.getAbsolutePath() + "'");
      }
    }

    // 2. Delete directory itself
    if (!dir.delete())
      //-- 208
      throw new WsSrvException(208,
          "Unable delete directory \\\"" + dir.getAbsolutePath() + "\\\"");
  }

  private String[] getAllProjectList(String base) {
    File dir = new File(getWsCfg().getBaseDir());
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

  private String[] getProjectList(String base, String name, String ext)
      throws WsSrvException {
    // 0. Get full path
    File dir = GenericUtils.checkDir(getWsCfg().getBaseDir(), name);

    // 1. Finally Read Directory Content
    String[] dlist = dir.list(new ExtFileFilter(ext));

    if (dlist == null || dlist.length == 0)
      return dlist == null ? new String[] {} : dlist;

    // Sort result
    Arrays.sort(dlist);

    return dlist;
  }

  /*
  public static Git createGitRepo(File dir) throws Exception {
    Repository repo = FileRepositoryBuilder.create(dir);
    repo.create(false);
  
    Git git = new Git(repo);
  
    // Commit first revision
    git.commit().setMessage("Repository created").call();
  
    return git;
  }
  */
}
