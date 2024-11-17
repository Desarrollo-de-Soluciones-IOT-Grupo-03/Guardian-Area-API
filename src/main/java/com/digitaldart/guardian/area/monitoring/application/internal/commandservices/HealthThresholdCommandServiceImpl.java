package com.digitaldart.guardian.area.monitoring.application.internal.commandservices;

import com.digitaldart.guardian.area.monitoring.domain.model.commands.UpdateHealthThresholdsCommand;
import com.digitaldart.guardian.area.monitoring.domain.services.HealthThresholdCommandService;
import com.digitaldart.guardian.area.monitoring.infrastructure.persistence.jpa.repositories.DeviceRepository;
import com.digitaldart.guardian.area.monitoring.interfaces.rest.resource.DeviceHealthMeasureResource;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthThresholdCommandServiceImpl implements HealthThresholdCommandService {

    private MqttClient mqttClient;
    private Gson gson;

    private final String HEALTH_THRESHOLDS_TOPIC = "guardian-area/health-thresholds";
    public HealthThresholdCommandServiceImpl(DeviceRepository deviceRepository, MqttClient mqttClient, Gson gson) {
        this.mqttClient = mqttClient;
        this.gson = gson;
    }

    @Override
    public void handle(DeviceHealthMeasureResource command) {
        try {
            MqttMessage mqttMessage = new MqttMessage(gson.toJson(command).getBytes());
            mqttMessage.setQos(2);
            mqttClient.publish(HEALTH_THRESHOLDS_TOPIC, mqttMessage);
            System.out.println("Mensaje enviado"+ mqttMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
