/*
 * Open Source Business Intelligence Tools - http://www.osgitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl.txt
 *
 * Date: 2018-05-16
 * 
 * Contributors:
 * 
 */

package com.osbitools.ws.rest.shared.base.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import com.osbitools.ws.rest.shared.base.it.WebResponse;

/**
 * Model for result of file download
 * 
 */
public class FileDownloadResponse extends WebResponse {
  
  private byte[] data;
  
  private Map<String, String> headers;
  

  public FileDownloadResponse(byte[] data) {
    super(200);
    this.data = data;
  }
  
  public FileDownloadResponse(HttpURLConnection conn) throws IOException {
    super(conn);
  }

  /**
   * @return the data
   */
  public byte[] getData() {
    return data;
  }


  /**
   * @return the headers
   */
  public Map<String, String> getHeaders() {
    return headers;
  }


  /**
   * @param headers the headers to set
   */
  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }
  
}
