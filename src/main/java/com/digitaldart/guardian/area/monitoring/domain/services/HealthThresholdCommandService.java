package com.digitaldart.guardian.area.monitoring.domain.services;

import com.digitaldart.guardian.area.monitoring.interfaces.rest.resource.DeviceHealthThresholdsResource;

public interface HealthThresholdCommandService {
    void handle(DeviceHealthThresholdsResource resource);
}
