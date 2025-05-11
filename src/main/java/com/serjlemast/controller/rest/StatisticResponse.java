package com.serjlemast.controller.rest;

import com.serjlemast.service.dto.SensorDto;
import java.util.List;

public record StatisticResponse(List<SensorDto> sensors, int total) {

  public StatisticResponse(List<SensorDto> sensors) {
    this(sensors, sensors.size());
  }
}
