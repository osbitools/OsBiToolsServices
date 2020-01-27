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

package com.osbitools.ws.base;

import java.util.HashMap;

/**
 * List of Errors for Shared Libraries
 * Reserved error in range [00-99]
 * 
 */
public class ErrorList {
   
	private static final HashMap<Integer, String> _map = 
				                            new HashMap<Integer, String>();

	public static String getErrorById(int id) {
		String info = _map.get(id);
		return (info == null ) ? "NULL" : info;
	}
	
	public static void addError(int id, String msg) {
	  _map.put(id, msg);
	}
}
