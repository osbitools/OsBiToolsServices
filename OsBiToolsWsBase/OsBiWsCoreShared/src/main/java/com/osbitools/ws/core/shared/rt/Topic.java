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

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Default and Project specific constants
 * 
 */

public class Topic {

  // List of subscribers
  private final Set<Consumer<DataMsg>> _subs =
      new HashSet<>();

  public synchronized void subscribe(Consumer<DataMsg> consumer) {
    _subs.add(consumer);
  }
  
  public synchronized void unSubscribe(Consumer<DataMsg> consumer) {
    _subs.remove(consumer);
  }

  public synchronized void publish(DataMsg data) {
    // Notify all consumers
    for (Consumer<DataMsg> consumer: _subs)
      consumer.accept(data);
  }
}
