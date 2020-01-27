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
 * 
 */

package com.osbitools.ws.shared.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import com.osbitools.ws.base.BaseUtils;

/**
 * Abstract Configuration bean
 * 
 */

public abstract class AbstractConfig implements Serializable {

  // Serial Version UID
  private static final long serialVersionUID = 1L;

  // Logger
  @Autowired
  private Logger log;

  @Autowired
  private ApplicationContext context;

  // Home directory with all configuration
  private String homeDir;

  // Full path for home directory
  private String _fdir;

  // Flag indicates that new config file written
  protected boolean _fnew;

  // Name of external file with saved properties
  private String fname;

  // Full path to configuration file
  private String _fpath;

  /**
   * Default constructor. Used when ws_cfg managed by Spring Boot and 
   * no external file required.
   */
  public AbstractConfig() {
  }

  /**
   * Constructor with file name only. Used for cases when configuration initialized via
   * Spring Context
   * 
   * @param fileName
   */
  public AbstractConfig(String fileName) {
    fname = fileName;
  }

  /**
   * Default constructor
   * 
   * @param fileName
   *          Name of property file to save
   */
  public AbstractConfig(String fileName, Logger logger) {
    this(fileName);

    log = logger;
  }

  public AbstractConfig(String fileName, String homeDir, Logger logger) {
    this(fileName, logger);

    setHomeDir(homeDir);
  }

  public abstract String getPrefix();

  protected List<String> getRestrictedParamList() {
    return null;
  }

