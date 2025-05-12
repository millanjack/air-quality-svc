package com.serjlemast.consumer;

import com.rabbitmq.client.Channel;
import com.serjlemast.controller.websocket.SensorWebSocketHandler;
import com.serjlemast.message.RaspberrySensorMessage;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

/*
 * RabbitMQ message consumer that processes incoming {@link RaspberrySensorMessage} ьуыыфпу.
 * This class receives sensor data from RabbitMQ, stores it in an atomic reference, and forwards it to a
 * WebSocket handler for real-time frontend updates.
 */
@Slf4j
@Component
@RabbitListener(queues = "${app.rabbitmq.queue}")
@RequiredArgsConstructor
public class RabbitMqConsumer {

  /*
   * WebSocket handler responsible for sending sensor data to connected WebSocket clients.
   * Used to push real-time updates as messages arrive from RabbitMQ.
   */
  private final SensorWebSocketHandler webSocketHandler;

  /*
   * Thread-safe reference to store the latest received sensor message.
   * Can be accessed by other components needing the most recent sensor data.
   */
  private final AtomicReference<RaspberrySensorMessage> sensorReferenceStorage;

  /*
   * Thread-safe reference to store the latest received sensor message.
   */
  private final AtomicReference<RaspberrySensorMessage> wsReferenceStorage;

  /**
   * Handles incoming sensor messages from RabbitMQ. This method is triggered automatically when a
   * message is received.
   *
   * @param message the received {@link RaspberrySensorMessage}
   * @param channel the AMQP channel through which the message was received
   * @param headers the AMQP headers
   */
  @RabbitHandler
  public void handle(
      RaspberrySensorMessage message, Channel channel, @Headers Map<String, Object> headers) {

    log.info("AMQP message: {}", message);
    logAmqpDetails(channel, headers);

    // Update the sensor data reference with the latest message
    sensorReferenceStorage.set(message);

    // Update the sensor data reference with the latest message
    wsReferenceStorage.set(message);

    // Send the sensor data to connected WebSocket clients
    webSocketHandler.sendSensorData(message);
  }

  private void logAmqpDetails(Channel channel, Map<String, Object> headers) {
    if (log.isDebugEnabled()) {
      log.debug("chanel - {}", channel);
      headers.forEach((key, value) -> log.debug("{}: {}", key, value));
    }
  }
}
