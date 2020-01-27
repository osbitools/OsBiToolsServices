/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2015-05-02
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.prj.shared.it.utils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.osbitools.ws.rest.prj.shared.utils.GitUtils;
import com.osbitools.ws.rest.prj.shared.utils.ProjectUtils;
import com.osbitools.ws.base.ErrorList;
import com.osbitools.ws.base.WsSrvException;

/**
 * Git Manager Test
 *
 */
public class GitMgrTest extends GenericPrjMgrTest {
  
  private static Git git;
  
	@BeforeClass
	public static void createGitRepo() throws Exception {
	  git = ProjectUtils.createGitRepo(new File(root + 
	                                    File.separator + ".git"));
	}
	
	@AfterClass
	public static void deleteGitRepo() throws WsSrvException {
		// Delete .git repository
    ProjectUtils.delDir(new File(root + File.separator + ".git"));		
	}
	
	@Test
	public void testSimpleFileCommit() throws WsSrvException, IOException {
		String dname = "zzz";
		String[] flist = new String[] {"test1", "test2"};
		String[] ftext = FUTILS.getDemoSrcSet();
		String[][] commits = new String[2][2];
		
    ProjectUtils.createProject(root, dname);
    
    String prefix = dname + ".";
    String ext = EUT.getExt();
        
    for (String fname: flist)
      EUT.create(root, prefix + fname + 
    	            "." + EUT.getExt(), ftext[0], false, true);
    
    for (int i = 0; i < flist.length; i++)
    	commits[i][0] = GitUtils.commitFile(git, root, prefix + flist[i] + 
    	                              "." + ext, "Created #" + i, "test", ext);
    
    for (String fname: flist)
      EUT.create(root, prefix + fname + 
                    "." + EUT.getExt(), ftext[1], true, true);
    
    for (int i = 0; i < flist.length; i++)
    	commits[i][1] =	GitUtils.commitFile(git, root, prefix + flist[i] + 
    							                "." + ext, "Updated #" + i, "test", ext);
    
    for (int i = 0; i < flist.length; i++) {
    	String fname = flist[i];
    	RevCommit[] log = GitUtils.getLog(git, root, 
    	                            prefix + fname + "." + ext, ext);
    	
    	assertNotNull(log);
    	assertFalse(log.equals(""));
    	
    	int idx = 1;
    	for (RevCommit rev: log) {
    		assertEquals(commits[i][idx], rev.getName());
    		
    		String json = GitUtils.getRevEntity(git, root, prefix + fname + 
    		                            "." + ext, rev.getName(), EUT, true);
    		String[][] set = FUTILS.getTestSet();
    		assertEquals("Testing commit " + rev.getName() + 
    		          " for file " + set[idx][0], set[idx][1], json);
    		idx--;
  		}
    }
    
    ProjectUtils.delProject(root, dname);
	}
	
	@Test
	public void testRemoteGit() {
		
		testRemoteActions("", null, 246, "Configuration parameter" +
																			" [git_remote_url] is not set");
		
		testRemoteActions("", "", 247, "Configuration parameter" +
																			" [git_remote_url] is empty");
		
		testRemoteActions("", "localhost", 251, "Configuration parameter" +
																			" [git_remote_name] is empty");
		
		testRemoteActions("test", "localhost", 249, "Error push to remote [test]", 
														250, "Error pull from remote [test]");
	}
	
	@Test
	public void testDiff() throws WsSrvException, IOException {
	  String dname = "zzz";
	  String fname = "dsd." + EUT.getExt();
    String[] commits = new String[2];
    
    ProjectUtils.createProject(root, dname);
    
    String prefix = dname + ".";
    String[] maps = FUTILS.getDemoSrcSet();
    
    EUT.create(root, prefix + fname, maps[0], false, true);
    
    commits[0] = GitUtils.commitFile(git, root, 
                           prefix + fname, "Created 1", "test", EUT.getExt());
    
    EUT.create(root, prefix + fname, maps[1], true, true);
    
    assertTrue("Detecting Diff failes", GitUtils.hasDiff(git, root, 
                                              prefix + fname, EUT.getExt()));
    
    commits[1] = GitUtils.commitFile(git, root, 
                           prefix + fname, "Updated 2", "test", EUT.getExt());
    
    assertFalse("Detecting No Diff failes", GitUtils.hasDiff(git, root, 
                                              prefix + fname, EUT.getExt()));
    
    ProjectUtils.delProject(root, dname);
	}
	
	private void testRemoteActions(String name, String url, 
	                                      int error, String msg) {
		testRemoteActions(name, url, error, msg, error, msg);
	}
	
	private void testRemoteActions(String name, String url, int pushError, 
												String pushMsg, int pullError, String pullMsg) {
		try {
	    GitUtils.pushToRemote(git, name, url);
	    fail(pushError + " exception expected");
    } catch (WsSrvException e) {
    	assertEquals(pushError, e.getErrorCode());
    	assertEquals(ErrorList.getErrorById(pushError), e.getMessage());
    	assertEquals(pushMsg, e.getErrorInfo());
    }
		
		try {
	    GitUtils.pullFromRemote(git, name, url);
	    fail(pullError + " exception expected");
    } catch (WsSrvException e) {
    	assertEquals(pullError, e.getErrorCode());
    	assertEquals(ErrorList.getErrorById(pullError), e.getMessage());
    	assertEquals(pullMsg, e.getErrorInfo());
    }
	}
}
