package com.serjlemast.controller.rest;

import com.serjlemast.service.TelemetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class StatisticController {

  private final TelemetryService telemetryService;

  @GetMapping("/statistics")
  public ResponseEntity<StatisticResponse> sensors() {
    return ResponseEntity.ok(telemetryService.findAllSensorsWithLimitedData());
  }
}
