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

import java.util.HashSet;

import com.osbitools.ws.shared.binding.ds.*;

public class LangSet {
  // Hash set with indexed language columns
  HashSet<String> _lset;

  public LangSet(DataSetExt dsExt) {
    LangMap lmap = dsExt.getLangMap();
    if (lmap != null) {
      _lset = new HashSet<String>();
      for (LangColumn column : lmap.getColumn())
        _lset.add(column.getName());
    }
  }

  public boolean isLangColumn(String column) {
    return (_lset != null && _lset.contains(column));
  }
}
