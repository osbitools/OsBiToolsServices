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
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.service;

import org.eclipse.jgit.revwalk.RevCommit;

import com.osbitools.ws.rest.prj.shared.dto.RevCommitDto;
import com.osbitools.ws.rest.prj.shared.model.GitFile;
import com.osbitools.ws.base.WsSrvException;

/**
 * Interface for Git services
 * 
 */
public interface GitService {

  /**
   * Commit file
   * 
   * @param name File name
   * @param comment Revision comment
   * @return Revision id
   * @throws WsSrvException
   */
  String commitFile(String name, String comment) throws WsSrvException;

  /**
   * Get log entries for given file
   * 
   * @param name File name
   * @return array with RevCommit entries
   * @throws WsSrvException
   */
  RevCommit[] getLog(String name) throws WsSrvException;

  /**
   * Retrieve file by revision id
   * 
   * @param name File name
   * @param rev Revision Id
   * @return GitFile object
   * @throws WsSrvException
   */
  GitFile getRevFile(String name, String rev) throws WsSrvException;

  /**
   * Push git changes to remote git repository
   * 
   * @param git Local git repository
   * @param name Remote name
   * @param url Remote Git url
   * @return push result
   * @throws WsSrvException
   */
  String pushToRemote() throws WsSrvException;

  /**
   * Pull data from remote repository
   * 
   * @return True on success and False on
   * @throws WsSrvException
   */
  boolean pullFromRemote() throws WsSrvException;

  /**
   * Check if file has diff
   * @@param name File name
   * @return True if file has diff and False if not
   * @throws WsSrvException
   */
  boolean hasDiff(String name) throws WsSrvException;

  /**
   * Check if file has logs
   * 
   * @param name File name
   * @return True if file has logs and False if not
   * @throws WsSrvException
   */
  boolean hasLog(String name) throws WsSrvException;

  /**
   * Commit local file
   * 
   * @param name File name
   * @param comment Comments
   * @return Revision Id
   * @throws WsSrvException
   */
  String commitLocalFile(String name, String comment) throws WsSrvException;

  /**
   * Retrieve all revisions for given entity
   * 
   * @param name Entity Name
   * @return Array with revisions
   * 
   * @throws WsSrvException
   */
  RevCommitDto[] getAllRevisions(String name) throws WsSrvException;

}
