package com.serjlemast.repository.raspberry;

import com.serjlemast.repository.raspberry.entity.RaspberryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RaspberryRepository extends MongoRepository<RaspberryEntity, String> {}
