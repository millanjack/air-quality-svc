package com.serjlemast.repository;

import static com.serjlemast.configuration.TimeZoneConfiguration.UTC_TIME_ZONE;
import static com.serjlemast.repository.GeneralFields.CREATED_FIELD;
import static com.serjlemast.repository.GeneralFields.DEVICE_ID_FIELD;
import static com.serjlemast.repository.GeneralFields.TYPE_FIELD;

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

  /*
   * Retrieves all sensor entities from the MongoDB collection.
   *
   * This method queries the entire collection of SensorEntity documents.
   * It is typically used for administrative or statistical purposes where all sensors
   * need to be processed or displayed.
   */
  public List<SensorEntity> findAll() {
    return mongoTemplate.findAll(SensorEntity.class);
  }

  /*
   * Finds an existing sensor entity by its device ID or inserts a new one if not found.
   * Returns the sensor entity ID after the operation.
   *
   * Behavior:
   *   - If a sensor with the given deviceId exists, it is returned unchanged.
   *   - If not, a new sensor entity is inserted with a type and creation time set.
   *
   * @param sensor The domain model containing device ID and sensor type
   * @return The ID of the found or newly inserted sensor entity
   * @throws RepositoryException if the operation fails to retrieve or create a record
   */
  public String findAndModify(Sensor sensor) {
    // Build a query to match the existing sensor by deviceId
    var query = new Query(Criteria.where(DEVICE_ID_FIELD).is(sensor.deviceId()));

    // Create update for insert-only fields (type, created time)
    var update =
        new Update()
            .setOnInsert(TYPE_FIELD, sensor.type())
            .setOnInsert(CREATED_FIELD, LocalDateTime.now(ZoneId.of(UTC_TIME_ZONE)));

    // Perform find-and-modify with upsert: update if exists, insert if not
    return Optional.ofNullable(
            mongoTemplate.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                SensorEntity.class))
        .map(SensorEntity::getId) // Extract ID if present
        .orElseThrow(
            () ->
                new RepositoryException(
                    "Failed to find or create sensor entity, deviceId: %s"
                        .formatted(sensor.deviceId())));
  }
}
