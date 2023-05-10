package de.wejago.mqtt2influx.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.wejago.mqtt2influx.config.MqttProperties;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith({OutputCaptureExtension.class, MockitoExtension.class})
class MqttSubscriberServiceTest {
    private final String TOPIC_TEST = "topic/test";
    @Mock
    private IMqttClient mqttClient;

    @Mock
    private MqttConnectOptions mqttConnectOptions;

    @Mock
    private SensorMessageSubscriber sensorMessageSubscriber;

    @Mock
    private MqttProperties mqttProperties;

    @InjectMocks
    private MqttSubscriberService mqttSubscriberService;

    @Test
    void testConnectSuccess(CapturedOutput output) throws MqttException {
        // GIVEN
        when(mqttProperties.getTopic()).thenReturn(TOPIC_TEST);

        // WHEN
        mqttSubscriberService.postConstruct();

        // THEN
        verify(mqttClient, times(1)).connect(mqttConnectOptions);
        verify(mqttClient, times(1)).subscribe(TOPIC_TEST, sensorMessageSubscriber);
        assertThat(output.getOut()).contains("Successfully connected to MQTT client!");
    }

    @Test
    void testConnectThrowsException(CapturedOutput output) throws MqttException {
        // GIVEN
        doThrow(new MqttException(1)).when(mqttClient).connect(mqttConnectOptions);

        // WHEN
        mqttSubscriberService.postConstruct();

        // THEN
        verify(mqttClient, times(1)).connect(mqttConnectOptions);
        assertThatThrownBy(() -> { throw new MqttException(1); })
            .isInstanceOf(MqttException.class);
        assertThat(output.getOut()).contains("Error connecting to MQTT client!");
    }
}