/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2018-02-03
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.service;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.binding.ll_set.LangLabelsSet;

/**
 * Interface for Language Labels service
 * 
 */

public interface LangSetService {

  LangLabelsSet read(String name) throws WsSrvException;

  void save(String name, LangLabelsSet lls,
      String comment) throws WsSrvException;

}
