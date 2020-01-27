/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the GPL v3 or higher
 * See http://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * Date: 2016-27-05
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.shared.common;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.junit.Test;

import com.osbitools.ws.shared.Constants;
import com.osbitools.ws.shared.ExtListFileFilter;
import com.osbitools.ws.base.BaseUtils;
import com.osbitools.ws.shared.GenericUtils;
import com.osbitools.ws.base.WsSrvException;

/**
 * Test differen ID combinations
 * 
 */
public class GenericTest {

  public static final String[] BAD_ID_LIST = new String[] { "`", "!", "@", "#", "$", "%", "^",
      "&", "(", ")", "+", "=", "[", "]", "{", "}", "\"", "'" };

  public static final String[] BAD_ID_NAMES = new String[] { "@#~~&", "#*@``", "=&*$_",
      "($#~%", "%^=(_", "$`~`$", "#+_+(", "%!(^~", ")(%+_", "@`^()", "^_##_", "(+^=%", "=%^(_",
      "))=*%", "_#@`&", "*%=_%", "_*@((", "%%%##", "#((++", ")-^#=", ")%@+_", "*`(*+", "$!&_%",
      "##$&@", "-*(~(", "@)#!@", "~-=!-", "=^!+#", "=+_^+", "-=&+*", "*(_(-", "($~#-", "_=!%+",
      "*-#-^", "()&@!", "(+^*_", "_$_**", "_@)-#", "~~%%*", "~@!!(", "`#&=)", "*~=(!", "_*@_^",
      "!^%`%", "$)*_#", "``!-#", "-)_=^", "!@#_+", "@$#$=", ")~*(!", "_&(_*", "%(^)_", "))_=_",
      "*)+*_", "+$@-$", "&#_++", "^$+#)", "`&-@(", "@!-!+", "(=_$$", "^+^^~", "_^%~_", "%%&$@",
      ")~)%+", "(-=_+", "$^`(&", "&~~+@", "&++^@", "%%_*=", "^_-=)", "%(-_)", "~`^!_", "%*)+$",
      "$`@!&", "_%=@!", "+`&#@", "*~#$)", "$_*!=", "%_)@+", "#(&_-", "(!&=)", "#*&-^", "@_^=$",
      "^*%#)", "@)~#*", "=$@(`", "$=*#`", "_(##^", "^^@(+", "+$&@%", "$*$)&", "-`*+#", "*#~!`",
      "**`(&", "_$`+$", "#-#^*", "****`", "##@-=", "!@=%@", "=$%=$" };

  public static final String[] GOOD_ID_LIST = new String[] { "qWeRtY", "qq_QQ", "qq__QQ",
      "_q_", "_qq", "_qQ", "_qw", "_qW", "_Q_", "_Qq", "_QQ", "_Qw", "_QW", "_w_", "_wq",
      "_wQ", "_ww", "_wW", "_W_", "_Wq", "_WQ", "_Ww", "_WW", "q__", "q_q", "q_Q", "q_w",
      "q_W", "qq_", "qqq", "qqQ", "qqw", "qqW", "qQ_", "qQq", "qQQ", "qQw", "qQW", "qw_",
      "qwq", "qwQ", "qww", "qwW", "qW_", "qWq", "qWQ", "qWw", "qWW", "Q__", "Q_q", "Q_Q",
      "Q_w", "Q_W", "Qq_", "Qqq", "QqQ", "Qqw", "QqW", "QQ_", "QQq", "QQQ", "QQw", "QQW",
      "Qw_", "Qwq", "QwQ", "Qww", "QwW", "QW_", "QWq", "QWQ", "QWw", "QWW", "w__", "w_q",
      "w_Q", "w_w", "w_W", "wq_", "wqq", "wqQ", "wqw", "wqW", "wQ_", "wQq", "wQQ", "wQw",
      "wQW", "ww_", "wwq", "wwQ", "www", "wwW", "wW_", "wWq", "wWQ", "wWw", "wWW", "W__",
      "W_q", "W_Q", "W_w", "W_W", "Wq_", "Wqq", "WqQ", "Wqw", "WqW", "WQ_", "WQq", "WQQ",
      "WQw", "WQW", "Ww_", "Wwq", "WwQ", "Www", "WwW", "WW_", "WWq", "WWQ", "WWw", "WWW",
      "Qw-4", "_", "-", "A", "b", "5", "5-5", "Qwerty_Zxcvb-1"};

  public static final String[][] SETTER_NAMES =
      new String[][] { new String[] { "test.one", "setTestOne" },
          new String[] { "test.one_two", "setTestOneTwo" },
          new String[] { "param1", "setParam1" } };

  public static final String[][] PROP_NAMES =
      new String[][] { new String[] { "testOne", "test.one" },
          new String[] { "testOneTwo", "test.one_two" }, new String[] { "param1", "param1" } };

  @Test
  public void testBadId() {
    for (String id : BAD_ID_LIST)
      assertEquals("ID: " + id, false, Constants.ID_PATTERN.matcher(id).matches());
  }

  @Test
  public void testGoodId() {
    for (String id : GOOD_ID_LIST)
      assertEquals("ID: " + id, true, Constants.ID_PATTERN.matcher(id).matches());
  }

