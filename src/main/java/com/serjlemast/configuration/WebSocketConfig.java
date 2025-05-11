package com.serjlemast.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serjlemast.controller.websocket.SensorDataWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

  private static final String WS_PATH = "/sensor-data";

  private final ObjectMapper objectMapper;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry
        .addHandler(new SensorDataWebSocketHandler(objectMapper), WS_PATH)
        // CORS: Allow requests from any origin
        .setAllowedOrigins("*");
  }
}