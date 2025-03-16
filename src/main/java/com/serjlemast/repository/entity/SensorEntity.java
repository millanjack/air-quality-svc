package com.serjlemast.repository.entity;

import com.serjlemast.model.sensor.SensorData;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "sensor")
@TypeAlias("SensorEntity")
@CompoundIndex(
    name = "unique_raspberry_sensor_index",
    def = "{'deviceId': 1, 'raspberryId': 1}",
    unique = true)
public class SensorEntity {

  @Id private String id;

  @NotNull private String deviceId;
  @NotNull private String raspberryId;

  /*
   * DHT_11,
   * DHT_22
   */
  private String type;

  private List<SensorData> data;

  @CreatedDate private LocalDateTime created;
}
