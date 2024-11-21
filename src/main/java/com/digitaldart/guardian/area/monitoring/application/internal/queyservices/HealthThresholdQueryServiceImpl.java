package com.digitaldart.guardian.area.monitoring.application.internal.queyservices;

import com.digitaldart.guardian.area.monitoring.domain.model.queries.GetHealthThresholdsByGuardianAreaDeviceRecordId;
import com.digitaldart.guardian.area.monitoring.domain.model.valueobjects.HealthThresholds;
import com.digitaldart.guardian.area.monitoring.domain.services.HealthThresholdQueryService;
import com.digitaldart.guardian.area.monitoring.infrastructure.persistence.jpa.repositories.DeviceRepository;
import com.digitaldart.guardian.area.shared.domain.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class HealthThresholdQueryServiceImpl implements HealthThresholdQueryService {

    private final DeviceRepository deviceRepository;

    public HealthThresholdQueryServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public HealthThresholds handle(GetHealthThresholdsByGuardianAreaDeviceRecordId query) {
        var device = deviceRepository.findByGuardianAreaDeviceRecordId(query.guardianAreaDeviceRecordId());
        if (device.isEmpty()) {
            throw new ResourceNotFoundException("No device found");
        }
        return device.get().getHealthThresholds();
    }
}
