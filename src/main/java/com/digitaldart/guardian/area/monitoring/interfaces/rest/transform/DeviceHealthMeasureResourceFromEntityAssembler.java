package com.digitaldart.guardian.area.monitoring.interfaces.rest.transform;

import com.digitaldart.guardian.area.monitoring.domain.model.aggregates.Device;
import com.digitaldart.guardian.area.monitoring.interfaces.rest.resource.DeviceHealthMeasureResource;

public class DeviceHealthMeasureResourceFromEntityAssembler {
    public static DeviceHealthMeasureResource toResourceFromEntity(Device device) {
        return new DeviceHealthMeasureResource(
                device.getDeviceRecordId(),
                device.getHealthThresholds().minBpm(),
                device.getHealthThresholds().maxBpm(),
                device.getHealthThresholds().minSpO2(),
                device.getHealthThresholds().maxSpO2()
        );
    }
}
