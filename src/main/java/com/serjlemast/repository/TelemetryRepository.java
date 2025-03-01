package com.serjlemast.repository;

import com.serjlemast.repository.entity.TelemetryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelemetryRepository extends MongoRepository<TelemetryEntity, String> {}
