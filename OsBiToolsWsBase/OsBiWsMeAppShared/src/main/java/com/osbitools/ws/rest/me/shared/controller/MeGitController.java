package com.osbitools.ws.rest.me.shared.controller;

import org.springframework.web.bind.annotation.RestController;

import com.osbitools.ws.rest.prj.rest.shared.controller.AbstractGitController;
import com.osbitools.ws.shared.binding.ds.DataSetDescr;

/**
 * Rest controller for Git service that processing DataSet Map Entity
 * 
 */

@RestController
public class MeGitController extends AbstractGitController<DataSetDescr> {

}
