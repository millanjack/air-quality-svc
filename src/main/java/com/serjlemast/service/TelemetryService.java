package com.serjlemast.service;

import com.serjlemast.event.RaspberrySensorEvent;
import com.serjlemast.model.raspberry.RaspberryInfo;
import com.serjlemast.model.sensor.Sensor;
import com.serjlemast.repository.raspberry.entity.RaspberryEntity;
import com.serjlemast.repository.sensor.entity.SensorEntity;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelemetryService {

  private final MongoTemplate mongoTemplate;

  public void createOrUpdateData(RaspberrySensorEvent event) {
    var timestamp = event.timestamp();
    var raspberryId = createOrUpdateRaspberryInfo(event.info(), timestamp);
    event
        .sensors()
        .forEach(
            sensor -> {
              String sensorId = findOrCreateSensorEntity(raspberryId, sensor);
            });
  }

  private String createOrUpdateRaspberryInfo(RaspberryInfo info, LocalDateTime timestamp) {
    var query = new Query(Criteria.where("deviceId").is(info.deviceId()));

    var update =
        new Update()
            .set("jvMemoryMb", info.jvMemoryMb())
            .set("boardTemperature", info.boardTemperature())
            .set("updated", timestamp)
            .setOnInsert("boardModel", info.boardModel())
            .setOnInsert("operatingSystem", info.operatingSystem())
            .setOnInsert("javaVersions", info.javaVersions())
            .setOnInsert("created", LocalDateTime.now(ZoneId.of("UTC")));

    return Optional.ofNullable(

            /*
             * When modifying a single document, both db.collection.findAndModify()
             * and the updateOne() method atomically update the document.
             *
             * See Atomicity and Transactions:
             * link: https://www.mongodb.com/docs/manual/reference/method/db.collection.findAndModify/#atomicity
             */
            mongoTemplate.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                RaspberryEntity.class))
        .map(RaspberryEntity::getId)
        .orElseThrow(
            () -> {
              return new RuntimeException(
                  "Creating or updating raspberry entity failed for " + info);
            });
  }

  private String findOrCreateSensorEntity(String raspberryId, Sensor sensor) {
    var query =
        new Query(
            Criteria.where("deviceId").is(sensor.deviceId()).and("raspberryId").is(raspberryId));

    var update =
        new Update()
            .setOnInsert("type", sensor.type())
            .setOnInsert("created", LocalDateTime.now(ZoneId.of("UTC")));

    return Optional.ofNullable(
            mongoTemplate.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                SensorEntity.class))
        .map(SensorEntity::getId)
        .orElseThrow(
            () ->
                new RuntimeException(
                    "Failed to аштв or create sensor entity, deviceId: " + sensor.deviceId()));
  }
}
