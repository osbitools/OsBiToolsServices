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
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.it.utils;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

import org.junit.BeforeClass;
import org.junit.Test;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.rest.prj.shared.common.PrjMgrBaseConstants;
import com.osbitools.ws.rest.prj.shared.common.TestPrjMgrBaseConstants;
import com.osbitools.ws.rest.prj.shared.utils.ExFileUtils;
import com.osbitools.ws.rest.prj.shared.utils.ProjectUtils;
import com.osbitools.ws.shared.GenericUtils;

public class ExFileMgrTest extends GenericPrjMgrTest {
  
  @BeforeClass
  public static void prepDemoProjDir() throws IOException, URISyntaxException {
    // Copy all top demo maps into demo folder
    FUTILS.copyDemoProj(TestPrjMgrBaseConstants.DEMO_PRJ_DIR_S);
  }
  
  @Test
  public void testExFileList() throws WsSrvException {
    String dname = "test";
    String fname = dname + ".*";
    
    assertTrue("Ext File Mask doesn't match",
        PrjMgrBaseConstants.EXT_FILE_LIST_MASK.matcher(fname).matches());
    
    HashMap<String, String[]> res = EUT.getSubDirExtList();
    if (res == null)
      return;
    
    for (String rname: res.keySet()) {
      assertEquals("List of ex files doesn't match", 
          FUTILS.getExtFileList(rname), GenericUtils.
                  getResDirExtList(TestPrjMgrBaseConstants.DEMO_PRJ_DIR_S, 
                      fname, rname, EUT.getExtLstFilenameFilter(rname)));
    }
  }
  
  @Test
  public void testExtFileInfo() throws WsSrvException {
    String dname = "test";
    
    HashMap<String, String[]> res = EUT.getSubDirExtList();
    if (res == null)
      return;
    
    for (String rname: res.keySet()) {
      String [][] files = FUTILS.getExtFileTestFiles(rname);
      for (int i = 0; i < files.length; i++) {
        String[] ftest = files[i];
        
        Pattern p = Pattern.compile(ftest[1].substring(1, 
                                                    ftest[1].length() - 1));
        String info = getInfo(TestPrjMgrBaseConstants.DEMO_PRJ_DIR, 
            dname + "." + ftest[0], EUT.getExtList(rname), 
            rname, new HashMap<String, String>(), true);
        
        assertTrue("File info for " + ftest[0] + " is invalid", 
                                              p.matcher(info).matches());
      }
    }
  }
  
  @Test
  public void testExFile() throws WsSrvException {
    HashMap<String, String[]> res = EUT.getSubDirExtList();
    if (res == null)
      return;
    
    String dname = "xxx";
    ProjectUtils.createProject(getRoot(), dname);
    
    for (String rname: res.keySet()) {
      HashSet<String> extl = EUT.getExtList(rname);
      for (String ext: extl) {
        HashMap <String, String>[] tp = FUTILS.getExtFileTestSetParams(rname);
        TestFileItem[] ts = FUTILS.getExtFileTestSet(rname, ext);
        for (int i = 0; i < ts.length; i++)
          testExFile(dname + ".test" + i, 
              ts[i].getFileText(), ts[i].getStrResponse(), extl,
                                        tp != null ? tp[i] : null, rname, ext);
      }
    }
    
    ProjectUtils.delProject(getRoot(), dname);
  }
  
  private void testExFile(String name, byte[] ftext, String fresp, 
      HashSet<String> extl, HashMap <String, String> params, String sdir, 
                                        String ext) throws WsSrvException {

    String cname = name + "." + ext;
    String rname = name + "_temp." + ext;
    
    String res = ExFileUtils.createFile(getRoot(), cname, 
                    new ByteArrayInputStream(ftext), 
                                          extl, sdir, params, EUT, true);
    
    assertEquals("Return result doesn't match for " + name, fresp, res);
    
    GenericUtils.renameFile(root, cname, rname, extl, sdir);
    
    assertArrayEquals("File after get doesn't match original file", 
        ftext, ExFileUtils.getFile(getRoot(), rname, extl, sdir));

    GenericUtils.deleteFile(root, rname, extl, sdir);
  }
  
  
  /**
   * Get File Info
   * 
   * @param base Base Directory Name
   * @param name File Name
   * @param extl Set of extensions
   * @param subDir Sub Directory
   * @param params Set of input parameters
   * @return String with File Info
   * 
   * @throws WsSrvException
   */
  public String getInfo(String base, String name, HashSet<String> extl,
      String subDir, HashMap<String, String> params, boolean minified)
      throws WsSrvException {
    File f = GenericUtils.checkFile(base, name, extl, subDir, true);

    return getInfo(f);
  }

  /**
   * Get File Info
   * 
   * @param base Base Directory Name
   * @param name File Name
   * @param ext File Extension
   * @param params Set of input parameters
   * @return String with File Info
   * 
   * @throws WsSrvException
   */
  public String getInfo(String base, String name, String ext,
      HashMap<String, String> params) throws WsSrvException {
    File f = GenericUtils.checkFile(base, name, ext, true);

    return getInfo(f);
  }

  /**
   * Get File Info
   * 
   * @param f Input File
   * 
   * @throws WsSrvException
   */
  public String getInfo(File f) throws WsSrvException {
    return "{\"size\":" + f.length() + "," + "\"read\":" +
        f.canRead() + ",\"write\":" + f.canWrite() + ",\"last_modified\":" +
        f.lastModified() + "}";
  }


}
