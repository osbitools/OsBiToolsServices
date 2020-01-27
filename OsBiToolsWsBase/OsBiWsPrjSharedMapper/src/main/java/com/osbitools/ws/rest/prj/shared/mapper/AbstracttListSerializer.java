/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2018-03-10
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.mapper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.osbitools.ws.base.BaseUtils;

/**
 * JSON Serializer for DataSetExtList class
 * 
 */

public abstract class AbstracttListSerializer<T1, T2> extends StdSerializer<T1> {

  // Default Serial Version UID
  private static final long serialVersionUID = 1L;

  // private static final int SUFFIX_LEN = "DataSetDescr".length();

  protected abstract String getListFieldName();

  protected abstract String getFieldName(T2 item);

  public AbstracttListSerializer(Class<T1> t) {
    super(t, true);
  }

  protected AbstracttListSerializer(Class<T1> t, boolean dummy) {
    super(t, dummy);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void serialize(T1 value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    // Using getter to read value
    String mname = "get" + BaseUtils.ucFirstChar(getListFieldName());

    Method m;
    try {
      m = handledType().getMethod(mname);
    } catch (NoSuchMethodException | SecurityException e) {
      throw new IOException("Method name [" + mname + "] not found in class " +
          handledType().getClass().getName() + " - " + e.getMessage());
    }

    Object o;
    try {
      o = m.invoke(value, new Object[] {});
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new IOException("Error invoking method [" + mname + "] from class " +
          handledType().getClass().getName() + " - " + e.getMessage());
    }

    List<T2> list;
    if (o instanceof List<?>)
      list = (List<T2>) o;
    else
      throw new IOException(
          "Expected List type from [" + mname + "] but returned " + o.getClass().getName());

    gen.writeStartArray();
    for (T2 item : list) {

      gen.writeStartObject();
      gen.writeObjectField(getFieldName(item), item);
      gen.writeEndObject();
    }
    gen.writeEndArray();
  }

}
