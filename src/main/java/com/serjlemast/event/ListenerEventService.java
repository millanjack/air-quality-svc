package com.serjlemast.event;

import com.serjlemast.service.TelemetryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ListenerEventService {

  private final TelemetryService telemetryService;

  @EventListener
  public void handle(CreateSensorDataEvent event) {
    log.info("Received CreateSensorDataEvent: {}", event);
    telemetryService.save(event);
  }
}
