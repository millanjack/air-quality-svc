package com.serjlemast.event;

import com.serjlemast.message.RaspberrySensorMessage;
import com.serjlemast.model.raspberry.RaspberryInfo;
import com.serjlemast.model.sensor.Sensor;
import java.time.LocalDateTime;
import java.util.List;

public record RaspberrySensorEvent(
    LocalDateTime timestamp, RaspberryInfo info, List<Sensor> sensors) {

  public RaspberrySensorEvent(RaspberrySensorMessage message) {
    this(message.timestamp(), message.info(), message.sensors());
  }
}
