/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2015-03-02
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.producers;

import java.util.List;

import com.osbitools.ws.shared.binding.ds.*;
import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.core.shared.daemons.DsExtResource;
import com.osbitools.ws.core.shared.daemons.LsFilesCheck;
import com.osbitools.ws.core.shared.model.*;
import com.osbitools.ws.core.shared.proc.AbstractDataSetProc;
import com.osbitools.ws.core.shared.proc.GroupDataSetProc;
import com.osbitools.ws.base.WsSrvException;

/**
 * Group DataSet Producer
 * 
 */
public class GroupDataSetProducer extends AbstractDataSetProducer<GroupData> {

  public GroupDataSetProducer(AbstractDataSetProc dsProc, LsFilesCheck lcheck,
      CoreWsConfig cfg) {
    super(dsProc, lcheck, cfg);
  }

  @Override
  public DataSet read(String name, String lang, TraceRecorder trace, List<String> warn)
      throws WsSrvException {

    DataSet d = makeNewDataSet(lang, trace, warn);
    d.startData();

    // Read all included resources
    for (DsExtResource dser : ((GroupDataSetProc) getDataSetProc()).getDsExtList())
      d.append(dser.getDataSetProc().readDataSet(name, lang, trace, warn, getLsFilesCheck(),
          getCoreWsConfig()));

    d.endData();
    return d;
  }

  @Override
      GroupData getDataSetSpec() {
    return ((GroupDataSetProc) getDataSetProc()).getDataSetSpec();
  }

}
