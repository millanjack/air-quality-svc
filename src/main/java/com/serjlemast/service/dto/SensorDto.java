package com.serjlemast.service.dto;

import java.util.List;

public record SensorDto(
    String id, String deviceId, String raspberryId, String type, List<SensorDataDto> data) {}
