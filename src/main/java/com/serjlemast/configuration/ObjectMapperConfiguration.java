package com.serjlemast.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Configuration class for setting up the {@link ObjectMapper}.
 * This configuration registers necessary modules, such as the {@link JavaTimeModule},
 * to enable proper serialization and deserialization of Java 8 Date/Time types.
 */
@Configuration
public class ObjectMapperConfiguration {

  /*
   * Creates and configures an ObjectMapper bean.
   * The default ObjectMapper is extended with additional modules,
   * such as the JavaTimeModule for handling Java 8 Date/Time types (e.g., LocalDate, LocalDateTime).
   */
  @Bean
  public ObjectMapper objectMapper() {
    // Registers the JavaTimeModule to properly handle Java 8 Date/Time types
    return new ObjectMapper().registerModule(new JavaTimeModule());
  }
}
