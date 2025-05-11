package com.serjlemast.configuration;

import com.serjlemast.controller.websocket.SensorWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket configuration class for registering WebSocket handlers. Enables WebSocket support and
 * configures endpoint for sensor data streaming.
 */
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfiguration implements WebSocketConfigurer {

  /** The endpoint path for sensor data WebSocket connections. */
  private static final String SENSOR_DATA_WS_PATH = "/sensor-data";

  /** The WebSocket handler managing sensor data messages. */
  private final SensorWebSocketHandler webSocketHandler;

  /** Registers the WebSocket handler for sensor data at the configured endpoint. */
  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry
        .addHandler(webSocketHandler, SENSOR_DATA_WS_PATH)
        // CORS: Allow requests from any origin
        .setAllowedOrigins("*");
  }
}
