/*
  * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2019-08-30
 * 
 * Contributors:
 *
 */

package com.osbitools.ws.core.shared.service.impl;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.core.shared.TestCoreSharedAppConfig;
import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.core.shared.config.CoreWsConfigProperties;
import com.osbitools.ws.core.shared.rt.DataMsg;
import com.osbitools.ws.core.shared.rt.GenericDataConsumer;
import com.osbitools.ws.core.shared.rt.RtBasket;
import com.osbitools.ws.core.shared.service.RtBasketService;
import com.osbitools.ws.shared.common.GenericTestUtils;

@SpringBootTest(value = { "spring.config.name=test" })
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CoreWsConfigProperties.class,
    TestCoreSharedAppConfig.class, RtBasketServiceImpl.class })
public class RtBasketServiceImplTest {

  @Autowired
  private RtBasketService _rtbs;

  @Autowired
  private CoreWsConfig _cfg;

  @Autowired
  private Logger _log;

  // Number of test values
  private static final int TEST_VAL_NUM = 10;

  // Wait time betwenn publishing values
  private static final int WAIT_TIME = 500;

  // Number of data consumers
  private static final int CONSUMERS_NUM = 25;

  @Test
  public void testValidBaskets() throws WsSrvException {
    // Check 2 test buskets configured
    List<String> baskets = getBasketNames();

    for (String bname : baskets)
      assertTrue(bname.equals("demo") || bname.equals("test"));

    // Add record to demo and test baskets
    for (int i = 0; i < TEST_VAL_NUM; i++) {

      for (String bname : baskets)
        _rtbs.addValue(bname, "value_" + (i + 1));

      if (i != TEST_VAL_NUM - 1)
        // Wait
        GenericTestUtils.sleep(WAIT_TIME);
    }

    // Test result
    for (String bname : baskets) {
      DataMsg[] data = _rtbs.getBasket(bname).pull();

      Date prev = null;
      Integer idx = 1;
      for (DataMsg entry : data) {
        assertEquals("value_" + idx, entry.getValue().toString());

        // Check delay between values
        if (prev == null) {
          prev = Date.from(LocalDateTime.parse(entry.getDate())
              .atZone(ZoneId.systemDefault()).toInstant());
        } else {
          Date date = Date.from(LocalDateTime.parse(entry.getDate())
              .atZone(ZoneId.systemDefault()).toInstant());
          long diff = date.getTime() - prev.getTime();

          // Check date in rang +/- 5%
          assertTrue(diff + " not in bottom range", diff > WAIT_TIME * 0.95);
          assertTrue(diff + " not in top range", diff < WAIT_TIME * 1.05);

          prev = date;
        }

        idx++;
      }

      assertEquals(TEST_VAL_NUM, idx.intValue() - 1);
    }
  }

  @Test
  public void testInValidBaskets() {
    try {
      _rtbs.addValue("xxx", "");
    } catch (WsSrvException e) {
      assertEquals(140, e.getErrorCode());
    }
  }

  @Test
  public void testTopics() throws WsSrvException {
    List<String> bnames = getBasketNames();

    // Get baskets
    RtBasket[] baskets = new RtBasket[bnames.size()];

    for (int i = 0; i < bnames.size(); i++)
      baskets[i] = _rtbs.getBasket(bnames.get(i));

    // Start data feeder
    TestDataFeeder dfeeder = new TestDataFeeder(baskets);
    new Thread(dfeeder).start();

    // Publish some data
    GenericTestUtils.sleep(500);

    // Create multiple consumers
    List<GenericDataConsumer<MessageProcessor>> consumers = new ArrayList<>();

    // Get random basket

    for (int i = 0; i < CONSUMERS_NUM; i++) {
      RtBasket basket =
          _rtbs.getBasket(bnames.get((int) (Math.random() * 2)));
      consumers.add(new GenericDataConsumer<MessageProcessor>(
          new MessageProcessor(basket), basket.getTopic(), _log));

      GenericTestUtils.sleep(50);
    }

    // Randomly unsibscribe consumer and add more
    for (int i = 0; i < CONSUMERS_NUM; i++) {
      int idx = (int) (Math.random() * consumers.size());
      if (consumers.get(idx).isSubscribed())
        consumers.get(idx).unSubscribe();

      RtBasket basket =
          _rtbs.getBasket(bnames.get((int) (Math.random() + 0.5)));
      consumers.add(new GenericDataConsumer<MessageProcessor>(
          new MessageProcessor(basket), basket.getTopic(), _log));

      GenericTestUtils.sleep(50);
    }

    // TODO
    GenericTestUtils.sleep(2000);

    dfeeder.stop();

    // Analyze result
    for (int i = 0; i < CONSUMERS_NUM; i++) {
      GenericDataConsumer<MessageProcessor> consumer = consumers.get(i);
      assertNull("DataConsumer " + i + " error ",
          consumer.getMsgProc().getError());
    }
  }

  private List<String> getBasketNames() {
    // Check 2 test buskets configured
    List<String> baskets = _cfg.getRtBaskets();
    assertNotNull("Real Time baskets is empty", baskets);
    assertEquals(2, baskets.size());

    return baskets;
  }
}

class MessageProcessor implements Consumer<DataMsg> {

  private final DataMsg[] _cache;

  private Integer _prev;

  private String error;

  public MessageProcessor(RtBasket basket) {
    _cache = basket.pull(this);
  }

  @Override
  public void accept(DataMsg msg) {
    // Quick check
    if (error != null)
      return;

    int data = (int) msg.getValue();
    if (_prev == null) {
      int cval = (int) _cache[_cache.length - 1].getValue();
      // Check new value is following cache last value
      if (cval != data - 1) {
        error = "Received value " + data + " doesn't follow cache last value " +
            cval;
        return;
      }

      _prev = data;
    } else {
      // Check new value is following previous value
      if (_prev != data - 1) {
        error = "Received value " + data + " doesn't follow previous value " +
            _prev;
        return;
      }

      _prev = data;

    }
  }

  /**
   * @return the error
   */
  public String getError() {
    return error;
  }

}