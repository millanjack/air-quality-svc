package com.serjlemast.service;

import com.serjlemast.event.RaspberrySensorEvent;
import com.serjlemast.model.raspberry.RaspberryInfo;
import com.serjlemast.model.sensor.Sensor;
import com.serjlemast.repository.entity.RaspberryEntity;
import com.serjlemast.repository.entity.SensorEntity;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelemetryService {

  public static final String CREATED_FIELD = "created";
  public static final String DEVICE_ID_FIELD = "deviceId";

  public static final String UTC_TIME_ZONE = "UTC";

  private final MongoTemplate mongoTemplate;

  public void createOrUpdateData(RaspberrySensorEvent event) {
    var timestamp = event.timestamp();
    var raspberryId = createOrUpdateRaspberryInfo(event.info(), timestamp);
    event
        .sensors()
        .forEach(
            sensor -> {
              var sensorId = findOrCreateSensorEntity(raspberryId, sensor);
              sensor
                  .data()
                  .forEach(
                      data ->
                          saveSensorData(
                              sensorId,
                              sensor.deviceId(),
                              sensor.type(),
                              data.key(),
                              data.val(),
                              timestamp));
            });
  }

  private String createOrUpdateRaspberryInfo(RaspberryInfo info, LocalDateTime timestamp) {
    var query = new Query(Criteria.where(DEVICE_ID_FIELD).is(info.deviceId()));

    var update =
        new Update()
            .set("jvMemoryMb", info.jvMemoryMb())
            .set("boardTemperature", info.boardTemperature())
            .set("updated", timestamp)
            .setOnInsert("boardModel", info.boardModel())
            .setOnInsert("operatingSystem", info.operatingSystem())
            .setOnInsert("javaVersions", info.javaVersions())
            .setOnInsert(CREATED_FIELD, LocalDateTime.now(ZoneId.of(UTC_TIME_ZONE)));

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
            () -> new RuntimeException("Creating or updating raspberry entity failed for " + info));
  }

  private String findOrCreateSensorEntity(String raspberryId, Sensor sensor) {
    var query =
        new Query(
            Criteria.where(DEVICE_ID_FIELD)
                .is(sensor.deviceId())
                .and("raspberryId")
                .is(raspberryId));

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
                new RuntimeException(
                    "Failed to аштв or create sensor entity, deviceId: " + sensor.deviceId()));
  }

  public void saveSensorData(
      String sensorId,
      String deviceId,
      String type,
      String key,
      Number val,
      LocalDateTime timestamp) {
    var document =
        new Document()
            .append("sensorId", sensorId)
            .append(DEVICE_ID_FIELD, deviceId)
            .append("type", type)
            .append("sensorId", sensorId)
            .append("key", key)
            .append("val", val)
            .append(CREATED_FIELD, timestamp);

    mongoTemplate.getCollection("sensor_data").insertOne(document);
  }

  public List<SensorEntity> getAllSensorsWithLimitedData() {
    Aggregation aggregation =
        Aggregation.newAggregation(
            Aggregation.lookup("sensor_data", DEVICE_ID_FIELD, DEVICE_ID_FIELD, "data"),
            Aggregation.sort(Sort.Direction.DESC, "data.created"),
            Aggregation.project(
                "id", DEVICE_ID_FIELD, "raspberryId", "type", CREATED_FIELD, "data"));

    AggregationResults<SensorEntity> results =
        mongoTemplate.aggregate(aggregation, "sensor", SensorEntity.class);
    return results.getMappedResults();
  }
}
