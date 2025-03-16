package com.serjlemast.repository.sensor.entity;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "sensor")
@TypeAlias("SensorEntity")
public class SensorEntity {

  @Id private String id;

  @NotNull
  //  @Indexed(unique = true) id + raspberryId
  private String raspberryId;

  @CreatedDate private LocalDateTime created;

  @LastModifiedDate private LocalDateTime updated;
}
