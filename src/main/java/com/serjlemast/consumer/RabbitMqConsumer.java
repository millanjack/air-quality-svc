package com.serjlemast.consumer;

import com.rabbitmq.client.Channel;
import com.serjlemast.model.SensorData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = "${app.rabbitmq.queue}")
public class RabbitMqConsumer {

  @RabbitHandler
  public void handle(SensorData data, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
    log.info("  <<< Received message: {}", data);
    log.info("  <<< Delivery tag: {}", tag);
    log.info("  <<< Channel channel: {}", channel);
  }
}
