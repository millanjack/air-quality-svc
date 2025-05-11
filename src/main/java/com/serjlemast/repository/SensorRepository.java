package com.serjlemast.repository;

import static com.serjlemast.repository.GeneralFields.CREATED_FIELD;
import static com.serjlemast.repository.GeneralFields.DEVICE_ID_FIELD;
import static com.serjlemast.repository.GeneralFields.UTC_TIME_ZONE;

import com.serjlemast.model.sensor.Sensor;
import com.serjlemast.repository.entity.SensorEntity;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SensorRepository {

  private final MongoTemplate mongoTemplate;

  public List<SensorEntity> findAll() {
    return mongoTemplate.findAll(SensorEntity.class);
  }

  public String findAndModifySensor(Sensor sensor) {
    var query = new Query(Criteria.where(DEVICE_ID_FIELD).is(sensor.deviceId()));

    var update =
        new Update()
            .setOnInsert("type", sensor.type())
            .setOnInsert(CREATED_FIELD, LocalDateTime.now(ZoneId.of(UTC_TIME_ZONE)));

    return Optional.ofNullable(
            mongoTemplate.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                SensorEntity.class))
        .map(SensorEntity::getId)
        .orElseThrow(
            () ->
                new RepositoryException(
                    "Failed to find or create sensor entity, deviceId: %s"
                        .formatted(sensor.deviceId())));
  }
}
