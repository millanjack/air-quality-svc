package com.serjlemast.repository.entity;

import com.serjlemast.publisher.event.RaspberryEvent;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "telemetry")
@TypeAlias("TelemetryEntity")
public class TelemetryEntity {

  @Id private String id;

  private RaspberryEvent data;
}
