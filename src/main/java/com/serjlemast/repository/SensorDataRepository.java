package com.serjlemast.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.serjlemast.repository.entity.SensorDataEntity;

public interface SensorDataRepository extends MongoRepository<SensorDataEntity, String> {

  List<SensorDataEntity> findTop100BySensorIdOrderByCreatedDesc(String sensorId);
}
