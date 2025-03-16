package com.serjlemast.event;

import com.serjlemast.message.RaspberrySensorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublisherEventService {

  private final ApplicationEventPublisher eventPublisher;

  public void publish(RaspberrySensorMessage message) {
    var event = new RaspberrySensorEvent(message);
    log.trace("Publishing event - {}", event);
    eventPublisher.publishEvent(event);
  }
}
