/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-02-24
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.me.shared.service.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.osbitools.ws.me.shared.common.MeSharedConstants;
import com.osbitools.ws.rest.prj.shared.service.impl.AbstractXmlEntityServiceImpl;
import com.osbitools.ws.shared.DsConstants;
import com.osbitools.ws.shared.binding.ds.DataSetDescr;

@Service
public class MeEntityServiceImpl
    extends AbstractXmlEntityServiceImpl<DataSetDescr> {

  public MeEntityServiceImpl() throws JAXBException, SAXException, IOException {
    super();
  }

  @Override
  public Map<String, String[]> getSubDirExtList() {
    return MeSharedConstants.EXT_LIST;
  }

  @Override
  protected String getBindPkgName() {
    return DsConstants.BIND_PKG_DS_MAP;
  }

  @Override
  protected URL getSchemaUrl() throws IOException {
    return new ClassPathResource("xsd/ds.xsd").getURL();
  }
}
