/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-11-09
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.common;

import com.osbitools.ws.base.ErrorList;

/**
 * List of Errors for OsBiTools Shared Project Manager Libraries
 * Reserved error in range [200-299]
 * 
 */
public class CustErrorList {

	static {
	  // TODO Need review
	  // Reserved 200-299
	  
	  // ErrorList.addError(200, "Error creating entity");
	  ErrorList.addError(201, "Error reading entity");
	  ErrorList.addError(202, "Error post-processing entity");
	  ErrorList.addError(203, "Error renaming project");
	  
	  // Project Utilities error
    ErrorList.addError(204, "Error creating Project");
    ErrorList.addError(205, "Error updating Project");
    ErrorList.addError(206, "Invalid project name");
    ErrorList.addError(207, "Unable delete Project Entity");
    ErrorList.addError(208, "Unable delete Project");
    ErrorList.addError(209, "Error creating Project directory tree");
    
    ErrorList.addError(223, "Error processing request");
    ErrorList.addError(225, "Entity invalid or corrupted");
    ErrorList.addError(226, "Internal Error processing Entity");
    
    ErrorList.addError(227, "Internal Error processing Entity");
    ErrorList.addError(228, "Internal Error processing Entity");
    ErrorList.addError(229, "Internal Error processing Entity");
    ErrorList.addError(230, "Internal Error processing Entity");
    
    ErrorList.addError(232, "Unsupported resource type");
    ErrorList.addError(234, "Unsupported resource type");
    
    ErrorList.addError(246, "Remote git repository is not configured");
    ErrorList.addError(247, "Remote git repository is not configured");
    
    ErrorList.addError(249, "Error push to remote git");
    ErrorList.addError(250, "Error pull from remote git");
    
    ErrorList.addError(252, "Request for this info type is not supported");
    ErrorList.addError(258, "Invalid file extension");
    
    ErrorList.addError(259, "Internal error processing request"); 
    
    ErrorList.addError(261, "Error reading language labels");
    ErrorList.addError(262, "Error reading language labels");
    ErrorList.addError(263, "Error reading language labels");
    ErrorList.addError(264, "Error reading language labels");
    ErrorList.addError(265, "Error saving file with custom language labels");
    
    ErrorList.addError(266, "Error dowloading file");
    ErrorList.addError(267, "Error dowloading file");
    
    ErrorList.addError(268, "Error uploading file");
    
    ErrorList.addError(290, "Error retrieving external file list");
    ErrorList.addError(291, "Error retrieving external file list");
    ErrorList.addError(292, "Error deleting external file");
    ErrorList.addError(293, "Error reading revision");
    ErrorList.addError(294, "Error reading file");
    ErrorList.addError(295, "Error reading file"); 
    ErrorList.addError(296, "Coud not recognize file type"); 
    ErrorList.addError(297, "Error updating file"); 
    ErrorList.addError(298, "Error updating file"); 
    ErrorList.addError(299, "Error updating file"); 
	  /*
	  ErrorList.addError(200, "Error processing uploading request");
	  ErrorList.addError(201, "Error processing uploading request");
	  ErrorList.addError(202, "Error processing uploading request");
	  ErrorList.addError(203, "Error processing request");
	  
		
	  ,
	                                      HttpStatus.BAD_REQUEST); // 400
    
    
    ErrorList.addError(208, "Internal Error processing Entity");
    ErrorList.addError(209, "Error reading Entity");
    
    ErrorList.addError(221, "Can't rename to itself",
                                    HttpStatus.UNPROCESSABLE_ENTITY); // 422
    ErrorList.addError(222, "Unable delete Project");
    ErrorList.addError(223, "Error processing request");
    ErrorList.addError(224, "Entity invalid or corrupted");
    ErrorList.addError(225, "Entity invalid or corrupted");
    ErrorList.addError(226, "Internal Error processing Entity");
    ErrorList.addError(227, "Internal Error processing Entity");
    ErrorList.addError(228, "Can not rename file to itself");
    ErrorList.addError(229, "Error renaming file");
    ErrorList.addError(230, "Error renaming file",
                                      HttpStatus.UNPROCESSABLE_ENTITY); // 422
    ErrorList.addError(231, "Error saving file");
    
    ErrorList.addError(233, "Empty Entity");
    
    ErrorList.addError(235, "Error renaming file",
                                    HttpStatus.UNPROCESSABLE_ENTITY); // 422
    ErrorList.addError(236, "Can not rename file to itself");
    ErrorList.addError(237, "Error renaming file");
    ErrorList.addError(238, "Unable delete file");
    ErrorList.addError(239, "Error commit file");
    ErrorList.addError(240, "Error commit file");
    ErrorList.addError(241, "Error read commit log");
    ErrorList.addError(242, "Error read saved revision");
    ErrorList.addError(243, "Unable find given revision");
    ErrorList.addError(244, "Unable find file in given revision");
    ErrorList.addError(245, "Error processing git repository");
    
    ErrorList.addError(248, "Invalid remote operation",
                                      HttpStatus.UNPROCESSABLE_ENTITY); // 422
    
    ErrorList.addError(253, "Error retrieving CSV File Info");
    ErrorList.addError(254, "Error retrieving CSV Columns");
    ErrorList.addError(255, "Error processing uploading request");
    ErrorList.addError(256, "Error processing uploading request");
    ErrorList.addError(257, "Error processing uploading request");
    
    ErrorList.addError(260, "Error getting Git Diff");
    
    
    
    */
		// ErrorList.addError(, "", 0);
	}
}
