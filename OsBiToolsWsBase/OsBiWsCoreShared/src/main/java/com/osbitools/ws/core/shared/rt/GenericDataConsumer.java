/*
 * Open Source Business Intelligence Tools - http://www.osbitools.com/
 * 
 * Copyright 2014-2018 IvaLab Inc. and by respective contributors (see below).
 * 
 * Released under the LGPL v3 or higher
 * See http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Date: 2019-08-31
 * 
* Contributors:
 * - Vlad Pishikin <pvf70@mail.ru>
 */

package com.osbitools.ws.core.shared.rt;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import org.slf4j.Logger;

/**
 * Abstract Data Consumer
 * 
 */

public class GenericDataConsumer<T extends Consumer<DataMsg>>
    implements Consumer<DataMsg> {

  // Internal queue
  private final LinkedBlockingQueue<DataMsg> _queue =
      new LinkedBlockingQueue<>();

  // Reference on subscribed topic
  private final Topic _topic;

  // Logger
  private final Logger _log;

  // Message processor
  private final T _mproc;
  
  // Processing thread;
  private Thread _ph;

  private boolean _subscribed = true;

  public GenericDataConsumer(T msgProc, Topic topic,
      Logger logger) {
    _topic = topic;
    _mproc = msgProc;
    _log = logger;

    // Create  waiting thread but do not start
    _ph = new Thread(new DataProcessor(_queue, msgProc, _log));
  }

  public T getMsgProc() {
    return _mproc;
  }
  
  @Override
  public void accept(DataMsg msg) {
    // Put message into the queue
    try {
      _queue.put(msg);
    } catch (InterruptedException e) {
      // Unsubscribe
      _log.error("Consuer interrupted and unsubscribed.");

      this.unSubscribe();
    }
  }

  /**
   * @param ready the ready to set
   */
  public void setReady() {
    // Start processing thread
    _ph.start();
  }

  public void unSubscribe() {
    // Stop processing thread
    _ph.interrupt();

    // Unsubscribe from topic
    _topic.unSubscribe(this);
    
    _subscribed = false;
  }
  
  public boolean isSubscribed() {
    return _subscribed;
  }

}

class DataProcessor implements Runnable {

  // Queue to process
  private final BlockingQueue<DataMsg> _queue;

  // Message processor
  private final Consumer<DataMsg> _mproc;

  // Logger
  private final Logger _log;

  public DataProcessor(BlockingQueue<DataMsg> queue,
      Consumer<DataMsg> processor, Logger logger) {
    _queue = queue;
    _mproc = processor;
    _log = logger;
  }

  public void run() {
    while (true) {
      try {
        DataMsg msg = _queue.take();
        _log.trace("Outgoing Message: " + msg);
        _mproc.accept(msg);
      } catch (InterruptedException e) {
        break;
      }
    }
  }
}
