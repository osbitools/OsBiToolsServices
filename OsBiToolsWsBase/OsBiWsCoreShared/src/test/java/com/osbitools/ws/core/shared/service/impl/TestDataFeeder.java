/*
  * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2019-09-01
 * 
 * Contributors:
 *
 */

package com.osbitools.ws.core.shared.service.impl;

import com.osbitools.ws.core.shared.rt.RtBasket;
import com.osbitools.ws.shared.common.GenericTestUtils;

/**
 * Test Data Feeder
 *
 */
public class TestDataFeeder implements Runnable {

  private boolean _go = true;

  private RtBasket[] _baskets;

  public TestDataFeeder(RtBasket[] baskets) {
    _baskets = baskets;
  }

  @Override
  public void run() {
    int cnt = 0;
    while (_go) {
      for (RtBasket basket : _baskets)
        basket.process(cnt);

      cnt++;

      GenericTestUtils.sleep(10);
    }
  }

  public void stop() {
    _go = false;
  }
}
