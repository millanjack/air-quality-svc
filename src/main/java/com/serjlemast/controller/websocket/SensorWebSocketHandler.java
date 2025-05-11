package com.serjlemast.controller.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serjlemast.message.RaspberrySensorMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
public class SensorWebSocketHandler extends TextWebSocketHandler {

  private final ObjectMapper objectMapper;

  private final List<WebSocketSession> sessions;

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    log.info("WebSocket connection established - session: {}", session);
    sessions.add(session);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    log.info("WebSocket connection closed - status: {}", status);
    sessions.remove(session);
  }

  public void sendSensorData(RaspberrySensorMessage message) {
    if (sessions.isEmpty()) {
      return;
    }
    var textMessage = convertToTextMessage(message);
    for (WebSocketSession session : sessions) {
      sendMessage(session, textMessage);
    }
  }

  private void sendMessage(WebSocketSession session, TextMessage textMessage) {
    try {
      log.info("Sending websocket message: {}", textMessage.getPayload());
      session.sendMessage(textMessage);
    } catch (Exception e) {
      throw new WebSocketHandlerException(
          "Error processing websocket message: '%s'".formatted(textMessage.getPayload()), e);
    }
  }

  private TextMessage convertToTextMessage(RaspberrySensorMessage message) {
    try {
      var json = objectMapper.writeValueAsString(message);
      return new TextMessage(json);
    } catch (JsonProcessingException e) {
      throw new WebSocketHandlerException(
          "Error serializing AMQP message: '%s'".formatted(message), e);
    }
  }
}
