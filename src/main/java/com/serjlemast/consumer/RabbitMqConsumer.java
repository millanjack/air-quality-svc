package com.serjlemast.consumer;

import com.rabbitmq.client.Channel;
import com.serjlemast.event.PublisherEventService;
import com.serjlemast.handler.SensorDataWebSocketHandler;
import com.serjlemast.message.RaspberrySensorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = "${app.rabbitmq.queue}")
@RequiredArgsConstructor
public class RabbitMqConsumer {

  private final SensorDataWebSocketHandler webSocketHandler;
  private final PublisherEventService publisherEventService;

  @RabbitHandler
  public void handle(
      RaspberrySensorMessage message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
    log.info("  <<< Received message: {}", message);
    log.info("  <<< Delivery tag: {}", tag);
    log.info("  <<< Channel channel: {}", channel);
    try {
      //      publisherEventService.publish(new CreateSensorDataEvent(data));
      webSocketHandler.sendSensorData(message); // Send to WebSocket clients
    } catch (Exception e) {
      log.error("Error sending WebSocket message", e);
    }
  }
}
