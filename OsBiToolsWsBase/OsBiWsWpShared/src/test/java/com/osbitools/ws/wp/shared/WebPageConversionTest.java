/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 * Date: 2018-08-04
 * 
 */

package com.osbitools.ws.wp.shared;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Test;
import org.slf4j.Logger;
import org.xml.sax.SAXException;

import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.shared.common.TestConstants;
import com.osbitools.ws.wp.shared.WpConstants;
import com.osbitools.ws.wp.shared.binding.WebPage;

/**
 * Web Page Conversion tests
 * 
 */
public class WebPageConversionTest {

  private final Logger _log = TestConstants.LOG;
  private Marshaller _m;
  private Unmarshaller _um;
  
  public WebPageConversionTest() throws SAXException, JAXBException {
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = sf.newSchema(new File(WpConstants.WP_SCHEMA_PATH));

    JAXBContext jc = JAXBContext.newInstance(WpConstants.BIND_PKG_WEB_PAGE);

    _um = jc.createUnmarshaller();
    _um.setSchema(schema);

    _m = jc.createMarshaller();
    _m.setSchema(schema);
    _m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, WpConstants.WP_SCHEMA_URL);
    _m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
  }
  
  @Test
  public void testMainDemoWepPageConversion() throws Exception {
    WebPage wp = readWebPage(
        new File(WpTestConstants.WEB_PAGES_PATH), WpTestConstants.WP_DEMO_FNAME);
    
    WebPageTestUtils.checkDemoWebPage(wp);
  }
  
  private WebPage readWebPage(File dir, String fname) throws Exception {
    _log.debug("Validating " + fname);

    // Read input file
    byte[] in = BaseUtils.readFile(new File(dir, fname));

    @SuppressWarnings("unchecked")
    WebPage wp =
        ((JAXBElement<WebPage>) _um.unmarshal(new ByteArrayInputStream(in))).getValue();
    _log.debug("Description: " + wp.getDescr());

    // Marshal back
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    _m.marshal(wp, out);
    String sout = new String(out.toByteArray(), "UTF-8");

    out.close();

    // Flaten input and compare with output
    String expected =
        new String(in, "UTF-8").replaceAll("</wp>\\n", "</wp>").replaceAll("\\n", " ")
            .replaceAll("\\t+", "").replaceAll(">\\s+<", "><").replaceAll("\\s+/>", "/>")
            .replaceAll("\\s+", " ").replaceAll("\\t+", "").replaceAll("<!--.*?-->", "");

    String actual = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        sout.replaceAll("\\n", " ").replaceAll("\\t+", "").replaceAll("\\s+", " ");

    if (_log.isDebugEnabled()) {
      System.out.println(expected);
      System.out.println(actual);
    }

    // Compare by size
    assertEquals(fname + " doesn't match size after marshalling.", expected.length(),
        actual.length());
    
    return wp;
  }
}
