package com.serjlemast.controller.sensor;

import com.serjlemast.repository.entity.SensorEntity;
import com.serjlemast.service.TelemetryService;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class SensorsRestController {

  private final TelemetryService telemetryService;

  @GetMapping("/sensors")
  public ResponseEntity<List<SensorEntity>> sensors() {
    return ResponseEntity.ok(telemetryService.getAllSensorsWithLimitedData());
  }
}
