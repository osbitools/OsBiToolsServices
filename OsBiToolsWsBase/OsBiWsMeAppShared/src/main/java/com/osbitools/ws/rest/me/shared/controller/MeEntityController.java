/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-01-01
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.me.shared.controller;

import org.springframework.web.bind.annotation.RestController;

import com.osbitools.ws.rest.prj.rest.shared.controller.AbstractEntityController;
import com.osbitools.ws.shared.binding.ds.DataSetDescr;

/**
 * Rest controller for DataSet Map Entity
 * 
 */

@RestController
public class MeEntityController extends AbstractEntityController<DataSetDescr> {

}
