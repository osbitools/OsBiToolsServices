/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2016-26-05
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared;

import com.osbitools.ws.base.ErrorList;

/**
 * List of Errors for Shared Libraries
 * Reserved error in range [00-99]
 * 
 */
public class CustErrorList extends ErrorList {

	static {
	  
	  // General Project access errors
    addError(1, "Entry not found");
    addError(2, "Entry already exists");
    addError(3, "Invalid project structure");
    addError(4, "Invalid Entry name");
    
    // File name validation
    addError(5, "Invalid File Name");
    addError(6, "Invalid File Name");
    addError(7, "Invalid File Extension");
    addError(8, "Invalid File Extension");
    addError(9, "Invalid File Name");
    addError(10, "Invalid OsBiTools Project");
    
    // File Utilities
    addError(11, "Error creating file");
    addError(12, "Error creating file");
    addError(13, "Error creating file");
    addError(14, "File not found");
    addError(15, "Error creating ");
    addError(16, "Error reading file");
    addError(17, "Entry not found");
    addError(18, "Can not rename file to itself");
    addError(19, "Unable rename file");
    addError(20, "Unable deleting file");
    
		// addError(, "");
	}
}
