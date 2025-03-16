package com.serjlemast.service;

import com.serjlemast.event.CreateSensorDataEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelemetryService {

  private final MongoTemplate mongoTemplate;

  public void save(CreateSensorDataEvent event) {
    // todo impl
  }
}
