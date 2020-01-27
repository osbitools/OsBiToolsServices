/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2018-02-03
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osbitools.ws.rest.prj.shared.service.GitService;
import com.osbitools.ws.rest.prj.shared.service.LangSetService;
import com.osbitools.ws.shared.GenericUtils;
import com.osbitools.ws.shared.LsConstants;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.binding.ll_set.LangLabelsSet;

/**
 * Implementation for Language Labels service
 */

@Service
public class LangSetServiceImpl extends AbstractBaseService
    implements LangSetService {
  //Singleton Instance of Marshaller
  private Marshaller _m;

  // Singleton instance of Unmarshaller
  private Unmarshaller _um;

  @Autowired
  private GitService _git;

  public LangSetServiceImpl() throws JAXBException {
    JAXBContext _jaxb = JAXBContext.newInstance(getBindPkgName());
    _m = _jaxb.createMarshaller();
    _um = _jaxb.createUnmarshaller();
  }

  public String getBindPkgName() {
    return LsConstants.BIND_PKG_LANG_LABELS_SET;
  }

  @Override
  public LangLabelsSet read(String name) throws WsSrvException {

    File f = new File(getWsCfg().getBaseDir() + File.separator + name);
    // Check if it exists and it's directory
    if (!f.exists())
      //-- 261
      throw new WsSrvException(261, "Project [" + name + "] not found.",
          "Directory " + f.getAbsolutePath() + " doesn't exists");
    else if (!f.isDirectory())
      //-- 262
      throw new WsSrvException(262, "Project [" + name + "] is not directory.",
          f.getAbsolutePath() + " is not the directory");

    // Check if file exists
    File fls = new File(getFullFileName(name));
    if (!fls.exists())
      // Return empty label set
      return null;

    return read(fls);
  }

  @Override
  public synchronized void save(String name, LangLabelsSet lls, String comment)
      throws WsSrvException {

    getReqLog().debug("Saving Lang Labels set '" + name + "'");

    // 1. Get full file name
    String fname = getFullFileName(name);

    // 2. Convert object into input stream
    InputStream is = convert(lls);

    // 3. Save file first
    GenericUtils.saveFile(new File(fname), is);

    try {
      is.close();
    } catch (IOException e) {
      // Ignore error
    }

    // 4. Commit file after
    _git.commitLocalFile(getShortFileName(name), comment);
  }

  private LangLabelsSet read(File f) throws WsSrvException {
    // Try open ll_set file
    InputStream in;
    try {
      in = new FileInputStream(f);
    } catch (FileNotFoundException e) {
      //-- 263
      throw new WsSrvException(263, e);
    }

    return readEntity(in);
  }

  @SuppressWarnings("unchecked")
  private LangLabelsSet readEntity(InputStream in) throws WsSrvException {
    try {
      return ((JAXBElement<LangLabelsSet>) _um.unmarshal(in)).getValue();
    } catch (JAXBException e) {
      //-- 264
      throw new WsSrvException(264, e);
    }
  }

  /**
   * Convert object from input format (json) into internal format 
   * that can be saved in file (xml, text etc.)
   * 
   * @param entity
   * @return Input stream with converted file
   * @throws WsSrvException
   */
  protected InputStream convert(LangLabelsSet entity) throws WsSrvException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      _m.marshal(entity, out);
    } catch (JAXBException e) {
      //-- 265
      throw new WsSrvException(265, e);
    }

    return new ByteArrayInputStream(out.toByteArray());
  }
  
  private String getFullFileName(String name) {
    return getWsCfg().getBaseDir() + File.separator + getShortFileName(name);
  }

  private String getShortFileName(String name) {
    return name + File.separator + LsConstants.LANG_SET_FILE;
  }

}
