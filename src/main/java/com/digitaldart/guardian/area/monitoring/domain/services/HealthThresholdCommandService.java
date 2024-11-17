package com.digitaldart.guardian.area.monitoring.domain.services;

import com.digitaldart.guardian.area.monitoring.domain.model.commands.UpdateHealthThresholdsCommand;
import com.digitaldart.guardian.area.monitoring.interfaces.rest.resource.DeviceHealthMeasureResource;

public interface HealthThresholdCommandService {
    void handle(DeviceHealthMeasureResource resource);
}
