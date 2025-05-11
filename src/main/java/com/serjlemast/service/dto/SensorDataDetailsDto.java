package com.serjlemast.service.dto;

import java.time.LocalDateTime;

public record SensorDataDetailsDto(String key, Double val, LocalDateTime created) {}
