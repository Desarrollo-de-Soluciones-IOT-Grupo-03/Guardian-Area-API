package com.digitaldart.guardian.area.monitoring.domain.model.commands;

import com.digitaldart.guardian.area.monitoring.domain.model.valueobjects.GuardianAreaDeviceRecordId;

public record UpdateHealthThresholdsCommand(
    GuardianAreaDeviceRecordId guardianAreaDeviceRecordId,
    int minBpm,
    int maxBpm,
    int minSpO2,
    int maxSpO2
) {
}
