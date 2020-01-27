/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-08-04
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.pd.shared.service.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.osbitools.ws.pd.shared.common.PdSharedConstants;
import com.osbitools.ws.rest.prj.shared.service.impl.AbstractXmlEntityServiceImpl;
import com.osbitools.ws.wp.shared.WpConstants;
import com.osbitools.ws.wp.shared.binding.WebPage;

@Service
public class PdEntityServiceImpl
    extends AbstractXmlEntityServiceImpl<WebPage> {

  public PdEntityServiceImpl() throws JAXBException, SAXException, IOException  {
    super();
  }

  @Override
  public Map<String, String[]> getSubDirExtList() {
    return PdSharedConstants.EXT_LIST;
  }

  @Override
  protected String getBindPkgName() {
    return WpConstants.BIND_PKG_WEB_PAGE;
  }

  @Override
  protected URL getSchemaUrl() throws IOException {
    return new ClassPathResource("xsd/wp.xsd").getURL();
  }
}
