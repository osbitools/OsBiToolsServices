/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-01-01
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.service;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.shared.base.dto.ColumnListDto;

/**
 * Interface for SQL Info Service
 * 
 */

public interface SqlInfoSevice {

  /**
   * Retrieve list of columns with Java types from given sql.
   * 
   * @param params Map with parameters
   * @return Array with Column Objects
   * 
   * @throws WsSrvException
   */
  ColumnListDto getColumnList(String sql)
      throws WsSrvException;

}
