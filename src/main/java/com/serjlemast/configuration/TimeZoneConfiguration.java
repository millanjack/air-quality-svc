package com.serjlemast.configuration;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
import org.springframework.context.annotation.Configuration;

/*
 * Configuration class that sets the default system-wide time zone for the application.
 * This ensures consistent handling of date and time values across all components,
 * especially in distributed or international environments.
 */
@Configuration
public class TimeZoneConfiguration {

  /*
   * Initializes the application with UTC as the default time zone.
   * This method is called automatically after the bean is created.
   *
   * Using UTC helps avoid issues with daylight saving time and regional time zone differences.
   */
  @PostConstruct
  public void init() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }
}
