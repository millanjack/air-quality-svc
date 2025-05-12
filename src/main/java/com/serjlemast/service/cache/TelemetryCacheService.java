package com.serjlemast.service.cache;

import com.serjlemast.controller.rest.StatisticResponse;
import com.serjlemast.service.TelemetryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelemetryCacheService {

  private final TelemetryService telemetryService;

  @Cacheable(cacheNames = "telemetry", key = "'sensor'")
  public StatisticResponse findAllSensorsFromCacheIfAvailable() {
    return telemetryService.findAllSensorsWithLimitedData();
  }

  @Scheduled(cron = "0 */1 * * * *")
  @CacheEvict(cacheNames = "telemetry", key = "'sensor'")
  public void clearCache() {
    log.debug("Clearing cache");
  }
}
