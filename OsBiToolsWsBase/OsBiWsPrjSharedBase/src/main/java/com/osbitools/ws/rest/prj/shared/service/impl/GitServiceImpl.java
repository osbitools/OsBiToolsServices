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

package com.osbitools.ws.rest.prj.shared.service.impl;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.osbitools.ws.rest.prj.shared.dto.RevCommitDto;
import com.osbitools.ws.rest.prj.shared.model.GitFile;
import com.osbitools.ws.rest.prj.shared.service.GitService;
import com.osbitools.ws.shared.GenericUtils;
import com.osbitools.ws.base.WsSrvException;

/**
 * Set of utilities to work with Git repository
 * 
 */

@Service
public class GitServiceImpl extends AbstractBaseService implements GitService {

  @Autowired
  @Qualifier("log")
  private Logger _log;

  @Lazy
  @Autowired
  private Git _git;

  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.service.GitService#commitFile(java.lang.String, java.lang.String)
   */
  @Override
  public String commitFile(String name, String comment) throws WsSrvException {
    // Prepare path for git save
    return commitLocalFile(getLocalPath(name), comment);
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.service.GitService#commitLocalFile(java.lang.String, java.lang.String)
   */
  @Override
  public String commitLocalFile(String name, String comment) throws WsSrvException {
    return commitFile(_git, name, comment, getReqLog().getLoginUser());
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.service.GitService#getLog(java.lang.String)
   */
  @Override
  public RevCommit[] getLog(String name) throws WsSrvException {
    ArrayList<RevCommit> res = getLogEx(name);
    return res.toArray(new RevCommit[res.size()]);
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.service.GitService#getRevFile(java.lang.String, java.lang.String)
   */
  @Override
  public GitFile getRevFile(String name, String revId) throws WsSrvException {
    String fp = getLocalPath(name);

    Iterable<RevCommit> logs;

    try {
      logs = _git.log().addPath(fp).add(getRevObjectId(revId)).setMaxCount(1).call();
    } catch (GitAPIException | MissingObjectException | IncorrectObjectTypeException e) {
      throw new WsSrvException(242, e);
    }

    RevCommit rev = null;

    for (RevCommit rc : logs) {
      // Expecting a single record only
      rev = rc;
      break;
    }

    if (rev == null)
      throw new WsSrvException(243, "Revision " + rev + " not found for entry " + fp);

    // now try to find a specific file
    TreeWalk tw = new TreeWalk(_git.getRepository());

    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try {
      tw.addTree(rev.getTree());
      tw.setRecursive(true);
      tw.setFilter(PathFilter.create(fp));
      if (!tw.next())
        //-- 244
        throw new WsSrvException(244, "Entry " + fp + " not found in revision " + rev);

      ObjectId objectId = tw.getObjectId(0);
      ObjectLoader loader = _git.getRepository().open(objectId);

      // and then one can the loader to read the file
      loader.copyTo(out);
      GitFile gfile = new GitFile(out.toByteArray(), true, hasDiff(name));

      return gfile;

    } catch (Exception e) {
      //-- 245
      throw new WsSrvException(245, "Error retrieving revision " + rev + " for entry " + fp);
    } finally {
      tw.close();
    }
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.service.GitService#pushToRemote()
   */
  @Override
  public String pushToRemote() throws WsSrvException {
    checkRemoteGitConfig();

    try {
      String res = "";
      Iterable<PushResult> pres = _git.push().setRemote(getWsCfg().getGitRemoteName()).call();
      for (PushResult r : pres)
        res += r.getMessages();

      return res;
    } catch (Exception e) {
      //-- 249
      throw new WsSrvException(249, e,
          "Error push to remote [" + getWsCfg().getGitRemoteName() + "]");
    }
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.service.GitService#pullFromRemote()
   */
  @Override
  public boolean pullFromRemote() throws WsSrvException {
    checkRemoteGitConfig();

    try {
      PullResult pres = _git.pull().setRemote(getWsCfg().getGitRemoteName())
          .setRemoteBranchName("master").call();

      return pres.isSuccessful();
    } catch (GitAPIException e) {
      //-- 250
      throw new WsSrvException(250, e,
          "Error pull from remote [" + getWsCfg().getGitRemoteName() + "]");
    }
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.service.GitService#hasDiff(java.lang.String)
   */
  @Override
  public boolean hasDiff(String name) throws WsSrvException {
    // Prepare path for git save
    String fp = getLocalPath(name);

    List<DiffEntry> diff;
    try {
      diff = _git.diff().setPathFilter(PathFilter.create(fp)).call();
    } catch (GitAPIException e) {
      throw new WsSrvException(260, "Unable retrieve git diff", e);
    }

    return diff.size() > 0;
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.service.GitService#hasLog(java.lang.String)
   */
  @Override
  public boolean hasLog(String name) throws WsSrvException {
    return getLogEx(name).size() > 0;
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.service.GitService#getAllRevisions(java.lang.String)
   */
  @Override
  public RevCommitDto[] getAllRevisions(String name) throws WsSrvException {
    // Read all revisions
    RevCommit[] rlist = getLog(name);
    RevCommitDto[] res = new RevCommitDto[rlist.length];

    for (int i = 0; i < rlist.length; i++) {
      RevCommit rc = rlist[i];
      res[i] = new RevCommitDto(rc.getName(), rc.getFullMessage().replace("\"", "\\\""),
          rc.getCommitTime(), rc.getCommitterIdent().getName());
    }

    return res;
  }

  /**
   * @param name
   * @return
   * @throws WsSrvException
   */
  private String getLocalPath(String name) throws WsSrvException {
    String res = GenericUtils.checkFile(getWsCfg().getBaseDir(), name, getWsCfg().getBaseExt())
        .getAbsolutePath();

    // Remove base directory from path
    int idx = res.lastIndexOf(getWsCfg().getBaseDir());
    if (idx >= 0)
      res = res.substring(idx + getWsCfg().getBaseDir().length() + 1);

    return res;
  }

  private ArrayList<RevCommit> getLogEx(String name) throws WsSrvException {
    // Prepare path for git save
    String fname = getLocalPath(name);

    return getLogByFileName(_git, fname);
  }

  private String commitFile(Git git, String fname, String comment, String user)
      throws WsSrvException {

    AddCommand add = git.add();
    try {
      add.addFilepattern(fname).call();
    } catch (GitAPIException e) {
      throw new WsSrvException(239, e);
    }

    CommitCommand commit =
        git.commit().setCommitter(user, "").setMessage((comment != null) ? comment : "");

    try {
      RevCommit rev = commit.call();
      return rev.getName();
    } catch (GitAPIException e) {
      throw new WsSrvException(240, e);
    }
  }

  private ArrayList<RevCommit> getLogByFileName(Git git, String fname) throws WsSrvException {
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

  /**
   * Check local configuration for remote git
   * 
   * @param name Remote Git name
   * @param url Remote Git url
   * @throws WsSrvException
   */
  private void checkRemoteGitConfig() throws WsSrvException {
    // Check if remote git configured
    if (StringUtils.isEmpty(getWsCfg().getGitRemoteUrl()))
      //-- 246
      throw new WsSrvException(246,
          "Configuration parameter [git_remote_url] is not set or not correct");

    // Check if remote git name configured
    if (StringUtils.isEmpty(getWsCfg().getGitRemoteName()))
      //-- 247
      throw new WsSrvException(247, "Configuration parameter [git_remote_name] is empty");
  }

  /**
   * Split Revision ID into 5 groups and return combined Object Id
   * 
   * @param revId Revision Id String
   * @return Revision Id Object
   */
  public static ObjectId getRevObjectId(String revId) {
    int[] rvl = new int[5];
    for (int k = 0; k < 5; k++)
      rvl[k] = new BigInteger(revId.substring(k * 8, (k + 1) * 8), 16).intValue();
    return new ObjectId(rvl[0], rvl[1], rvl[2], rvl[3], rvl[4]);
  }

}
