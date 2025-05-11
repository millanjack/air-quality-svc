package com.serjlemast.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Collection;

public record SensorDataDto(
    String key,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = UTC)
        Collection<LocalDateTime> dates,
    Collection<Double> values) {

  public static final String UTC = "UTC";
}
