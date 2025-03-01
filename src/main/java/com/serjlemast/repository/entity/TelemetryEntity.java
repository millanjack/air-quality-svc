package com.serjlemast.repository.entity;

import com.serjlemast.model.SensorDataEvent;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "telemetry")
@TypeAlias("TelemetryEntity")
public class TelemetryEntity {

  @Id private String id;

  private SensorDataEvent data;
}
