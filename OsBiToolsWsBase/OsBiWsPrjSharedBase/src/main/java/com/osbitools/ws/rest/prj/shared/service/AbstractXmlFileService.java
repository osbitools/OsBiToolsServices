/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2015-08-07
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.rest.prj.shared.service.impl.AbstractBaseService;
import com.osbitools.ws.shared.*;

/**
 * 
 * Basic utilites to read/write/get Json for Xml file
 * 
 * @param <T> Top XML class
 */

public abstract class AbstractXmlFileService<T> extends AbstractBaseService {

  public abstract String getBindPkgName();

  // Jaxb context singleton
  private JAXBContext _jc = null;

  public T create(File dest, InputStream in, boolean overwrite,
      boolean minified) throws WsSrvException {
    // 1. Validate XML File
    T obj = validateFile(in);

    // Reset input stream
    try {
      in.reset();
    } catch (IOException e) {
      //-- 223
      throw new WsSrvException(223, e);
    }

    // 2. Save initial xml
    GenericUtils.saveFile(dest, in);

    return obj;
  }

  public T validateFile(InputStream in) throws WsSrvException {
    T res = null;
    JAXBContext jc = getJAXBContext();

    try {
      res = readFile(jc, in);
    } catch (JAXBException e) {
      //-- 225
      throw new WsSrvException(225, e.getCause(), "Error parsing xml");
    }

    return res;
  }

  private synchronized JAXBContext getJAXBContext() throws WsSrvException {
    if (_jc == null) {
      String pkg = getBindPkgName();
      try {
        _jc = JAXBContext.newInstance(pkg);
      } catch (JAXBException e) {
        //-- 226
        throw new WsSrvException(226,
            "Unable create JAXB instance for \"" + pkg + "\"", e);
      }
    }

    return _jc;
  }

  @SuppressWarnings("unchecked")
  public T readEntity(InputStream in) throws WsSrvException, JAXBException {
    JAXBContext jc = getJAXBContext();
    return ((JAXBElement<T>) jc.createUnmarshaller().unmarshal(in)).getValue();
  }

  @SuppressWarnings("unchecked")
  private T readFile(JAXBContext jc, InputStream in) throws JAXBException {
    return ((JAXBElement<T>) jc.createUnmarshaller().unmarshal(in)).getValue();
  }

}
