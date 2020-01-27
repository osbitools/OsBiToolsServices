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

package com.osbitools.ws.core.shared.producers;

import java.util.List;

import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.core.shared.daemons.LsFilesCheck;
import com.osbitools.ws.core.shared.model.*;
import com.osbitools.ws.core.shared.proc.AbstractDataSetProc;
import com.osbitools.ws.core.shared.proc.XmlDataSetProc;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.binding.ds.XmlData;

/**
 * Xml dataSet Producer
 * 
 */
public class XmlDataSetProducer extends AbstractDataSetProducer<XmlData> {

  public XmlDataSetProducer(AbstractDataSetProc dsProc, LsFilesCheck lcheck,
      CoreWsConfig cfg) {
    super(dsProc, lcheck, cfg);
  }

  @Override
  public DataSet read(String name, String lang, TraceRecorder trace, List<String> warn)
      throws WsSrvException {
    // TODO Add implementation
    return null;
  }

  @Override
      XmlData getDataSetSpec() {
    return ((XmlDataSetProc) getDataSetProc()).getDataSetSpec();
  }

}
