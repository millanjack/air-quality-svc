package com.serjlemast.model.sensor;

import java.util.List;

public record Sensor(String deviceId, String type, List<SensorData> data) {}
