package com.serjlemast.repository.raspberry.entity;

import com.serjlemast.repository.sensor.entity.SensorEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@Document(collection = "raspberry")
@TypeAlias("RaspberryEntity")
public class RaspberryEntity {

  @Id private String id;

  @DocumentReference private List<SensorEntity> sensor;

  @CreatedDate private LocalDateTime created;

  @LastModifiedDate private LocalDateTime updated;
}
