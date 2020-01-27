/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-26-05
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared;

/**
 * Interface for service error code. It depends of layer and could be HTTP Error code or 
 *  SQL Error code or any other.
 *  
 */
public interface IServiceErrorCode {

  /**
   * Get Service Error Code
   * 
   * @param errCode Error code
   * @return Service Error Code
   */
  int getServiceErrorCode(int errCode);
}
