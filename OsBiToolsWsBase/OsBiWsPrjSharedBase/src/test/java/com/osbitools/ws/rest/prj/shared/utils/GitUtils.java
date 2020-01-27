/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2014-12-12
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.prj.shared.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.springframework.util.StringUtils;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.GenericUtils;

/**
 * Set of utilities to work with Git repository
 * 
 */

public class GitUtils extends GenericUtils {

  static String getLocalPath(String name) {
    String[] path = name.split("\\.");

    String res;
    if (path.length == 2) {
      res = name;
    } else {
      res = path[0];
      for (int i = 1; i < path.length - 1; i++)
        res += "/" + path[i];

      res += "." + path[path.length - 1];
    }

    return res;
  }

  public static synchronized String commitFile(Git git, String base,
      String name, String comment, String user, String ext)
      throws WsSrvException {
    checkFile(base, name, ext).getAbsolutePath();

    // Prepare path for git save
    String fname = getLocalPath(name);

    return commitFile(git, fname, comment, user);
  }

  public static String commitFile(Git git, String fname, String comment,
      String user) throws WsSrvException {

    AddCommand add = git.add();
    try {
      add.addFilepattern(fname).call();
    } catch (GitAPIException e) {
      throw new WsSrvException(239, e);
    }

    CommitCommand commit = git.commit();
    try {
      RevCommit rev = commit.setMessage(comment).setCommitter(user, "").call();
      return rev.getName();
    } catch (GitAPIException e) {
      throw new WsSrvException(240, e);
    }
  }

  public static ArrayList<RevCommit> getLogEx(Git git, String base, String name,
      String ext) throws WsSrvException {
    checkFile(base, name, ext).getAbsolutePath();

    // Prepare path for git save
    String fname = getLocalPath(name);

    return getLogByFileName(git, fname);
  }

  public static ArrayList<RevCommit> getLogByFileName(Git git, String fname)
      throws WsSrvException {
    Iterable<RevCommit> log;
    try {
      log = git.log().addPath(fname).call();
    } catch (GitAPIException e) {
      throw new WsSrvException(241, e);
    }

    ArrayList<RevCommit> res = new ArrayList<RevCommit>();
    for (RevCommit rev : log)
      res.add(rev);

    return res;
  }

  public static RevCommit[] getLog(Git git, String base, String name,
      String ext) throws WsSrvException {
    ArrayList<RevCommit> res = getLogEx(git, base, name, ext);
    return res.toArray(new RevCommit[res.size()]);
  }

  public static boolean hasLog(Git git, String base, String name, String ext)
      throws WsSrvException {
    return getLogEx(git, base, name, ext).size() > 0;
  }

  public static String getRevEntityJson(Git git, String base, String name,
      String rev, IEntityUtils eut, boolean minified) throws WsSrvException {

    ByteArrayInputStream in = new ByteArrayInputStream(
        getRevEntityEx(git, base, name, rev, eut, minified).toByteArray());
    return eut.getJson(in, name, minified);
  }

  public static String getRevEntity(Git git, String base, String name,
      String rev, IEntityUtils eut, boolean minified) throws WsSrvException {
    return getRevEntityEx(git, base, name, rev, eut, minified).toString();
  }

  public static ByteArrayOutputStream getRevEntityEx(Git git, String base,
      String name, String rev, IEntityUtils eut, boolean minified)
      throws WsSrvException {
    checkFile(base, name, eut.getExt());
    String fp = getLocalPath(name);

    Iterable<RevCommit> logs;

    try {
      logs = git.log().addPath(fp).call();
    } catch (GitAPIException e) {
      throw new WsSrvException(242, e);
    }

    RevCommit r = null;
    for (RevCommit rc : logs) {
      if (rc.getName().equals(rev)) {
        r = rc;
        break;
      }
    }

    if (r == null)
      throw new WsSrvException(243,
          "Revision " + rev + " not found for entry " + fp);

    // now try to find a specific file
    ByteArrayOutputStream entity = new ByteArrayOutputStream();
    TreeWalk tw = new TreeWalk(git.getRepository());

    try {
      tw.addTree(r.getTree());
      tw.setRecursive(true);
      tw.setFilter(PathFilter.create(fp));
      if (!tw.next())
        throw new WsSrvException(244,
            "Entry " + fp + " not found in revision " + rev);

      ObjectId objectId = tw.getObjectId(0);
      ObjectLoader loader = git.getRepository().open(objectId);

      // and then one can the loader to read the file
      loader.copyTo(entity);
    } catch (Exception e) {
      throw new WsSrvException(245,
          "Error retrieving revision " + rev + " for entry " + fp);
    } finally {
      tw.close();
    }

    // ByteArrayInputStream in = new ByteArrayInputStream(dse.toByteArray());

    // return eut.get(in, name, minified);

    return entity;
  }

