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

package com.osbitools.ws.rest.me.shared.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.osbitools.ws.me.shared.mapper.*;
import com.osbitools.ws.rest.me.shared.config.MeAppWebConfig;
import com.osbitools.ws.rest.shared.base.common.AbstractWebConfiguration;
import com.osbitools.ws.shared.binding.ds.DataSetExtList;

/**
 * Web Configuration
 * 
 */

@EnableWebMvc
@Configuration
public class MeWebConfiguration extends AbstractWebConfiguration {

  @Autowired
  MeAppWebConfig _wcfg;

  @Override
  public void
      extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    super.extendMessageConverters(converters);

    SimpleModule module = new SimpleModule();
    module.addSerializer(DataSetExtList.class, new DataSetExtListSerializer());
    module.addDeserializer(DataSetExtList.class,
        new DataSetExtListDeSerializer());

    configure(module, _wcfg.getMinified());
  }
}