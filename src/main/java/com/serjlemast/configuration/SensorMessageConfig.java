package com.serjlemast.configuration;

import com.serjlemast.message.RaspberrySensorMessage;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SensorMessageConfig {

  @Bean
  public AtomicReference<RaspberrySensorMessage> lastSensorMessage() {
    return new AtomicReference<>();
  }
}
