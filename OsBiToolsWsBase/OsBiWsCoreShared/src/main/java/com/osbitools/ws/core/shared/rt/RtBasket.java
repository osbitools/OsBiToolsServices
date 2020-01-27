/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2019-08-30
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.rt;

import java.time.LocalDateTime;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

import com.osbitools.ws.core.shared.rt.Topic;

/**
 * Real Time basket
 *
 */
public class RtBasket {
  private final Queue<DataMsg> _cache;
  
  private final Topic _topic;
  
  // Real time synchronization lock
  private final Lock _lock = new ReentrantLock();
 
  public RtBasket(int size) {
    _cache = new ArrayBlockingQueue<DataMsg>(size);
    _topic = new Topic();
  }

  /**
   * @return the topic
   */
  public Topic getTopic() {
    return _topic;
  }
  
  /**
   * Add value to the cache and publish into the topic
   * 
   * @param value Value to process
   */
  public void process(Object value) {
    _lock.lock();
    DataMsg msg = new DataMsg(LocalDateTime.now().toString(), value);
    
    try {
      _cache.add(msg);
    } catch (IllegalStateException e) {
      // Queue is full
      _cache.remove();
      
      // Repeat
      _cache.add(msg);
    }
    
    _topic.publish(msg);
    _lock.unlock();
  }

  public DataMsg[] pull() {
    return pull(null);
  }
  
  public DataMsg[] pull(Consumer<DataMsg> consumer) {
    _lock.lock();
    // Clone cache
    DataMsg[] result = new DataMsg[_cache.size()];
    _cache.toArray(result);
    if (consumer != null)
      _topic.subscribe(consumer);
    _lock.unlock();
    
    return result;
  }
}