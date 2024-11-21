package com.digitaldart.guardian.area.healthmeasure.service.impl;

import com.digitaldart.guardian.area.monitoring.application.internal.commandservices.HealthMeasureCommandServiceImp;
import com.digitaldart.guardian.area.monitoring.domain.model.aggregates.Device;
import com.digitaldart.guardian.area.monitoring.domain.model.aggregates.HealthMeasure;
import com.digitaldart.guardian.area.monitoring.domain.model.commands.CreateHealthMeasureCommand;
import com.digitaldart.guardian.area.monitoring.domain.model.valueobjects.ApiKey;
import com.digitaldart.guardian.area.monitoring.domain.model.valueobjects.BeatsPerMinute;
import com.digitaldart.guardian.area.monitoring.domain.model.valueobjects.GuardianAreaDeviceRecordId;
import com.digitaldart.guardian.area.monitoring.domain.model.valueobjects.SaturationOfPeripheralOxygen;
import com.digitaldart.guardian.area.monitoring.infrastructure.persistence.jpa.repositories.DeviceRepository;
import com.digitaldart.guardian.area.monitoring.infrastructure.persistence.jpa.repositories.HealthMeasureRepository;
import com.digitaldart.guardian.area.shared.domain.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HealthMeasureCommandServiceImpTest {

    private HealthMeasureCommandServiceImp service;

    @Mock
    private HealthMeasureRepository healthMeasureRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new HealthMeasureCommandServiceImp(healthMeasureRepository, deviceRepository);
    }

    @Test
    void handle_ShouldCreateHealthMeasure_WhenDeviceIsValid() {
        // Arrange
        var bpm = new BeatsPerMinute(80);
        var spo2 = new SaturationOfPeripheralOxygen(98);
        var guardianAreaDeviceRecordId = new GuardianAreaDeviceRecordId("70fb68f3-aa7e-4108-9417-0bd1fb84fa77");
        var apiKey = new ApiKey("api-key-abc");
        var command = new CreateHealthMeasureCommand(bpm, spo2, guardianAreaDeviceRecordId, apiKey);
        var device = mock(Device.class); // Simula un dispositivo válido
        var healthMeasure = new HealthMeasure(bpm, spo2, guardianAreaDeviceRecordId);

        when(deviceRepository.findByGuardianAreaDeviceRecordIdAndApiKey(guardianAreaDeviceRecordId, apiKey))
                .thenReturn(Optional.of(device));
        when(healthMeasureRepository.save(any(HealthMeasure.class))).thenReturn(healthMeasure);

        // Act
        Optional<HealthMeasure> result = service.handle(command);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(80, result.get().getBpm().bpm());
        assertEquals(98, result.get().getSpo2().spo2());
        assertEquals("70fb68f3-aa7e-4108-9417-0bd1fb84fa77", result.get().getGuardianAreaDeviceRecordId().deviceRecordId());

        verify(deviceRepository, times(1)).findByGuardianAreaDeviceRecordIdAndApiKey(guardianAreaDeviceRecordId, apiKey);
        verify(healthMeasureRepository, times(1)).save(any(HealthMeasure.class));
    }

    @Test
    void handle_ShouldThrowValidationException_WhenDeviceIsInvalid() {
        // Arrange
        var bpm = new BeatsPerMinute(80);
        var spo2 = new SaturationOfPeripheralOxygen(98);
        var guardianAreaDeviceRecordId = new GuardianAreaDeviceRecordId("device-id-123");
        var apiKey = new ApiKey("api-key-abc");
        var command = new CreateHealthMeasureCommand(bpm, spo2, guardianAreaDeviceRecordId, apiKey);

        when(deviceRepository.findByGuardianAreaDeviceRecordIdAndApiKey(guardianAreaDeviceRecordId, apiKey))
                .thenReturn(Optional.empty());

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> service.handle(command));
        assertEquals("Incorrect device Id or API key", exception.getMessage());

        verify(deviceRepository, times(1)).findByGuardianAreaDeviceRecordIdAndApiKey(guardianAreaDeviceRecordId, apiKey);
        verify(healthMeasureRepository, never()).save(any(HealthMeasure.class));
    }
}