  public static void getLastRevision(Git git, String base, String name,
      String rev, OutputStream out, String ext)
      throws WsSrvException, IOException {

    checkFile(base, name, ext);
    String fp = getLocalPath(name);

    // find the HEAD
    ObjectId lastCommitId = git.getRepository().resolve(Constants.HEAD);

    // a RevWalk allows to walk over commits based on 
    // 									some filtering that is defined
    RevWalk revWalk = new RevWalk(git.getRepository());
    RevCommit commit = revWalk.parseCommit(lastCommitId);
    // and using commit's tree find the path
    RevTree tree = commit.getTree();
    System.out.println("Having tree: " + tree);

    // now try to find a specific file
    TreeWalk treeWalk = new TreeWalk(git.getRepository());
    treeWalk.addTree(tree);
    treeWalk.setRecursive(true);
    treeWalk.setFilter(PathFilter.create(fp));

    if (!treeWalk.next()) {
      treeWalk.close();
      revWalk.close();
      throw new IllegalStateException("Did not find expected file 'README.md'");
    }
    
    treeWalk.close();
    
    ObjectId objectId = treeWalk.getObjectId(0);
    System.out.println("ObjectId: " + objectId.toString());
    ObjectLoader loader = git.getRepository().open(objectId);

    // and then one can the loader to read the file
    loader.copyTo(System.out);

    revWalk.close();

  }

  /**
   * Push git changes to remote git repository
   * 
   * @param git
   *          Local git repository
   * @param name
   *          Remote name
   * @param url
   *          Remote Git url
   * @return push result
   * @throws WsSrvException
   */
  public static String pushToRemote(Git git, String name, String url)
      throws WsSrvException {
    checkRemoteGitConfig(name, url);

    try {
      String res = "";
      Iterable<PushResult> pres = git.push().setRemote(name).call();
      for (PushResult r : pres)
        res += r.getMessages();

      return res;
    } catch (GitAPIException e) {
      throw new WsSrvException(249, e, "Error push to remote [" + name + "]");
    }
  }

  /**
   * Push git changes from remote git repository
   * 
   * @param git
   *          Local git repository
   * @param name
   *          Remote Git name
   * @param url
   *          Remote Git url
   * @return pull result
   * @throws WsSrvException
   */
  public static boolean pullFromRemote(Git git, String name, String url)
      throws WsSrvException {
    checkRemoteGitConfig(name, url);

    try {
      PullResult pres = git.pull().setRemote(name).call();

      return pres.isSuccessful();
    } catch (GitAPIException e) {
      //-- 250
      throw new WsSrvException(250, e, "Error pull from remote [" + name + "]");
    }
  }

  /**
   * Check local configuration for remote git
   * 
   * @param name
   *          Remote Git name
   * @param url
   *          Remote Git url
   * @throws WsSrvException
   */
  public static void checkRemoteGitConfig(String name, String url)
      throws WsSrvException {
    // Check if remote git configured
    if (url == null)
      throw new WsSrvException(246,
          "Configuration parameter [git_remote_url] is not set");
    else if (url.equals(""))
      throw new WsSrvException(247,
          "Configuration parameter [git_remote_url] is empty");

    // Check if remote git name configured
    if (StringUtils.isEmpty(name))
      throw new WsSrvException(251,
          "Configuration parameter [git_remote_name] is empty");
  }

  public static String getDiff(Git git, String base, String name, String ext)
      throws WsSrvException {
    checkFile(base, name, ext).getAbsolutePath();

    // Prepare path for git save
    String fp = getLocalPath(name);

    List<DiffEntry> diff;
    try {
      diff = git.diff().setPathFilter(PathFilter.create(fp)).call();
    } catch (GitAPIException e) {
      throw new WsSrvException(260, "Unable retrieve git diff", e);
    }

    ByteArrayOutputStream res = new ByteArrayOutputStream();

    for (DiffEntry entry : diff) {
      DiffFormatter formatter = new DiffFormatter(res);
      formatter.setRepository(git.getRepository());
      try {
        formatter.format(entry);
      } catch (IOException e) {
        //-- 261
        throw new WsSrvException(261, "Unable format diff", e);
      } finally {
        formatter.close();
      }
    }

    return res.toString();
  }

  public static boolean hasDiff(Git git, String base, String name, String ext)
      throws WsSrvException {
    checkFile(base, name, ext).getAbsolutePath();

    // Prepare path for git save
    String fp = getLocalPath(name);

    List<DiffEntry> diff;
    try {
      diff = git.diff().setPathFilter(PathFilter.create(fp)).call();
    } catch (GitAPIException e) {
      throw new WsSrvException(260, "Unable retrieve git diff", e);
    }

    return diff.size() > 0;
  }
}
