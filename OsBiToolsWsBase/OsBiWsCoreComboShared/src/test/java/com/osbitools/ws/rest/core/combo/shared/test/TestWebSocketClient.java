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

package com.osbitools.ws.rest.core.combo.shared.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.slf4j.Logger;

@ClientEndpoint
public class TestWebSocketClient {

  private final Session _session;

  // Logger
  private final Logger _log;

  // Data Cache
  private String _cache;
  
  private int _status;

  private List<String> messages = new ArrayList<>();;

  public TestWebSocketClient(String host, Logger logger)
      throws DeploymentException, IOException, URISyntaxException {
    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    _log = logger;

    _session = container.connectToServer(this, new URI(host));

  }

  public void sendMessage(String msg) throws IOException {
    if (_status == 0)
      throw new IOException("Not connected.");
    _session.getBasicRemote().sendText(msg);
  }

  @OnOpen
  public void onOpen(Session session) {
    _status = 1;
    _log.info("Connected");
  }

  @OnClose
  public void onClose(Session session, CloseReason reason) {
    _status = 0;
    _log.info(
        "Closed " + reason.getCloseCode() + ":" + reason.getReasonPhrase());
  }

  @OnMessage
  public void onMessage(Session _session, String msg) {
    _log.trace("Message: " + msg);

    // First response is cache
    if (_cache == null)
      _cache = msg;
    else
      // Append message into the list
      messages.add(msg);
  }

  @OnError
  public void onError(Session session, Throwable err) {
    _log.error("Web Socket Error " + err.getClass().getName() + " - " +
        err.getMessage());
  }

  /**
   * @return the messages
   */
  public List<String> getMessages() {
    return messages;
  }

  /**
   * @return the cache
   */
  public String getCache() {
    return _cache;
  }
  
  public void disconnect() {
    try {
      _session.close();
    } catch (IOException e) {
      // Ignore error
    }
  }

  public boolean isConnected() {
    return _status != 0;
  }
}