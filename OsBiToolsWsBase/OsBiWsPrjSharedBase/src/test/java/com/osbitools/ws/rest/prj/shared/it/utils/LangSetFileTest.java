/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-11-09
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.rest.prj.shared.it.utils;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.rest.prj.shared.common.CustErrorList;
import com.osbitools.ws.rest.prj.shared.utils.LangSetUtils;
import com.osbitools.ws.shared.*;
import com.osbitools.ws.shared.binding.ll_set.LangLabelsSet;
import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.shared.common.JarUtils;

/**
 * Test lang_set file
 * 
 */
public class LangSetFileTest {

  // Pointer on LangUtils
  private static LangSetUtils _utils;

  // List of supported locales
  private static  String ls = "";
  
  static {
    // Combine list of supported locales
    
    for (String lang: CommonConstants.LOCALES.keySet())
      ls += "," + lang;
  }
  // JSON template for demo ll_set
  public static final String LL_SET_JSON = "{\"lang_label\":[" +
      // @formatter:off
      "{" +
        "\"ll_def\":[" +
          "{\"lang\":\"en\",\"value\":\"Username\"}," +
          "{\"lang\":\"fr\",\"value\":\"Nom d'utilisateur\"}," +
          "{\"lang\":\"ru\",\"value\":\"Имя\"}," +
          "{\"lang\":\"es\",\"value\":\"Nombre de usuario\"}," +
          "{\"lang\":\"de\",\"value\":\"Nutzername\"}]," +
        "\"id\":\"LL_USERNAME\"}," +
      "{" +
        "\"ll_def\":[" +
          "{\"lang\":\"en\",\"value\":\"Password\"}," +
          "{\"lang\":\"fr\",\"value\":\"Mot de passe\"}," +
          "{\"lang\":\"ru\",\"value\":\"Пароль\"}," +
          "{\"lang\":\"es\",\"value\":\"Contraseña\"}," +
          "{\"lang\":\"de\",\"value\":\"Passwort\"}]," +
        "\"id\":\"LL_PASSWORD\"}]," +
      "\"lang_list\":\"" + ls.substring(1) + "\"," +
      "\"ver_max\":1," +
      "\"ver_min\":0}";
      // @formatter:on

  public static final String LL_SET_TEST_JSON = "{\"lang_label\":[" +
      // @formatter:off
      "{" +
        "\"ll_def\":[" +
          "{\"lang\":\"en\",\"value\":\"Let's go\"}," +
          "{\"lang\":\"fr\",\"value\":\"Allons-y\"}," +
          "{\"lang\":\"ru\",\"value\":\"Поехали\"}," +
          "{\"lang\":\"es\",\"value\":\"Vamonos\"}," +
          "{\"lang\":\"de\",\"value\":\"Lass uns gehen\"}]," +
        "\"id\":\"LL_LETS_GO\"}]," +
      "\"lang_list\":\"" + ls.substring(1) + "\"," +
      "\"ver_max\":1," +
      "\"ver_min\":0}";
      // @formatter:on
  
  @BeforeClass
  public static void instUtils() throws Exception {
    _utils = new LangSetUtils();
    Class.forName(CustErrorList.class.getName());
  }

  @Test
  public void test() throws IOException, WsSrvException, JAXBException {
    testLangSetFile(LsTestConstants.LANG_SET_RES_PATH, LsConstants.LANG_SET_FILE, LL_SET_JSON);
    testLangSetFile(LsTestConstants.LANG_SET_RES_PATH + "_test",
        LsConstants.LANG_SET_FILE + "_test", LL_SET_TEST_JSON);
  }

  // Read lang_set file from Jar File & convert into JSON
  private void testLangSetFile(String path, String fname, String expected)
      throws WsSrvException, IOException, JAXBException {
    ObjectMapper mapper = new ObjectMapper();

    mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCaseStrategy.SNAKE_CASE);
    LangLabelsSet entity = _utils.readEntity(JarUtils.readJarFileAsStream(path));
    String actual = mapper.writeValueAsString(entity);

    assertEquals(expected, actual);
  }
}
