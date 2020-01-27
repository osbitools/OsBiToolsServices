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
 * Date: 2018-03-10
 * 
 */

package com.osbitools.ws.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

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
import com.osbitools.ws.shared.binding.ds.DataSetDescr;
import com.osbitools.ws.shared.common.TestConstants;

/**
 * Validate all test DataSet maps against XSD
 * 
 */
public class DsMapsConversionTest {

  private final Logger _log = TestConstants.LOG;
  private Marshaller _m;
  private Unmarshaller _um;

  public DsMapsConversionTest() throws SAXException, JAXBException {
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = sf.newSchema(new File(DsConstants.DS_SCHEMA_PATH));

    JAXBContext jc = JAXBContext.newInstance(DsConstants.BIND_PKG_DS_MAP);

    _um = jc.createUnmarshaller();
    _um.setSchema(schema);

    _m = jc.createMarshaller();
    _m.setSchema(schema);
    _m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, DsConstants.DS_SCHEMA_URL);
    _m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
    // unmarshaller.setEventHandler(new DebugValidationEventHandler());
  }

  @Test
  public void testDemoMapConversion() throws Exception {
    DataSetDescr dsd = testDataSetMapConversion(
        new File(TestDsConstants.DS_MAPS_PATH), TestDsConstants.DS_DEMO_FNAME);
    
    DsMapsTestUtils.checkDemoDataSetMap(dsd);
  }

  @Test
  public void testDataSetDirsConversion() throws Exception {
    for (String dname : new String[] { "test", "bad" }) {
      testDataSetDirConversion(dname);
    }
  }

  private void testDataSetDirConversion(String dname) throws Exception {
    _log.debug("Testing " + dname + " directory.");

    // Scan directory
    File dir = new File(TestDsConstants.DS_MAPS_PATH + dname);

    if (!dir.exists())
      fail(dir + " does not exist.");

    if (!dir.isDirectory())
      fail(dir + " exist but it's not directory.");

    // Scan directory for xml files
    String[] flist = dir.list(new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.indexOf("xml") == name.length() - 3;
      }
    });

    assertNotNull(flist);
    assertNotEquals(0, flist.length);

    Arrays.sort(flist);
    for (String fname : flist)
      testDataSetMapConversion(dir, fname);
  }

  public DataSetDescr testDataSetMapConversion(File dir, String fname) throws Exception {
    _log.debug("Validating " + fname);

    // Read input file
    byte[] in = BaseUtils.readFile(new File(dir, fname));

    @SuppressWarnings("unchecked")
    DataSetDescr dsd =
        ((JAXBElement<DataSetDescr>) _um.unmarshal(new ByteArrayInputStream(in))).getValue();
    _log.debug("Description: " + dsd.getDescr());

    // Marshal back
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    _m.marshal(dsd, out);
    String sout = new String(out.toByteArray(), "UTF-8");

    out.close();

    // Flaten input and compare with output
    String expected =
        new String(in, "UTF-8").replaceAll("</ds>\\n", "</ds>").replaceAll("\\n", " ")
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
    
    return dsd;
  }
}
