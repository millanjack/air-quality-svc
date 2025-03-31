package com.serjlemast.repository;

import com.serjlemast.repository.entity.SensorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SensorRepository extends MongoRepository<SensorEntity, String> {}
