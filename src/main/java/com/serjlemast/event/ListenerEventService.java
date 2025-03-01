package com.serjlemast.event;

import com.serjlemast.repository.TelemetryRepository;
import com.serjlemast.repository.entity.TelemetryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ListenerEventService {

  private final TelemetryRepository telemetryRepository;

  @EventListener
  public void handle(CreateSensorDataEvent event) {
    log.info("Received CreateSensorDataEvent: {}", event);
    TelemetryEntity telemetryEntity = new TelemetryEntity();
    telemetryEntity.setData(event.sensorDataEvent());
    telemetryRepository.save(telemetryEntity);
  }
}
