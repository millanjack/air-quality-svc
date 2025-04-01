package com.serjlemast.service;

import com.serjlemast.controller.sensor.dto.SensorDataResponse;
import com.serjlemast.controller.sensor.dto.SensorResponse;
import com.serjlemast.event.RaspberrySensorEvent;
import com.serjlemast.model.raspberry.RaspberryInfo;
import com.serjlemast.model.sensor.Sensor;
import com.serjlemast.repository.SensorDataRepository;
import com.serjlemast.repository.SensorRepository;
import com.serjlemast.repository.entity.RaspberryEntity;
import com.serjlemast.repository.entity.SensorEntity;
import com.serjlemast.service.dto.SensorDataDto;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
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

  public static final String CREATED_FIELD = "created";
  public static final String DEVICE_ID_FIELD = "deviceId";

  public static final String UTC_TIME_ZONE = "UTC";

  private final MongoTemplate mongoTemplate;
  private final SensorRepository sensorRepository;
  private final SensorDataRepository sensorDataRepository;

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
                  .forEach(data -> saveSensorData(sensorId, data.key(), data.val(), timestamp));
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

  public void saveSensorData(String sensorId, String key, Number val, LocalDateTime timestamp) {
    var document =
        new Document()
            .append("sensorId", sensorId)
            .append("sensorId", sensorId)
            .append("key", key)
            .append("val", val)
            .append(CREATED_FIELD, timestamp);

    mongoTemplate.getCollection("sensor_data").insertOne(document);
  }

  public List<SensorResponse> getAllSensorsWithLimitedData() {
    List<SensorResponse> response = new ArrayList<>();

    sensorRepository
        .findAll()
        .forEach(
            sensor -> {
              var sensorId = sensor.getId();
              var sensorDataEntities =
                  sensorDataRepository.findTop100BySensorIdOrderByCreatedDesc(sensorId);

              Map<String, List<SensorDataDto>> map =
                  sensorDataEntities.stream()
                      .map(
                          data ->
                              new SensorDataDto(data.getKey(), data.getVal(), data.getCreated()))
                      .collect(Collectors.groupingBy(SensorDataDto::key, Collectors.toList()));

              List<SensorDataResponse> sensorDataResponses = new ArrayList<>();
              map.forEach(
                  (key, value) -> {
                    Map<LocalDateTime, Double> values =
                        value.stream()
                            .collect(
                                Collectors.toMap(
                                    SensorDataDto::created,
                                    SensorDataDto::val,
                                    (first, last) -> last,
                                    LinkedHashMap::new));
                    sensorDataResponses.add(
                        new SensorDataResponse(key, values.keySet(), values.values()));
                  });

              response.add(
                  new SensorResponse(
                      sensorId,
                      sensor.getDeviceId(),
                      sensor.getRaspberryId(),
                      sensor.getType(),
                      sensorDataResponses));
            });

    return response;
  }
}
