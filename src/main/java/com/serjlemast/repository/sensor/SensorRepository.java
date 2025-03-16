package com.serjlemast.repository.sensor;

import com.serjlemast.repository.sensor.entity.SensorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends MongoRepository<SensorEntity, String> {}
