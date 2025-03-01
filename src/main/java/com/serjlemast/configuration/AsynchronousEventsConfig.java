package com.serjlemast.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class AsynchronousEventsConfig {

  /*
   * Creating Asynchronous Events
   * link: https://www.baeldung.com/spring-events#anonymous-events
   */
  @Bean
  public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
    var eventMulticaster = new SimpleApplicationEventMulticaster();
    eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
    return eventMulticaster;
  }
}
