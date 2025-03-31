package com.serjlemast.controller.sensor.dto;

import java.util.List;

public record SensorResponse(
    String id, String deviceId, String raspberryId, String type, List<SensorDataResponse> data) {}
