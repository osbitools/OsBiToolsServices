/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2016-06-09
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.shared.base.common;

import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Web Configuration
 * 
 */

public abstract class AbstractWebConfiguration implements WebMvcConfigurer {

  private ObjectMapper _mapper;
  
  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    // Find MappingJackson2HttpMessageConverter
    for (HttpMessageConverter<?> conv : converters)
      if (conv.getClass().equals(MappingJackson2HttpMessageConverter.class)) {
        _mapper = ((MappingJackson2HttpMessageConverter) conv).
            getObjectMapper();
        
        // Set naming strategy
        _mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        
        // Ignore null fields when converting to JSON
        _mapper.setSerializationInclusion(Include.NON_NULL);
        
        break;
      }
  }
  
  protected void configure(SimpleModule module, Boolean minified) {
    _mapper.registerModule(module);
    
    if (minified)
      getJsonToHttpMapper().disable(SerializationFeature.INDENT_OUTPUT);
    else
      getJsonToHttpMapper().enable(SerializationFeature.INDENT_OUTPUT);
  }
  
  public ObjectMapper getJsonToHttpMapper() {
    return _mapper;
  }
}