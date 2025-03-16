package com.serjlemast.repository.raspberry;

import com.serjlemast.repository.raspberry.entity.RaspberryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaspberryRepository extends MongoRepository<RaspberryEntity, String> {}
