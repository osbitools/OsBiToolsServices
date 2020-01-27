/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-11-09
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.it.utils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.osbitools.ws.shared.Constants;
import com.osbitools.ws.base.ErrorList;
import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.common.GenericTest;
import com.osbitools.ws.rest.prj.shared.utils.ProjectUtils;

/**
 * Test Project processing using direct utils access
 * 
 */
public class ProjMgrTest extends GenericPrjMgrTest {

  @Before
  public void checkEmptyDirOnStart() {
    // Clear root folder
    try {
      BaseUtils.delDirRecurse(froot, false);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @After
  public void checkEmptyDirOnEnd() {
    assertEquals("Check init proj list is empty", "",
    		ProjectUtils.getAllProjects(root));
  }
  
  @Test
  public void testBadProj() {
    for (int i = 0; i < GenericTest.BAD_ID_LIST.length; i++) {
      String id = GenericTest.BAD_ID_LIST[i];
      
      try {
	      ProjectUtils.createProject(root, id);
	      fail("4 Exception expected");
      } catch (WsSrvException e) {
        checkWsSrvException(id, 4, e, 
                          "Invalid name \\\"" + id + "\\\"");
      }
      
      try {
	      ProjectUtils.createProject(root, id + "." + id);
	      fail("3 Exception expected");
      } catch (WsSrvException e) {
        checkWsSrvException(id, 3, e, 
            "Only " + Constants.MAX_PROJ_LVL + 
              " level structure supported " +
                "but found 2 in name \\\"" + id + "." + id + "\\\"");
      }
      
      try {
        ProjectUtils.createProject(root, "." + id);
        fail("3 Exception expected");
      } catch (WsSrvException e) {
        checkWsSrvException(id, 3, e, 
            "Only " + Constants.MAX_PROJ_LVL + 
            " level structure supported " +
              "but found 2 in name \\\"" + "." + id + "\\\"");
      }
      
      try {
	      ProjectUtils.createProject(root, "../" + id);
	      fail("3 Exception expected");
      } catch (WsSrvException e) {
        checkWsSrvException(id, 3, e, 
            "Only 1 level structure supported " +
                "but found 3 in name \\\"../" + id + "\\\"");
      }
      
      try {
	      ProjectUtils.createProject(root, id);
	      fail("4 Exception expected");
      } catch (WsSrvException e) {
        checkWsSrvException(id, 4, e, 
            "Invalid name \\\"" + id + "\\\"");
      }
      
      try {
      	ProjectUtils.getProject(root, id, EUT.getExt());
        fail("4 Exception expected");
      } catch (WsSrvException e) {
        checkWsSrvException(id, 4, e, 
            "Invalid name \\\"" + id + "\\\"");
      }
    }
  }
  
  @Test
  public void testSelect() throws Exception {
    // Copy all top demo files into working ds folder
    FUTILS.copyDemoProj(root);
    
    // Test list of projects
    assertEquals("List of projects doesn't match", 
                              FUTILS.getProjListSorted(), 
                                    ProjectUtils.getAllProjects(root));
    
    // Test all entities for projects
    assertEquals("List of projects entities doesn't match", 
                            FUTILS.getProjEntitiesListSorted(), 
               ProjectUtils.getAllProjectsEntities(root, EUT.getExt()));
    
    // Test list of entities per project
    for (String[] tp : FUTILS.getProjList()) {
      String dlist = ProjectUtils.getProject(root, tp[0], EUT.getExt());
      
      assertNotNull(dlist);
      assertEquals(false, dlist.equals(""));
      
      assertEquals("Can't read test project", tp[1], dlist);
    }
    
    // Delete temp file
    BaseUtils.delDirRecurse(froot, false);
  }
  
  @Test
  public void testCreate() throws Exception {
  	String dst = "";
  	String[] dlist = new String[] {"test1", "test2", "test3"};
  	
  	for (String ds: dlist) {
  		dst += "," + ds;
	  	ProjectUtils.createProject(root, ds);
	  	assertEquals("Project test1 created", dst.substring(1), 
	  											ProjectUtils.getAllProjects(root));
	  	assertEquals("Project " + ds + " is empty", "", 
	  											ProjectUtils.getProject(root, ds, EUT.getExt()));
	  	
	  	// Try create project with same name
	  	try {
      	ProjectUtils.createProject(root, ds);
        fail("2 Exception expected");
      } catch (WsSrvException e) {
        assertEquals("Testing " + ds, 2, e.getErrorCode());
        assertEquals(ErrorList.getErrorById(2), e.getMessage());
      }
	  	
	  	Thread.sleep(1000);
  	}
  	
    for (String ds: dlist)
      ProjectUtils.delProject(root, ds);
  }
  
  @Test
  public void testUpdate() throws Exception {
  	// Create test1
  	ProjectUtils.createProject(root, "test1");
  	assertEquals("Project test1 created", "test1", 
  														ProjectUtils.getAllProjects(root));
  	
  	// Rename test1->test2
  	ProjectUtils.renameProject(root, "test1", "test2");
  	assertEquals("Project test1 created", "test2", 
													ProjectUtils.getAllProjects(root));
  	
  	Thread.sleep(1000);
  	
  	// Create test1
   	ProjectUtils.createProject(root, "test1");
   	assertEquals("Project test1 created", "test1,test2", 
   														ProjectUtils.getAllProjects(root));
   	
   	for (String ds: new String[] {"test1","test2"})
      ProjectUtils.delProject(root, ds);
  }
  
  @Test
  public void testInvalidProj() throws FileNotFoundException, IOException {
    // Create file
    File ftemp = new File(root + File.separator + "test");
    (new FileOutputStream(ftemp)).close();
    
    // Try read file instead of directory
    try {
      ProjectUtils.getProject(root, "test", EUT.getExt());
      fail("10 exception expected");
    } catch (WsSrvException e) {
      checkWsSrvException("test", 10, e,
      											"@Entry \\\\\".*test\\\\\" is not a directory@");
    }
    
    // Delete temp file
    assertTrue("Deleting test", ftemp.delete());
  }
}