  @Test
  public void testFileDir() throws WsSrvException {
    assertEquals("test",
        GenericUtils.getFileDir(TestConstants.SRC_DIR, "test.config").getName());
    assertEquals("src", GenericUtils.getFileDir(TestConstants.SRC_DIR, "test").getName());
  }

  @Test
  public void testExtListFilter() {
    ExtListFileFilter filter = new ExtListFileFilter(new String[] { "a", "bb", "ccc" });

    assertTrue(filter.accept(new File("."), "a.a"));
    assertTrue(filter.accept(new File("."), "b.bb"));
    assertTrue(filter.accept(new File("."), "c.ccc"));
    assertTrue(filter.accept(new File("."), "qq.a"));

    assertFalse(filter.accept(new File("."), ".a"));
    assertFalse(filter.accept(new File("."), "x.abc"));
    assertFalse(filter.accept(new File("."), "y.bbc"));
    assertFalse(filter.accept(new File("."), ".ccc"));
    assertFalse(filter.accept(new File("."), "123"));

    filter = new ExtListFileFilter(new String[] { "aaa", "bbb" });
    assertTrue(filter.accept(new File("."), "x.aaa"));
    assertTrue(filter.accept(new File("."), "yy.aaa"));
    assertTrue(filter.accept(new File("."), "qq.bbb"));
    assertTrue(filter.accept(new File("."), "zzz.aaa"));

    assertFalse(filter.accept(new File("."), "ww.ccc"));
    assertFalse(filter.accept(new File("."), ".aaa"));
    assertFalse(filter.accept(new File("."), ".aaaa"));
  }

  @Test
  public void testBeanSetterNames() {
    for (String[] name : SETTER_NAMES)
      assertEquals(name[1], BaseUtils.getBeanSetter(name[0]));
  }

  @Test
  public void testPropNames() {
    for (String[] name : PROP_NAMES)
      assertEquals(name[1], BaseUtils.getPropertyName(name[0], "."));
  }

  @Test
  public void testLocaleLang() {
    assertEquals(Locale.US.getLanguage(), "en");
    assertEquals(Locale.CANADA.getLanguage(), "en");
    assertEquals(Locale.CANADA_FRENCH.getLanguage(), "fr");
    assertEquals(new Locale("ru", "RU").getLanguage(), "ru");
    assertEquals(new Locale("es", "ES").getLanguage(), "es");
    assertEquals(new Locale("de", "DE").getLanguage(), "de");
  }

  @Test
  public void testDirCopy() throws IOException, WsSrvException {
    // Check if target/test_src directory exists and recreate if not
    File src = new File(TestConstants.TARGET_PATH + "src");
    doCheckDeleteDir(src);

    if (!src.mkdir())
      fail("Unable create source directory '" + src.getAbsolutePath() + "'");

    // Same for dst dir
    File dst = new File(TestConstants.TARGET_PATH + "dst");

    // Create destination directory
    doCheckDeleteDir(dst);

    // Try copy source directory into non-existing destination
    GenericTestUtils.copyDirectory(src, dst);

    if (!dst.mkdir())
      fail("Unable create destination directory '" + dst.getAbsolutePath() + "'");

    // Try copy empty directory into empty one
    GenericTestUtils.copyDirectory(src, dst);

    // Create 2 test files
    final int TEST_FNUM = 2;
    for (int i = 1; i <= TEST_FNUM; i++)
      BaseUtils.copyToFile("test" + i, src.getAbsoluteFile() + File.separator + "test" + i);

    // Copy directory with files into destination
    GenericTestUtils.copyDirectory(src, dst);

    // Test both file exists
    for (int i = 1; i <= TEST_FNUM; i++)
      assertTrue("Destination file test" + i + " not found",
          new File(dst.getAbsoluteFile() + File.separator + "test" + i).exists());

    // Create sub-directory in source folder
    String sdir = src.getAbsolutePath() + File.separator + "test";

    assertTrue("Error creating sub-directory in source folder", new File(sdir).mkdir());

    // Copy source folder with empty sub-directory
    GenericTestUtils.copyDirectory(src, dst);

    // Check if destination sub-directory exist
    File sdest = new File(dst.getAbsolutePath() + File.separator + "test");
    assertTrue("Destination sub-folder test not found", sdest.exists());

    // Create files into sub-directory in source folder
    for (int i = TEST_FNUM + 1; i <= TEST_FNUM * 2; i++)
      BaseUtils.copyToFile("test" + i, sdir + File.separator + "test" + i);

    // Copy source folder with non-empty sub-directory
    GenericTestUtils.copyDirectory(src, dst);

    // Check destination files exists
    for (int i = TEST_FNUM + 1; i <= TEST_FNUM * 2; i++)
      assertTrue("Destination file test" + i + " not found",
          new File(sdest.getAbsolutePath() + File.separator + "test" + i).exists());

    // Clean up
    BaseUtils.delDirRecurse(src, true);
    BaseUtils.delDirRecurse(dst, true);
  }

  private void doCheckDeleteDir(File dir) throws IOException, WsSrvException {
    if (dir.exists()) {
      if (dir.isDirectory())
        BaseUtils.delDirRecurse(dir, true);
      else
        BaseUtils.deleteFile(dir);
    }

  }
}
