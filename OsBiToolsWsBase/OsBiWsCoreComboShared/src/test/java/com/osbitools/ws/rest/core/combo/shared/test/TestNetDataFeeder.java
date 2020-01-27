/*
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2019-09-05
 * 
 * Contributors:
 *
 */

package com.osbitools.ws.rest.core.combo.shared.test;


import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.shared.common.GenericTestUtils;
import com.osbitools.ws.shared.common.TestConstants;

/**
 * Test Data Feeder that using REST API
 *
 */
public class TestNetDataFeeder implements Runnable {

  private boolean _go = true;

  private final String[] _names;

  private final TestRestTemplate _rest;

  private final int _delay;

  private String _error;

  public TestNetDataFeeder(String[] names, TestRestTemplate template,
      int delay) {
    _names = names;
    _rest = template;
    _delay = delay;
  }

  @Override
  public void run() {
    int cnt = 0;
    while (_go) {

      for (String name : _names) {
        String url = CommonConstants.BASE_URL + "/pub/" + name + "/" + cnt;
        ResponseEntity<String> response =
            _rest.exchange(url, HttpMethod.POST, null, String.class);
        TestConstants.LOG.trace(name + ":" + cnt);
        if (response.getStatusCode() != HttpStatus.OK) {
          _error = "Reseived HTTP Error " + response.getStatusCode() +
              " for URL " + url;
          break;
        }
      }
      cnt++;

      GenericTestUtils.sleep(_delay);
    }
  }

  public void stop() {
    _go = false;
  }

  public String getError() {
    return _error;
  }
}
