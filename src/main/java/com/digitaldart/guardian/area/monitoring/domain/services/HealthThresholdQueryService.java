package com.digitaldart.guardian.area.monitoring.domain.services;

import com.digitaldart.guardian.area.monitoring.domain.model.queries.GetHealthThresholdsByGuardianAreaDeviceRecordId;
import com.digitaldart.guardian.area.monitoring.domain.model.valueobjects.HealthThresholds;

public interface HealthThresholdQueryService {
    HealthThresholds handle(GetHealthThresholdsByGuardianAreaDeviceRecordId query);
}
