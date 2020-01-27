/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-11-07
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.daemons;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import com.osbitools.ws.core.shared.common.CoreConstants;
import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.binding.ds.DataSetDescr;

/**
 * DataSet checking daemon
 *
 */

@Component("dcheck")
public class DsFilesCheck
    extends AbstractResourceCheck<DataSetDescr, DsDescrResource> {

  public DsFilesCheck()
      throws JAXBException, NoSuchMethodException, SecurityException {
    super(DsDescrResource.class);
  }

  @Override
  public void scan(File dir, String prefix) {
    File[] flist = dir.listFiles();

    for (File f : flist) {
      if (f.isDirectory()) {
        // Recursion
        scan(f, prefix + f.getName() + ".");
      } else {
        // Check extension
        String fname = f.getName();
        int pos = fname.lastIndexOf(".");
        if (pos <= 0 || pos == fname.length() - 1 ||
            !DsConstants.BASE_EXT.equals(fname.substring(pos + 1)))
          continue;

        // Read file name with extension
        String mname = prefix + fname;

        String fpath = getBasePath() + File.separator +
            prefix.replaceAll("\\.", File.separator) + fname;

        checkResourceFile(mname, fpath, prefix);
      }
    }
  }

  @Override
  String getBindPkg() {
    return DsConstants.BIND_PKG_DS_MAP;
  }

  /**
   * Return array with custom loaded maps (excluding system)
   * 
   * @return array with custom loaded maps (excluding system)
   */
  public String[] getDsMapList() {
    String[] list = getAllResources();
    ArrayList<String> res = new ArrayList<String>();

    for (String name : list) {
      int pos = name.indexOf(".");
      
      // Only add resource with extension
      if (pos > 0 && pos < name.length() - CoreConstants.BASE_EXT_LEN) {
        res.add(name);
      }
    }

    return res.toArray(new String[res.size()]);
  }

  @Override
  int[][] getVersions(DataSetDescr res) {
    return new int[][] { new int[] { res.getVerMax(), res.getVerMin() },
        DsConstants.DS_VER };
  }

}
