package com.serjlemast.configuration;

import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

@Configuration
public class AsynchronousEventsConfig {

  @Bean
  public ApplicationEventMulticaster applicationEventMulticaster() {
    var eventMulticaster = new SimpleApplicationEventMulticaster();
    eventMulticaster.setTaskExecutor(Executors.newVirtualThreadPerTaskExecutor());
    return eventMulticaster;
  }
}