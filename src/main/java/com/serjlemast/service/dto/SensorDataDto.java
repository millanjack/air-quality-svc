package com.serjlemast.service.dto;

import static com.serjlemast.configuration.TimeZoneConfiguration.UTC_TIME_ZONE;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Collection;

public record SensorDataDto(
    String key,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = UTC_TIME_ZONE)
        Collection<LocalDateTime> dates,
    Collection<Double> values) {}
