package com.digitaldart.guardian.area.monitoring.interfaces.rest.transform;

import com.digitaldart.guardian.area.monitoring.domain.model.aggregates.Device;
import com.digitaldart.guardian.area.monitoring.interfaces.rest.resource.DeviceHealthThresholdsResource;

public class DeviceHealthThresholdsResourceFromEntityAssembler {
    public static DeviceHealthThresholdsResource toResourceFromEntity(Device device) {
        return new DeviceHealthThresholdsResource(
                device.getDeviceRecordId(),
                device.getHealthThresholds().minBpm(),
                device.getHealthThresholds().maxBpm(),
                device.getHealthThresholds().minSpO2(),
                device.getHealthThresholds().maxSpO2()
        );
    }
}
