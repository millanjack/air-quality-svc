package com.serjlemast.repository;

import com.serjlemast.repository.entity.SensorDataEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SensorDataRepository extends MongoRepository<SensorDataEntity, String> {

  List<SensorDataEntity> findTop120BySensorIdOrderByCreatedDesc(String sensorId);
}
