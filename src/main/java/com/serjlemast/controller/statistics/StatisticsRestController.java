package com.serjlemast.controller.statistics;

import com.serjlemast.controller.statistics.dto.SensorResponse;
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
public class StatisticsRestController {

  private final TelemetryService telemetryService;

  @GetMapping("/statistics")
  public ResponseEntity<List<SensorResponse>> sensors() {
    return ResponseEntity.ok(telemetryService.getAllSensorsWithLimitedData());
  }
}
