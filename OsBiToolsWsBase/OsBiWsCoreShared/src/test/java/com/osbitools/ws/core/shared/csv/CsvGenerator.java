/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2014-11-07
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.osbitools.ws.core.shared.common.CoreSharedTestConstants;

/**
 * Generate files for test purposes in test config csv directory
 * 
 */
public class CsvGenerator {

  private static final String CSV_DIR = CoreSharedTestConstants.WORK_CSV_DIR + File.separator;
  private static final String FNAME1 = "temp1.csv";
  private static final String FNAME2 = "temp2.csv";

  private static final int REC_NUM = 100;
  private static final int RAND_RANGE = REC_NUM / 10;

  private static final int WORD_LEN = 3;

  private static final char[][] alphabets =
      new char[][] { "abcdefghijklmnopqrstuvwxyz".toCharArray(),
          "абвгдежзклмнопрстуфхцчшщэюя".toCharArray() };

  public static void main(String[] args) throws IOException {
    File d = new File(CoreSharedTestConstants.WORK_CSV_DIR);

    if (!d.exists() && !d.mkdirs()) {
      System.err.println("Unable create " + CoreSharedTestConstants.WORK_CSV_DIR + " directory.");
      System.exit(1);
    }

    // genSort1();
    // genSort2();
    getSql();
  }

  public static void genSort1() throws IOException {
    // Generate sort1.csv with 2 columns.
    // First column is random 3 letters localized value and second
    // is random number in range <number of records>/10

    String fname = CSV_DIR + FNAME1;

    FileWriter out = new FileWriter(fname, false);

    out.write("CSTR,CINT,CNUM,CDATE\n");

    for (int i = 0; i < REC_NUM; i++) {
      char[] word = genRandWord();

      out.write(new String(word) + "," + (int) (Math.random() * RAND_RANGE) + "," +
          Math.random() * RAND_RANGE + "," + ((int) (Math.random() * 12) + 1) + "/" +
          ((int) (Math.random() * 25) + 1) + "/" + (2000 + (int) (Math.random() * 12)) + "\n");
    }

    out.close();

    System.out.println(REC_NUM + " records successfully written to " + fname);
  }

  public static void genSort2() throws IOException {
    String fname = CSV_DIR + FNAME2;

    // Generate 4 random words
    int rw_len = 4;
    char[][] rwords = new char[rw_len][];
    for (int i = 0; i < rw_len; i++)
      rwords[i] = genRandWord();

    FileWriter out = new FileWriter(fname, false);

    out.write("CSTR\n");

    for (int i = 0; i < REC_NUM; i++) {
      char[] word = rwords[(int) (Math.random() * rw_len)];

      out.write(new String(word) + "\n");
    }

    out.close();

    System.out.println(REC_NUM + " records successfully written to " + fname);
  }

  static void getSql() throws IOException {
    String fname = CSV_DIR + FNAME1 + ".sql";

    FileWriter out = new FileWriter(fname, false);

    for (int i = 0; i < REC_NUM; i++) {
      char[] word = genRandWord();

      out.write("INSERT INTO TEST_DATA(CSTR,CINT,CNUM,CDATE) VALUES('" + new String(word) +
          "'," + (int) (Math.random() * RAND_RANGE) + "," + Math.random() * RAND_RANGE + ",'" +
          ((2000 + (int) (Math.random() * 12) + "-" + ((int) (Math.random() * 12) + 1)) + "-" +
              ((int) (Math.random() * 25) + 1)) +
          "');\n");
    }

    out.close();

    System.out.println(REC_NUM + " records successfully written to " + fname);
  }

  static char[] genRandWord() {
    int anum = alphabets.length;
    // Pick alphabet
    char[] alphabet = alphabets[(int) (Math.random() * anum)];

    char[] word = new char[WORD_LEN];
    for (int j = 0; j < WORD_LEN; j++) {
      char letter = alphabet[(int) (Math.random() * anum)];

      // pick case
      if (Math.random() * 2 < 1)
        letter = Character.toLowerCase(letter);
      else
        letter = Character.toUpperCase(letter);

      word[j] = letter;
    }

    return word;
  }
}
