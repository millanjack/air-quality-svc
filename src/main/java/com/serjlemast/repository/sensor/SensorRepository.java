package com.serjlemast.repository.sensor;

import com.serjlemast.repository.sensor.entity.SensorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SensorRepository extends MongoRepository<SensorEntity, String> {}
