package com.serjlemast.configuration;

import com.serjlemast.message.RaspberrySensorMessage;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReferenceStorageConfiguration {

  @Bean
  public AtomicReference<RaspberrySensorMessage> sensorReferenceStorage() {
    return new AtomicReference<>();
  }

  @Bean
  public AtomicReference<RaspberrySensorMessage> wsReferenceStorage() {
    return new AtomicReference<>();
  }
}
