package com.digitaldart.guardian.area.monitoring.interfaces.rest;

import com.digitaldart.guardian.area.monitoring.domain.model.queries.GetDeviceByGuardianAreaDeviceRecordIdQuery;
import com.digitaldart.guardian.area.monitoring.domain.model.queries.GetHealthThresholdsByGuardianAreaDeviceRecordId;
import com.digitaldart.guardian.area.monitoring.domain.model.valueobjects.GuardianAreaDeviceRecordId;
import com.digitaldart.guardian.area.monitoring.domain.services.DeviceCommandService;
import com.digitaldart.guardian.area.monitoring.domain.services.DeviceQueryService;
import com.digitaldart.guardian.area.monitoring.domain.services.HealthThresholdCommandService;
import com.digitaldart.guardian.area.monitoring.domain.services.HealthThresholdQueryService;
import com.digitaldart.guardian.area.monitoring.interfaces.rest.resource.DeviceHealthThresholdsResource;
import com.digitaldart.guardian.area.monitoring.interfaces.rest.resource.UpdateDeviceHealthThresholdResource;
import com.digitaldart.guardian.area.monitoring.interfaces.rest.transform.DeviceHealthThresholdsResourceFromEntityAssembler;
import com.digitaldart.guardian.area.monitoring.interfaces.rest.transform.UpdateHealthThresholdCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/devices", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Devices")
public class DeviceHealthThresholdController {

    private final HealthThresholdCommandService healthThresholdCommandService;
    private final HealthThresholdQueryService healthThresholdQueryService;
    private final DeviceCommandService deviceCommandService;
    private final DeviceQueryService deviceQueryService;

    public DeviceHealthThresholdController(HealthThresholdCommandService healthThresholdCommandService, HealthThresholdQueryService healthThresholdQueryService, DeviceCommandService deviceCommandService, DeviceQueryService deviceQueryService) {
        this.healthThresholdCommandService = healthThresholdCommandService;
        this.healthThresholdQueryService = healthThresholdQueryService;
        this.deviceCommandService = deviceCommandService;
        this.deviceQueryService = deviceQueryService;
    }

    @PutMapping("/{deviceRecordId}/health-thresholds")
    public ResponseEntity<DeviceHealthThresholdsResource> updateDeviceHealthThresholdsByDeviceRecordId(@PathVariable String deviceRecordId, @RequestBody UpdateDeviceHealthThresholdResource updateDeviceHealthThresholdResource) {
        var updateHealthThresholdsCommand = UpdateHealthThresholdCommandFromResourceAssembler.toCommandFromResource(deviceRecordId, updateDeviceHealthThresholdResource);
        var device = deviceCommandService.handle(updateHealthThresholdsCommand);
        if (device.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var deviceHealthThresholdsResource = DeviceHealthThresholdsResourceFromEntityAssembler.toResourceFromEntity(device.get());
        healthThresholdCommandService.handle(deviceHealthThresholdsResource);
        return ResponseEntity.ok(deviceHealthThresholdsResource);
    }

    @GetMapping("/{deviceRecordId}/health-thresholds")
    public ResponseEntity<DeviceHealthThresholdsResource> getDeviceHealthThresholdsByDeviceRecordId(@PathVariable String deviceRecordId) {
        var guardianAreaDeviceRecordId = new GuardianAreaDeviceRecordId(deviceRecordId);
        var query = new GetDeviceByGuardianAreaDeviceRecordIdQuery(guardianAreaDeviceRecordId);
        var device = deviceQueryService.handle(query);
        var deviceHealthThresholdsResource = DeviceHealthThresholdsResourceFromEntityAssembler.toResourceFromEntity(device.get());
        return ResponseEntity.ok(deviceHealthThresholdsResource);
    }

}