  /**
   * Post construct bean when it initialized from servlet context
   * 
   * @throws SecurityException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   * 
   */
  public void readCtxHomeDir()
      throws NoSuchMethodException, SecurityException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    readCtxHomeDir(null);
  }

  /**
   * 
   * @param data
   *          Object with initial configuration data
   * 
   * @throws NoSuchMethodException
   * @throws SecurityException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   * 
   */
  public void readCtxHomeDir(Object data)
      throws NoSuchMethodException, SecurityException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {
    init(data);
  }

  /**
   * Check if home directory exists
   * 
   * @return True if exists and False otherwise
   * 
   * @throws IOException
   */
  public boolean checkHomeDir() throws IOException {
    Boolean res = BaseUtils.checkDirectory(_fdir);

    String[] dlist = getHomeSubDirList();
    if (dlist != null && dlist.length > 0)
      for (String sname : dlist)
        BaseUtils.checkDirectory(_fdir + File.separator + sname);

    return res;
  }

  /**
   * Return home directory as it set in configuration
   * 
   * @return home directory
   */
  public String getHomeDir() {
    return homeDir;
  }

  /**
   * Return full path for home directory
   * 
   * @return full path for home directory
   */
  public String getHomePath() {
    return _fdir;
  }

  public void init() {
    init(null);
  }

  public void init(Object data) {
    // Check home directory
    log.info("Using '" + _fdir + "' as configuration directory.");

    _fpath = _fdir + File.separator + fname;

    try {
      _fnew = !checkHomeDir() || !(new File(_fpath)).exists();
    } catch (IOException e) {
      String err = "Unable create configuration directory '" + _fdir + "'. " +
          e.getMessage();
      log.error(err);

      throw new RuntimeErrorException(new Error(err));
    }

    // Check if directory exists
    if (_fnew) {
      // Creating default configuration file
      log.info("Creating default configuration file " + "'" + _fpath + "'");
      try {
        // Before save check initial data supplied
        if (data != null)
          setConfigProperties(data);
        save("Default Parameters");
      } catch (IOException e) {
        log.error("Unable create configuration file '" + _fpath + "'. " +
            e.getMessage());
      } catch (Exception e) {
        String err = "Unable create configuration file " +
            "from the bean 'ws_cfg'. " + e.getMessage();
        log.error(err);
        throw new RuntimeErrorException(new Error(err));
      }
    } else {
      // Load configuration file
      log.info("Loading existing configuration file " + "'" + _fpath + "'");
      Properties props = new Properties();
      FileInputStream in = null;

      try {
        in = new FileInputStream(new File(_fpath));

        try {
          props.load(in);
          setConfigProperties(props);
        } catch (Exception e) {
          log.error("Unable load configuration from file '" + _fpath + "'. " +
              e.getMessage());
        }
      } catch (FileNotFoundException ex) {
        log.error(ex.getMessage());
      } finally {
        if (in != null) {
          try {
            in.close();
          } catch (IOException e) {
            // Do nothing
          }
        }
      }
    }

    log.info("Initialized " + getPrefix() + " config file with next values: " +
        toString());
  }

  public void save(String comment)
      throws IllegalArgumentException, IllegalAccessException, IOException {
    Properties props = readConfigProperties();

    FileOutputStream out = null;

    try {
      out = new FileOutputStream(new File(_fpath));
      props.store(out, comment);
    } catch (FileNotFoundException ex) {
      log.error(ex.getMessage());
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          // Do nothing
        }
      }
    }
  }

  public Properties readConfigProperties()
      throws IllegalArgumentException, IllegalAccessException {
    Properties res = new Properties();

    Class<?> clazz = this.getClass();
    while (!clazz.equals(Object.class)) {
      // Check for @PropertyItem annotated fields
      for (Field field : clazz.getDeclaredFields()) {
        // Ensure the RetentionPolicy of 'PropertyItem' is RUNTIME.
        if (field.isAnnotationPresent(PropertyItem.class)) {
          field.setAccessible(true);

          Object value = field.get(this);

          if (value != null)
            // Add to properties
            res.put(
                BaseUtils.getPropertyName(field.getName(),
                    field.getAnnotation(PropertyItem.class).delim()),
                value.toString());
        }
      }

      clazz = clazz.getSuperclass();
    }

    return res;
  }

  public void setConfigProperties(Object props) throws IllegalArgumentException,
      IllegalAccessException, SecurityException, InstantiationException,
      InvocationTargetException, NoSuchMethodException {

    Class<?> clazz = this.getClass();
    while (!clazz.equals(Object.class)) {
      // Check for @PropertyItem annotated fields
      for (Field field : clazz.getDeclaredFields()) {
        // Ensure the RetentionPolicy of 'PropertyItem' is RUNTIME.
        if (field.isAnnotationPresent(PropertyItem.class)) {
          field.setAccessible(true);

          BeanWrapper wrapper =
              PropertyAccessorFactory.forBeanPropertyAccess(props);
          Object value = wrapper.getPropertyValue(field.getName());
          setFieldValue(field, value);
        }
      }

      clazz = clazz.getSuperclass();
    }
  }

  /**
   * Initiate configuration properties with values from
   * 
   * @param props
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   * @throws NoSuchMethodException
   * @throws SecurityException
   * @throws InstantiationException
   * @throws InvocationTargetException
   */
  public void setConfigProperties(Properties props)
      throws IllegalArgumentException, IllegalAccessException,
      NoSuchMethodException, SecurityException, InstantiationException,
      InvocationTargetException {
    Class<?> clazz = this.getClass();
    while (!clazz.equals(Object.class)) {
      // Check for @PropertyItem annotated fields
      for (Field field : clazz.getDeclaredFields()) {
        // Ensure the RetentionPolicy of 'PropertyItem' is RUNTIME.
        if (field.isAnnotationPresent(PropertyItem.class)) {
          // Add to properties
          field.setAccessible(true);
          Object value =
              props.getProperty(BaseUtils.getPropertyName(field.getName(),
                  field.getAnnotation(PropertyItem.class).delim()));

          setFieldValue(field, value);
        }
      }

      clazz = clazz.getSuperclass();
    }
  }

  /**
   * Dynamically set field value directly
   * 
   * @param field
   *          Field to set
   * @param value
   *          Value to set
   * @throws SecurityException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   */
  private void setFieldValue(Field field, String value)
      throws NoSuchMethodException, SecurityException, IllegalArgumentException,
      IllegalAccessException, InstantiationException,
      InvocationTargetException {
    Constructor<?> co = field.getType().getConstructor(String.class);
    field.set(this, co.newInstance(value));
  }

  /**
   * Dynamically set field value using setter
   * 
   * @param field
   *          Field to set
   * @param value
   *          Value to set
   * @throws SecurityException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   */
  private void setFieldValue(Field field, Object value)
      throws IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException,
      InstantiationException {
    setFieldValue(field, value, null);
  }

  private boolean setFieldValue(Field field, Object value, Class<?> clazz)
      throws IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, NoSuchMethodException, SecurityException,
      InstantiationException {
    if (value != null) {
      Method m = BeanUtils.findMethod(this.getClass(),
          "set" + StringUtils.capitalize(field.getName()),
          new Class<?>[] { (clazz == null ? value.getClass() : clazz) });

      if (m != null) {
        m.invoke(this, value);
      } else {
        log.warn("Unable find setter with type " + value.getClass() +
            " for field '" + field.getName());

        // Try use interface if value class inherited any
        Class<?>[] interfaces = value.getClass().getInterfaces();

        if (interfaces.length != 0) {
          for (Class<?> ifs : interfaces)
            if (setFieldValue(field, value, ifs))
              break;

          return false;
        } else {
          log.warn("Unable find any implemented interfaces for type " +
              value.getClass() +
              " tryin find constructor to intialize field directly");

          // Edge scenario that needs to be avoided
          setFieldValue(field, value.toString());
        }
      }
    }

    return true;
  }

  /**
   * Find Bean property by name. Dynamically find and invoke getter
   * 
   * @param bean
   *          property name
   * @return property value
   * @throws Exception
   */
  public String getPropByName(String name) throws Exception {
    Method m = BeanUtils.findMethod(this.getClass(),
        BaseUtils.getBeanGetter(name), new Class<?>[] {});

    if (m == null)
      throw new Exception("Getter for property '" + name + " not found");

    return m.invoke(this, new Object[] {}).toString();
  }

  public boolean isNew() {
    return _fnew;
  }

  @Override
  public String toString() {
    return "home_dir=" + homeDir + ";";
  }

  public String[] getHomeSubDirList() {
    return null;
  }

  /**
   * Initialize configuration for standalone Microservice
   * Do not persist configuration file in the file.
   * Keep during app session in the memory.
   * This forces always load application.properties on start-up
   * 
   * @throws Exception
   */
  protected void initAppConfig() throws Exception {
    // Find configuration property bean by name
    String bname = getPrefix() + "_props";
    Object props = context.getBean(bname);

    if (props == null)
      throw new Exception("Unable find bean '" + bname + "'");

    setConfigProperties(props);

    log.info("Initialized " + getPrefix() + " application config file " +
        "with next values: " + toString());
  }

  public void setHomeDir(String homeDir) {
    this.homeDir = homeDir;

    // Adjust directory path
    _fdir = BaseUtils.checkSystemPath(homeDir);
  }

  public void setLogger(Logger log) {
    this.log = log;
  }

  public void setFileName(String fileName) {
    this.fname = fileName;
  }
}
