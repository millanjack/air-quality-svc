package com.serjlemast.repository.raspberry.entity;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "raspberry")
@TypeAlias("RaspberryEntity")
public class RaspberryEntity {

  @Id private String id;

  @NotNull private String deviceId;

  private String boardModel;

  private String operatingSystem;

  private String javaVersions;

  private double jvMemoryMb;

  private double boardTemperature;

  @CreatedDate private LocalDateTime created;

  @LastModifiedDate private LocalDateTime updated;
}
