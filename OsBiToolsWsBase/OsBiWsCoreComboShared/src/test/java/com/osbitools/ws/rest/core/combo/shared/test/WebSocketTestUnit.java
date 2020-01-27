/*
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2019-08-31
 * 
 * Contributors:
 *
 */

package com.osbitools.ws.rest.core.combo.shared.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

import javax.websocket.DeploymentException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.core.shared.rt.DataMsg;
import com.osbitools.ws.shared.common.CommonConstants;
import com.osbitools.ws.shared.common.GenericTestUtils;

public abstract class WebSocketTestUnit {

  public final int DATAFEEDER_DELAY = 10; // msec

  // Test data feeder
  private static TestNetDataFeeder FEEDER;

  // Lock object
  private static final Object LOCK = new Object();
  
  protected abstract CoreWsConfig getCoreWsConfig();

  protected abstract TestRestTemplate getTestRestTemplate();

  protected abstract ObjectMapper getObjectMapper();

  protected abstract int getPort();
  
  protected abstract Logger getLogger();
  
  @Before
  public void init() {
    startDataFeeder();
  }

  @Test
  public void testWebSocketClient() throws WsSrvException {
    testWebSocketClientSingle();
  }

  @After
  public void clear() {
    // Stop data feeder
    FEEDER.stop();
  }

  public void startDataFeeder() {
    startDataFeeder(DATAFEEDER_DELAY);
  }

  public void startDataFeeder(int delay) {
    synchronized (LOCK) {
      // DataFeeder is singleton
      if (FEEDER == null) {
        FEEDER = new TestNetDataFeeder(getBasketNames(getCoreWsConfig()),
            getTestRestTemplate(), delay);
        
        new Thread(FEEDER).start();
      }
    }
  }

  protected void compareData(DataMsg prev, DataMsg data) {
    // Check sequential value
    if ((int) data.getValue() != ((int) prev.getValue()) + 1)
      fail("Data value " + data.getValue().toString() + " is not after " +
          prev.getValue().toString());

    if (!LocalDateTime.parse(data.getDate())
        .isAfter(LocalDateTime.parse(prev.getDate())))
      fail("Date " + data.getDate() + " is not after " + prev.getDate());

  }

  protected String[] getBasketNames(CoreWsConfig config) {
    // Check 2 test buskets configured
    List<String> baskets = config.getRtBaskets();
    assertNotNull("Real Time baskets is empty", baskets);
    assertEquals(2, baskets.size());

    String[] result = new String[2];
    baskets.toArray(result);
    return result;
  }

  public void testWebSocketClientSingle()
      throws WsSrvException {
    String[] names = getBasketNames(getCoreWsConfig());

    String host = "ws://localhost:" + getPort() + CommonConstants.BASE_URL + "/rt";

    TestWebSocketClient client = null;

    try {
      client = new TestWebSocketClient(host, getLogger());
    } catch (DeploymentException | URISyntaxException | IOException e) {
      fail("Connection to " + host + " failed - " + e.getMessage());
    }

    // Wait for connection status
    long dts = System.currentTimeMillis();
    while (System.currentTimeMillis() - dts < 5000) {
      if (client.isConnected())
        break;
      else
        GenericTestUtils.sleep(100);
    }

    if (!client.isConnected())
      fail("Unable connect after 5 sec of waiting");

    // Wait little bit
    GenericTestUtils.sleep((int) (Math.random() * 500) + 500);

    try {
      try {
        // Read random real time basket
        client.sendMessage(names[(int) (Math.random() * 2)]);
      } catch (IOException e) {
        fail("Failed send message to " + host + " - " + e.getMessage());
      }

      // Wait some time between 4-5 seconds
      GenericTestUtils.sleep((int) (Math.random() * 1000) + 4000);

    } finally {
      // Disconnect client
      client.disconnect();
    }

    // Check Data Feeder error
    assertNull("DataFeeder returned error", FEEDER.getError());

    // Analyze response
    String cache = client.getCache();
    String[] carr = cache.substring(1, cache.length() - 1).split("\\},\\{");

    // Check if cache sequential
    DataMsg prev = null;
    for (String entry : carr) {
      String record = (entry.charAt(0) != '{' ? "{" : "") + entry + "}";
      try {
        DataMsg data = getObjectMapper().readValue(record, DataMsg.class);
        if (prev == null) {
          prev = data;
          continue;
        }

        compareData(prev, data);
        prev = data;
      } catch (IOException e) {
        fail("Invalid data message: " + record);
      }
    }

    // Check messages
    for (String msg : client.getMessages()) {
      DataMsg data = null;
      try {
        data = getObjectMapper().readValue(msg, DataMsg.class);
      } catch (IOException e) {
        fail("Invalid data message: " + msg);
      }
      compareData(prev, data);
      prev = data;
    }
  }
}
