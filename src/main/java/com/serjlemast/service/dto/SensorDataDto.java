package com.serjlemast.service.dto;

import java.time.LocalDateTime;

public record SensorDataDto(String key, Double val, LocalDateTime created) {}
