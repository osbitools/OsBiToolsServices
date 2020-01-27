package com.osbitools.ws.rest.pd.shared.controller;

import org.springframework.web.bind.annotation.RestController;

import com.osbitools.ws.rest.prj.rest.shared.controller.AbstractGitController;
import com.osbitools.ws.wp.shared.binding.WebPage;

/**
 * Rest controller for Git service that processing Web Page Entity
 * 
 */

@RestController
public class PdGitController extends AbstractGitController<WebPage> {

}
