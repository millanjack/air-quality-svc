package com.serjlemast.service;

import com.serjlemast.controller.rest.StatisticResponse;
import com.serjlemast.message.RaspberrySensorMessage;
import com.serjlemast.repository.RaspberryRepository;
import com.serjlemast.repository.SensorDataRepository;
import com.serjlemast.repository.SensorRepository;
import com.serjlemast.repository.entity.SensorDataEntity;
import com.serjlemast.repository.entity.SensorEntity;
import com.serjlemast.service.dto.SensorDataDto;
import com.serjlemast.service.dto.SensorDto;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelemetryService {

  private final SensorRepository sensorRepository;
  private final SensorDataRepository sensorDataRepository;

  private final RaspberryRepository raspberryRepository;

  public void findAndModify(RaspberrySensorMessage message) {
    var timestamp = message.timestamp();

    var raspberryInfo = message.info();
    raspberryRepository.findAndModifyRaspberryInfo(raspberryInfo, timestamp);

    var sensors = message.sensors();
    sensors.forEach(
        sensor -> {
          var sensorId = sensorRepository.findAndModifySensor(sensor);
          sensor
              .data()
              .forEach(
                  data -> sensorDataRepository.save(sensorId, data.key(), data.val(), timestamp));
        });
  }

  public StatisticResponse findAllSensorsWithLimitedData() {
    var sensors =
        sensorRepository.findAll().stream()
            .map(this::buildSensorDetailsIfDataExists)
            .flatMap(Optional::stream)
            .toList();

    return new StatisticResponse(sensors);
  }

  private Optional<SensorDto> buildSensorDetailsIfDataExists(SensorEntity sensor) {
    var sensorId = sensor.getId();

    var sensorDataList = sensorDataRepository.findLastRecordDataSortByCreatedDesc(sensorId);
    if (sensorDataList.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(
        new SensorDto(
            sensorId,
            sensor.getDeviceId(),
            sensor.getRaspberryId(),
            sensor.getType(),
            fetchSensorData(sensorDataList)));
  }

  private List<SensorDataDto> fetchSensorData(List<SensorDataEntity> entities) {
    // types: temperature_celsius, humidity, temperature_fahrenheit, eco2
    Map<String, List<SensorDataEntity>> typesWithDetails =
        entities.stream()
            // rever DESC records
            .sorted(Comparator.comparing(SensorDataEntity::getCreated))
            .filter(data -> data.getKey() != null)
            .collect(Collectors.groupingBy(SensorDataEntity::getKey, Collectors.toList()));

    return findSensorDataList(typesWithDetails);
  }

  private List<SensorDataDto> findSensorDataList(Map<String, List<SensorDataEntity>> groupedData) {
    return groupedData.entrySet().stream()
        .map(
            entry -> {
              var type = entry.getKey();
              Map<LocalDateTime, Double> valueMap =
                  entry.getValue().stream()
                      .collect(
                          Collectors.toMap(
                              SensorDataEntity::getCreated,
                              SensorDataEntity::getVal,
                              (existing, replacement) -> replacement,
                              LinkedHashMap::new));
              return new SensorDataDto(type, valueMap.keySet(), valueMap.values());
            })
        .toList();
  }
}
