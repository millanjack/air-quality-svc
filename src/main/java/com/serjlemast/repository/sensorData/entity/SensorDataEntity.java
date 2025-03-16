package com.serjlemast.repository.sensorData.entity;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "sensor_data")
@TypeAlias("SensorDataEntity")
public class SensorDataEntity {

  @Id private String id;

  @NotNull
  @Indexed(unique = true)
  private String sensorId;

  @CreatedDate private LocalDateTime created;

  @LastModifiedDate private LocalDateTime updated;
}
