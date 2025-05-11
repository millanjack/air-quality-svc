package com.serjlemast.controller.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serjlemast.message.RaspberrySensorMessage;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class SensorDataWebSocketHandler extends TextWebSocketHandler {

  private final ObjectMapper objectMapper;

  private static final CopyOnWriteArrayList<WebSocketSession> sessions =
      new CopyOnWriteArrayList<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    sessions.add(session);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    sessions.remove(session);
  }

  public void sendSensorData(RaspberrySensorMessage message) throws Exception {
    var json = objectMapper.writeValueAsString(message);
    for (WebSocketSession session : sessions) {
      session.sendMessage(new TextMessage(json));
    }
  }
}
