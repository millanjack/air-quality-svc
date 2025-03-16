package com.serjlemast.repository.sensorData;

import com.serjlemast.repository.sensorData.entity.SensorDataEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorDataRepository extends MongoRepository<SensorDataEntity, String> {}
