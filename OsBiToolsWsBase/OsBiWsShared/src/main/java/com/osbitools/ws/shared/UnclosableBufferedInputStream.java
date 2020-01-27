/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2015-08-07
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.shared;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Wrapper around BufferedInputStream that cannot be closed i.e it can be reused
 * 
 */
public class UnclosableBufferedInputStream extends BufferedInputStream {

  public UnclosableBufferedInputStream(InputStream in) {
    super(in);
    super.mark(Integer.MAX_VALUE);
  }

  @Override
  public void close() throws IOException {
    super.reset();
  }
  
  public void done() throws IOException {
    super.close();
  }
}