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

import org.springframework.beans.factory.annotation.Configurable;

import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.core.shared.daemons.LangSet;
import com.osbitools.ws.core.shared.daemons.LsFilesCheck;
import com.osbitools.ws.core.shared.model.*;
import com.osbitools.ws.core.shared.proc.AbstractDataSetProc;
import com.osbitools.ws.base.WsSrvException;

/**
 * Abstract DataSet Producer
 * 
 */

@Configurable("ds_prod")
public abstract class AbstractDataSetProducer<T> {
  // Pointer on LangSet
  private LsFilesCheck _lcheck;

  // Pointer on configuration
  private CoreWsConfig _cfg;

  // DataSet handle
  AbstractDataSetProc _dsp;

  public AbstractDataSetProducer(AbstractDataSetProc proc, LsFilesCheck lcheck,
      CoreWsConfig cfg) {
    _cfg = cfg;
    _dsp = proc;
    _lcheck = lcheck;
  }

  /**
   * 
   * @param name
   *          Name of spec file
   * @param lang
   *          Required language
   * @param trace
   *          Trace handler
   * @param warn
   *          Warn handler
   * @return
   * @throws WsSrvException
   */
  public abstract DataSet read(String name, String lang, TraceRecorder trace,
      List<String> warn) throws WsSrvException;

  abstract T getDataSetSpec();

  public LangSet getLangColumns() {
    return _dsp.getDsExtResource().getLangColumns();
  }

  public AbstractDataSetProc getDataSetProc() {
    return _dsp;
  }

  public DataSet makeNewDataSet(String lang, TraceRecorder trace, List<String> warn) {
    return new DataSet(this, lang, trace, warn);
  }

  public String findLocaleValue(String lang, String value) {
    return _lcheck.getLangLabel(getDataSetProc().getDsDescrResource().getProjectName(), lang,
        value);
  }

  public LsFilesCheck getLsFilesCheck() {
    return _lcheck;
  }

  public CoreWsConfig getCoreWsConfig() {
    return _cfg;
  }

  public void debug(String msg) {
    getDataSetProc().debug(msg);
  }
}
