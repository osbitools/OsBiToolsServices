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
import java.util.HashMap;

import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.binding.ll_set.*;

/**
 * 
 * Monitor file with Language Labels
 * 
 */

@Component("lcheck")
public class LsFilesCheck extends AbstractResourceCheck<LangLabelsSet, LsResource> {

  public LsFilesCheck() throws NoSuchMethodException, SecurityException, JAXBException {
    super(LsResource.class);
  }

  @Override
  public void scan(File dir, String prefix) {

    String[] flist = dir.list();

    for (String pname : flist) {
      String path = dir + File.separator + pname;
      File fd = new File(path);
      if (!fd.isDirectory())
        continue;

      String fpath = path + File.separator + LsConstants.LANG_SET_FILE;
      if ((new File(fpath)).exists()) {
        String fkey = pname + "." + LsConstants.LANG_SET_FILE;

        checkResourceFile(fkey, fpath, pname);
      }
    }
  }

  public synchronized HashMap<String, String> getLangLabelSet(String prj, String lang) {
    LsResource res = getResource(prj);
    return res == null ? null : res.getLangLabelSet(lang);
  }

  public synchronized String getLangLabel(String prj, String lang, String key) {
    LsResource res = getResource(prj + LsConstants.LANG_SET_FILE);
    return res == null ? null : res.getLangLabel(lang, key);
  }

  @Override
  String getBindPkg() {
    return LsConstants.BIND_PKG_LANG_LABELS_SET;
  }

  @Override
  int[][] getVersions(LangLabelsSet res) {
    return new int[][] { new int[] { res.getVerMax(), res.getVerMin() }, LsConstants.LS_VER };
  }
}
