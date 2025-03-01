package com.serjlemast.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublisherEventService {

  private final ApplicationEventPublisher eventPublisher;
  
  public void publish(CreateSensorDataEvent event) {
    log.trace("Publishing task data event: '{}'", event);
    eventPublisher.publishEvent(event);
  }
}
