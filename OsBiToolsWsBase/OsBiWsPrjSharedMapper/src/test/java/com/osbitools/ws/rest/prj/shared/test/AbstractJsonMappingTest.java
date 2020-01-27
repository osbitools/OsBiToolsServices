package com.osbitools.ws.rest.prj.shared.test;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.osbitools.ws.shared.common.JarUtils;

/**
 * Test DataSetDescr mapping from JSON to Object
 * 
 */
public abstract class AbstractJsonMappingTest<T> {

  private Unmarshaller _um;

  protected final static ObjectMapper MAPPER = new ObjectMapper();

  static {
    // Basic mapper configuration
    MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    MAPPER.setSerializationInclusion(Include.NON_NULL);
  }

  private Class<T> clazz;
  
  public AbstractJsonMappingTest(Class<T> clazz) throws Exception {
    this.clazz = clazz;
    _um = JAXBContext.newInstance(getBindPkg()).createUnmarshaller();
  }

  protected abstract String getDemoMapJson();

  protected abstract String getBindPkg();
  
  protected abstract void checkDemoEntity(T entity);
  
  protected abstract String getMainDemoPath();
  
  @Test
  public void testDemoDataSetMapping() throws Exception {
    // Test forward mapping from XML -> JSON
    T entity = readMainDemoFromXml();
    checkDemoEntity(entity);
    String json = MAPPER.writeValueAsString(entity);
    assertEquals("Converting XML -> JSON doesn't match", getDemoMapJson(), json);

    // Test backward mapping JSON -> XML
    entity = MAPPER.readValue(getDemoMapJson(), clazz);
    
    checkDemoEntity(entity);
  }

  @SuppressWarnings("unchecked")
  protected T readMainDemoFromXml() throws Exception {
    InputStream in = JarUtils.readJarFileAsStream(getMainDemoPath());
    return ((JAXBElement<T>) _um.unmarshal(in)).getValue();
  }
}

