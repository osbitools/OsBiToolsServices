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

package com.osbitools.ws.core.shared.proc;

import java.util.HashMap;

import com.osbitools.ws.shared.binding.ds.*;
import com.osbitools.ws.core.shared.daemons.DsExtResource;
import com.osbitools.ws.core.shared.producers.StaticDataSetProducer;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.service.RequestLogger;

/**
 * Static DataSet Processor
 * 
 */
public class StaticDataSetProc
    extends RealDataSetProc<StaticDataSetProducer, StaticDataSetDescr, StaticData> {

  public StaticDataSetProc(DsExtResource dsExtResource,
      HashMap<String, Object> requestParameters, RequestLogger log) throws WsSrvException {
    super(StaticDataSetProducer.class, dsExtResource, requestParameters, log);
  }

  @Override
  public StaticData getDataSetSpec() {
    DataSetExt dse = getDataSetExt();
    return (dse.getClass().equals(DataSetDescr.class)) ? ((DataSetDescr) dse).getStaticData()
        : ((StaticDataSetDescr) dse).getStaticData();
  }
}
