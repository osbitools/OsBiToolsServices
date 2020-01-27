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

package com.osbitools.ws.rest.core.combo.shared.web_socket;

import java.io.IOException;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osbitools.ws.core.shared.rt.DataMsg;

/**
 * WebSocket Configuration
 * 
 */

public class NetMessageProcessor implements Consumer<DataMsg> {

  private final WebSocketSession _session;

  private final Logger _log;

  private final ObjectMapper _mapper;

  public NetMessageProcessor(WebSocketSession session, ObjectMapper mapper,
      Logger log) {
    _session = session;
    _mapper = mapper;
    _log = log;
  }

  @Override
  public void accept(DataMsg msg) {
    try {
      _session.sendMessage(new TextMessage(_mapper.writeValueAsString(msg)));
    } catch (IOException e) {
      _log.error("Failed send message to client " + _session.getId());
    }
  }
}
