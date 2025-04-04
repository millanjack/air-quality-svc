package com.serjlemast.consumer;

import com.rabbitmq.client.Channel;
import com.serjlemast.handler.SensorDataWebSocketHandler;
import com.serjlemast.message.RaspberrySensorMessage;
import java.util.concurrent.atomic.AtomicReference;
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
  private final AtomicReference<RaspberrySensorMessage> lastMessage;

  @RabbitHandler
  public void handle(
      RaspberrySensorMessage message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
    try {
      log.trace("Delivery tag - {}, chanel - {},  message: {}", tag, channel, message);
      lastMessage.set(message);
      webSocketHandler.sendSensorData(message);
    } catch (Exception e) {
      log.error("Error sending WebSocket message", e);
    }
  }
}
