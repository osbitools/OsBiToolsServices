/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-03-20
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.pd.shared.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.osbitools.ws.wp.shared.mapper.*;
import com.osbitools.ws.rest.pd.shared.config.PdAppWebConfig;
import com.osbitools.ws.rest.shared.base.common.AbstractWebConfiguration;
import com.osbitools.ws.wp.shared.binding.WebWidgets;

/**
 * Web Configuration for Page Designer mapper
 * 
 */

@EnableWebMvc
@Configuration
public class PdWebConfiguration extends AbstractWebConfiguration {

  @Autowired
  PdAppWebConfig _wcfg;
  
  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    super.extendMessageConverters(converters);

    SimpleModule module = new SimpleModule();
    module.addSerializer(WebWidgets.class, new WebWidgetsSerializer());
    module.addDeserializer(WebWidgets.class, new WebWidgetsDeSerializer());
    
    configure(module, _wcfg.getMinified());
  }
}