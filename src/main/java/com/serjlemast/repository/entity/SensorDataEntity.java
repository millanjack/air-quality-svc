package com.serjlemast.repository.entity;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "sensor_data")
@TypeAlias("SensorDataEntity")
public class SensorDataEntity {

  @Id private String id;

  @NotNull private String sensorId;

  @NotNull private String key;

  @NotNull private Double val;

  @CreatedDate private LocalDateTime created;
}
