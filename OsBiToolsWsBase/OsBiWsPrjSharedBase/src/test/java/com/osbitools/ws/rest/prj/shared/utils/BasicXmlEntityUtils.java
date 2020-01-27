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

package com.osbitools.ws.rest.prj.shared.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.osbitools.ws.rest.prj.shared.utils.EntityUtils;
import com.osbitools.ws.base.WsSrvException;

/**
 * 
 * Basic utilites to read/write/get Json for Project Entity file
 * 
 * @param <T> Top XML class
 */

public abstract class BasicXmlEntityUtils<T> extends EntityUtils {

  private static final ObjectMapper mapper = new ObjectMapper();

  static {
    mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    mapper.setSerializationInclusion(Include.NON_NULL);
  }
  
  public abstract String getBindPkgName();

  @Override
  public String create(String base, String name, InputStream in,
      boolean overwrite, boolean minified) throws WsSrvException {
    File f = checkFile(base, name, getExt(), overwrite);

    // 1. Validate XML Entity
    T entity = validateEntity(in);

    // Reset input stream
    try {
      in.reset();
    } catch (IOException e) {
      //-- 223
      throw new WsSrvException(223, e);
    }

    // 2. Save initial xml
    saveFile(f, in);

    return getJson(entity, minified);
  }

  @SuppressWarnings("unchecked")
  @Override
  public String getJson(InputStream in, String name, boolean minified)
      throws WsSrvException {
    T entity;
    JAXBContext jc = getJAXBContext();

    try {
      entity = ((JAXBElement<T>) jc.createUnmarshaller().unmarshal(in))
          .getValue();
    } catch (JAXBException e) {
      String msg = "Invalid xml entity \"" + name + "\"";
      if (e.getMessage() != null)
        //-- 224
        throw new WsSrvException(224, msg, e);
      else
        //-- 224
        throw new WsSrvException(224, msg, e.getCause());
    }

    return getJson(entity, minified);
  }

  public T validateEntity(String entity) throws WsSrvException {
    return validateEntity(new ByteArrayInputStream(entity.getBytes()));
  }

  public T validateEntity(InputStream in) throws WsSrvException {
    T res = null;
    JAXBContext jc = getJAXBContext();

    try {
      res = readEntity(jc, in);
    } catch (JAXBException e) {
      //-- 225
      throw new WsSrvException(225, "Invalid xml entity", e);
    }

    return res;
  }

  private JAXBContext getJAXBContext() throws WsSrvException {
    String pkg = getBindPkgName();
    try {
      return JAXBContext.newInstance(pkg);
    } catch (JAXBException e) {
      //-- 226
      throw new WsSrvException(226,
          "Unable create JAXB instance for \"" + pkg + "\"", e);
    }
  }
  
/*
 // TODO Delete obsoleted
  private Marshaller getMarshaller(JAXBContext jc, boolean formattedOutput)
      throws WsSrvException {
    Marshaller m;

    try {
      m = jc.createMarshaller();
      m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formattedOutput);
    } catch (JAXBException e) {
      //-- 227
      throw new WsSrvException(227,
          "Unable create Marshaller for \"" + getBindPkgName() + "\"", e);
    }

    return m;
  }
*/
  @SuppressWarnings("unchecked")
  private T readEntity(JAXBContext jc, InputStream in) throws JAXBException {
    return ((JAXBElement<T>) jc.createUnmarshaller().unmarshal(in)).getValue();
  }

  private String getJson(T entity, boolean minified)
      throws WsSrvException {
    
    // Map entity list into JSON string
    if (minified)
      mapper.disable(SerializationFeature.INDENT_OUTPUT);
    else
      mapper.enable(SerializationFeature.INDENT_OUTPUT);

    try {
      return mapper.writeValueAsString(entity);
    } catch (JsonProcessingException e) {
      //-- 302
      throw new WsSrvException(302, e);
    }
    
    /*  // TODO Delete obsoleted
    JAXBContext jc = getJAXBContext();

    Marshaller m = getMarshaller(jc, !minified);

    try {
      // m.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
      // m.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
      m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
    } catch (PropertyException e) {
      //-- 208
      throw new WsSrvException(208,
          "Unable create JSON Marshaller for \"" + getBindPkgName() + "\"", e);
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      m.marshal(entity, out);
    } catch (JAXBException e) {
      //-- 209
      throw new WsSrvException(209, "Error converting \"" + name + "\" to JSON",
          e);
    }

    return out.toString();
    */
  }

  @Override
  public boolean hasInfoReq(String info) {
    return false;
  }

  @Override
  public String execInfoReq(String info, String base, String fname,
      HashSet<String> extl, String subDir, HashMap<String, String> param,
      boolean minifieds) throws WsSrvException {
    return null;
  }
}
