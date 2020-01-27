/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-02-10
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.osbitools.ws.base.WsSrvException;

public abstract class AbstractXmlEntityServiceImpl<T>
    extends AbstractEntityServiceImpl<T> {

  // Singleton Instance of Marshaller
  private Marshaller _m;

  // Singleton instance of Unmarshaller
  private Unmarshaller _um;

  protected abstract String getBindPkgName();

  protected abstract URL getSchemaUrl() throws IOException;
  
  public AbstractXmlEntityServiceImpl() throws JAXBException, SAXException, IOException  {
    JAXBContext _jaxb = JAXBContext.newInstance(getBindPkgName());
    _m = _jaxb.createMarshaller();
    _um = _jaxb.createUnmarshaller();
    
    SchemaFactory sf =
        SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema;
    schema = sf.newSchema(getSchemaUrl());
    _um.setSchema(schema);
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.service.EntityService#get(java.io.InputStream)
   */
  @Override
  public T get(InputStream fileInputStream) throws WsSrvException {
    try {
      return readEntity(fileInputStream);
    } catch (JAXBException e) {
      //-- 227
      throw new WsSrvException(227, e);
    }
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.service.EntityService#get(byte[])
   */
  @Override
  public T get(byte[] data) throws WsSrvException {
    return get(new ByteArrayInputStream(data));
  }

  /* (non-Javadoc)
   * @see com.osbitools.ws.rest.prj.shared.service.impl.AbstractEntityServiceImpl#convert(java.lang.Object)
   */
  @Override
  protected InputStream convert(T entity) throws WsSrvException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      getMarshaller().marshal(entity, out);
    } catch (JAXBException e) {
      //-- 228
      throw new WsSrvException(228, e);
    }

    return new ByteArrayInputStream(out.toByteArray());
  }

  /**
   * Create marshaler to format input object into XML
   * 
   * @param jc
   * 
   * @return
   * @throws WsSrvException
   */
  private Marshaller getMarshaller() throws WsSrvException {
    try {
      _m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
          !getWsCfg().getMinified());
    } catch (JAXBException e) {
      //-- 230
      throw new WsSrvException(230, "Unable configure Marshaller", e);
    }

    return _m;
  }

  @SuppressWarnings("unchecked")
  private T readEntity(InputStream in) throws JAXBException {
    return ((JAXBElement<T>) _um.unmarshal(in)).getValue();
  }
}
