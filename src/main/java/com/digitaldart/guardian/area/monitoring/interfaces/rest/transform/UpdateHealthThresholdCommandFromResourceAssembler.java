package com.digitaldart.guardian.area.monitoring.interfaces.rest.transform;

import com.digitaldart.guardian.area.monitoring.domain.model.commands.UpdateHealthThresholdsCommand;
import com.digitaldart.guardian.area.monitoring.domain.model.valueobjects.GuardianAreaDeviceRecordId;
import com.digitaldart.guardian.area.monitoring.interfaces.rest.resource.UpdateDeviceHealthThresholdResource;

public class UpdateHealthThresholdCommandFromResourceAssembler {
    public static UpdateHealthThresholdsCommand toCommandFromResource(String deviceRecordId, UpdateDeviceHealthThresholdResource resource){
        return new UpdateHealthThresholdsCommand(
                new GuardianAreaDeviceRecordId(deviceRecordId),
                resource.minBpm(),
                resource.maxBpm(),
                resource.minSpO2(),
                resource.maxSpO2()
        );
    }
}
