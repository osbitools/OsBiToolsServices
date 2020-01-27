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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osbitools.ws.base.WsSrvException;
import com.osbitools.ws.core.shared.config.CoreWsConfig;
import com.osbitools.ws.core.shared.rt.DataMsg;
import com.osbitools.ws.core.shared.rt.GenericDataConsumer;
import com.osbitools.ws.core.shared.rt.RtBasket;
import com.osbitools.ws.core.shared.service.RtBasketService;
import com.osbitools.ws.shared.common.CommonConstants;

/**
 * WebSocket Configuration
 * 
 */

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  @Autowired
  private CoreWsConfig _cfg;

  @Autowired
  private RtBasketService _bsrv;

  @Autowired
  private Logger _log;

  @Autowired
  private ObjectMapper _mapper;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    // Check if any backet configured
    List<String> names = _cfg.getRtBaskets();
    if (names == null || names.size() == 0)
      return;

    registry
        .addHandler(new RtClientHandler(_bsrv, _mapper, _log),
            CommonConstants.BASE_URL + "/rt")
        .setAllowedOrigins("http://localhost");
    ;
  }

  // @Bean
  public ServletServerContainerFactoryBean createWebSocketContainer() {
    ServletServerContainerFactoryBean container =
        new ServletServerContainerFactoryBean();
    container.setMaxTextMessageBufferSize(8192);
    container.setMaxBinaryMessageBufferSize(8192);
    return container;
  }

}

class RtClientHandler implements WebSocketHandler {

  // Real Time basket service
  private final RtBasketService _bsrv;

  // Logger
  private final Logger _log;

  // Cross reference list of WebSocket sessions and consumers
  private final Map<String, GenericDataConsumer<Consumer<DataMsg>>> _consumers =
      new HashMap<>();

  // ObjectMapper
  private final ObjectMapper _mapper;

  public RtClientHandler(RtBasketService service, ObjectMapper mapper,
      Logger logger) {
    _log = logger;
    _bsrv = service;
    _mapper = mapper;
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session)
      throws Exception {
    _log.info("Accepted WebSocket connection #" + session.getId() +
        " from client " + session.getRemoteAddress());
  }

  @Override
  public void handleMessage(WebSocketSession session,
      WebSocketMessage<?> message) throws Exception {
    // Check if backet exists
    RtBasket backet;

    // There is only one message from client 
    // to request cache from real time basket
    String name;
    if (message instanceof TextMessage) {
      name = ((TextMessage) message).getPayload();
    } else {
      _log.error("Received unknown cache request message of type " +
          message.getClass().getName());
      return;
    }

    try {
      backet = _bsrv.getBasket(name);
    } catch (WsSrvException e) {
      session.sendMessage(
          new TextMessage("ERROR:Real Time Basket [" + name + "] not found."));
      return;
    }

    GenericDataConsumer<Consumer<DataMsg>> consumer =
        new GenericDataConsumer<Consumer<DataMsg>>(
            new NetMessageProcessor(session, _mapper, _log), backet.getTopic(),
            _log);
    
    // Add consumer into local thread list to automatically unsubscribe
    // when thread terminated.
    _consumers.put(session.getId(), consumer);
    DataMsg[] data = backet.pull(consumer);

    // Map to json
    String json = _mapper.writeValueAsString(data);

    // Return back to client
    session.sendMessage(new TextMessage(json));
    
    // Release Mesagge processing
    consumer.setReady();
  }

  @Override
  public void handleTransportError(WebSocketSession session,
      Throwable exception) throws Exception {
    _log.error("WebSocket transport error " + exception.getClass().getName() +
        " - " + exception.getMessage());
    _log.info("Closed WebSocket connection #" + session.getId() +
        " from client " + session.getRemoteAddress());
    _consumers.get(session.getId()).unSubscribe();
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session,
      CloseStatus closeStatus) throws Exception {
    _log.info("Closed WebSocket connection #" + session.getId() +
        " with status code " + closeStatus.getCode() + ":" +
        closeStatus.getReason() + " from client " + session.getRemoteAddress());
    _consumers.get(session.getId()).unSubscribe();
  }

  @Override
  public boolean supportsPartialMessages() {
    return false;
  }

}
