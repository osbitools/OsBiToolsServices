/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-10-02
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.me.shared.common;

import org.springframework.stereotype.Component;

import com.osbitools.ws.base.ErrorList;

/**
 * List of Errors for OsBiTools Map Editor
 * Reserved error in range [300-349]
 * 
 */

@Component("me_err_list")
public class CustErrorList {

  static {
    ErrorList.addError(300, "Internal error processing request");
    ErrorList.addError(301, "Error processing info request");
    
    ErrorList.addError(302, "Internal error processing info request");
    ErrorList.addError(303, "Error processing info request");
    ErrorList.addError(304, "Error processing info request");
    ErrorList.addError(305, "Error processing info request");
    ErrorList.addError(306, "Error uploading file");
    
    /*
    
    ErrorList.addError(307, "Error processing info request");
    ErrorList.addError(308, "Error processing info request");
    ErrorList.addError(309, "Error processing info request");
    ErrorList.addError(310, "Error processing info request");
    ErrorList.addError(311, "Error processing info request");
    */
  }
}