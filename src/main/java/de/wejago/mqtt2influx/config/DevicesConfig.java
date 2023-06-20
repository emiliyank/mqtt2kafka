package de.wejago.mqtt2influx.config;

import de.wejago.mqtt2influx.factory.YamlPropertySourceFactory;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Getter
@Setter
@ConfigurationProperties()
@PropertySource(value = "classpath:mqtt2kafka-configuration.yaml", factory = YamlPropertySourceFactory.class)
public class DevicesConfig {
    private List<Device> devices;
}
