package com.serjlemast.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serjlemast.controller.websocket.SensorWebSocketHandler;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfiguration implements WebSocketConfigurer {

  private static final String SENSOR_DATA_WS_PATH = "/sensor-data";

  private final ObjectMapper objectMapper;

  @Bean
  public SensorWebSocketHandler webSocketHandler() {
    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    return new SensorWebSocketHandler(objectMapper, sessions);
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry
        .addHandler(webSocketHandler(), SENSOR_DATA_WS_PATH)
        // CORS: Allow requests from any origin
        .setAllowedOrigins("*");
  }
}
