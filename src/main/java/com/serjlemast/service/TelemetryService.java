package com.serjlemast.service;

import com.serjlemast.controller.rest.StatisticResponse;
import com.serjlemast.message.RaspberrySensorMessage;
import com.serjlemast.repository.RaspberryRepository;
import com.serjlemast.repository.SensorDataRepository;
import com.serjlemast.repository.SensorRepository;
import com.serjlemast.repository.entity.SensorDataEntity;
import com.serjlemast.repository.entity.SensorEntity;
import com.serjlemast.service.dto.SensorDataDetailsDto;
import com.serjlemast.service.dto.SensorDataDto;
import com.serjlemast.service.dto.SensorDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    List<SensorDto> sensors = new ArrayList<>();

    List<SensorEntity> sensorEntities = sensorRepository.findAll();

    sensorEntities.forEach(
        sensor -> {
          var sensorId = sensor.getId();
          var sensorDataEntities =
              sensorDataRepository.findLastRecordDataSortByCreatedDesc(sensorId);
          if (sensorDataEntities.isEmpty()) {
            return;
          }

          Map<String, List<SensorDataDetailsDto>> map =
              sensorDataEntities.stream()
                  // rever DESC records
                  .sorted(Comparator.comparing(SensorDataEntity::getCreated))
                  .filter(data -> data.getKey() != null)
                  .map(
                      data ->
                          new SensorDataDetailsDto(data.getKey(), data.getVal(), data.getCreated()))
                  .collect(Collectors.groupingBy(SensorDataDetailsDto::key, Collectors.toList()));

          List<SensorDataDto> sensorDataResponses = new ArrayList<>();
          map.forEach(
              (key, value) -> {
                Map<LocalDateTime, Double> values =
                    value.stream()
                        .collect(
                            Collectors.toMap(
                                SensorDataDetailsDto::created,
                                SensorDataDetailsDto::val,
                                (first, last) -> last,
                                LinkedHashMap::new));
                sensorDataResponses.add(new SensorDataDto(key, values.keySet(), values.values()));
              });

          sensors.add(
              new SensorDto(
                  sensorId,
                  sensor.getDeviceId(),
                  sensor.getRaspberryId(),
                  sensor.getType(),
                  sensorDataResponses));
        });

    return new StatisticResponse(sensors);
  }
}
