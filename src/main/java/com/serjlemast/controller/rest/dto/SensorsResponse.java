package com.serjlemast.controller.rest.dto;

import java.util.List;

public record SensorsResponse(List<SensorResponse> sensors, int total) {

  public SensorsResponse(List<SensorResponse> sensors) {
    this(sensors, sensors.size());
  }
}
