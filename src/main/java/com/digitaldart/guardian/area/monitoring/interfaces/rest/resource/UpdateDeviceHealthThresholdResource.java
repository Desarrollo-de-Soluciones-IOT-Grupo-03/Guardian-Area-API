package com.digitaldart.guardian.area.monitoring.interfaces.rest.resource;

public record UpdateDeviceHealthThresholdResource(
    int minBpm,
    int maxBpm,
    int minSpO2,
    int maxSpO2
) {
}
