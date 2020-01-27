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

package com.osbitools.ws.core.shared.daemons;

import com.osbitools.ws.core.shared.proc.AbstractDataSetProc;
import com.osbitools.ws.shared.binding.ds.*;

/**
 * Implementation of ResourceInfo for DataSet Description
 *
 */
public class DsExtResource {

  // Hash set with indexed language columns
  private LangSet _lset;

  // Handle for DataSetExt
  private DataSetExt _dse;

  // Handle for corresponded DataSet processor
  private AbstractDataSetProc _dproc;

  public DsExtResource(DataSetExt dsExt) {
    _dse = dsExt;

    // Set LangSet
    setLangSet();
  }

  public void setLangSet() {
    _lset = new LangSet(_dse);
  }

  public LangSet getLangColumns() {
    return _lset;
  }

  public DataSetExt getDataSetExt() {
    return _dse;
  }

  public AbstractDataSetProc getDataSetProc() {
    return _dproc;
  }

  public void setDataSetProc(AbstractDataSetProc dsProc) {
    _dproc = dsProc;
  }

}