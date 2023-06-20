package de.wejago.mqtt2influx.repository;

import de.wejago.mqtt2influx.config.MqttDataPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, MqttDataPoint> kafkaTemplate;

    public void writePoint(MqttDataPoint data) {
        kafkaTemplate.send("mqtt", data.getDevice_name(), data);
    }
}
