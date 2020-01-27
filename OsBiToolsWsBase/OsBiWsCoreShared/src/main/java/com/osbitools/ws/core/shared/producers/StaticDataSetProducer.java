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

import com.osbitools.ws.shared.binding.ds.*;
import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.core.shared.daemons.LsFilesCheck;
import com.osbitools.ws.core.shared.model.*;
import com.osbitools.ws.core.shared.proc.AbstractDataSetProc;
import com.osbitools.ws.core.shared.proc.StaticDataSetProc;
import com.osbitools.ws.base.WsSrvException;

/**
 * DataSet Producer for static records source
 * 
 */
public class StaticDataSetProducer extends AbstractDataSetProducer<StaticData> {

  public StaticDataSetProducer(AbstractDataSetProc dsProc, LsFilesCheck lcheck,
      CoreWsConfig cfg) {
    super(dsProc, lcheck, cfg);
  }

  @Override
      StaticData getDataSetSpec() {
    return ((StaticDataSetProc) getDataSetProc()).getDataSetSpec();
  }

  @Override
  public DataSet read(String name, String lang, TraceRecorder trace, List<String> warn)
      throws WsSrvException {
    DataSet d = makeNewDataSet(lang, trace, warn);
    StaticData sd = getDataSetSpec();

    d.startData();

    for (ColumnHeader ch : sd.getColumns().getColumn())
      d.addColumn(ch.getName(), ch.getJavaType());
    d.endColumn();

    for (RowDef rd : sd.getStaticRows().getRow()) {
      d.startRecord();

      for (RowCell cd : rd.getCell()) {
        Object value = d.createValue(cd.getName(), cd.getValue());
        d.addValue(cd.getName(), value);
      }

      d.endRecordSuccess();
    }

    d.endData();

    return d;
  }
}
