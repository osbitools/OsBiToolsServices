/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-03-10
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.mapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * JSON Deserializer for DataSetExtList class
 * 
 *
 */

public abstract class AbstractListDeSerializer<T>
    extends StdDeserializer<T> {

  private Class<T> _clazz;
  
  protected AbstractListDeSerializer(Class<T> t) {
    super(t);
    
    _clazz = t;
  }

  //Default Serial Version UID
  private static final long serialVersionUID = 1L;

  protected abstract String[] getListTypes();
  
  protected abstract String composeClassName(String listType);
  
  protected abstract String getListFieldName();
  
  protected abstract String getListElType(String listType);
  
  @Override
  public T deserialize(JsonParser parser, DeserializationContext ctx)
                          throws IOException, JsonProcessingException {

    T res;
    try {
      res = _clazz.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new IOException("Unable instantaniate class " + _clazz.getName() +
          " - " + e.getMessage());
    }
    
    List<Object> list = new ArrayList<>();
    JsonNode node = parser.getCodec().readTree(parser);

    for (JsonNode jn: node) {      
      for (String stype : getListTypes()) {
        // Test each DataSetExt type
        JsonNode json = jn.get(getListElType(stype));

        if (json != null) {
            Class<?> clazz;
            String cname = composeClassName(stype);
            try {
              clazz = Class.forName(cname);
            } catch (ClassNotFoundException e) {
              throw new IOException("Unable find class [" + cname +
                  "] -  " + e.getMessage());
            }

            // Parse tree recursively
            list.add(parser.getCodec().treeToValue(json, clazz));
        }
      }
    };

    // Since setter does not exists use reflection to assign list variable
    String fname = getListFieldName();
    try {
      Field f = _clazz.getDeclaredField(fname);
      f.setAccessible(true);
      f.set(res, list);
    } catch (NoSuchFieldException | SecurityException | IllegalArgumentException
        | IllegalAccessException e) {
      throw new IOException("Unable find field '" + fname +
          "' in class " + _clazz.getName());
    }

    return res;
  }
}