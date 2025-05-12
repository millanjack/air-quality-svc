package com.serjlemast.sceduler;

import com.serjlemast.message.RaspberrySensorMessage;
import com.serjlemast.service.TelemetryService;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/*
 * Service responsible for periodically processing the latest sensor message.
 *
 * This service is scheduled to run at a fixed rate (configured via `app.schedule.fixedRate`)
 * and processes the most recent message stored in a shared {@link AtomicReference}.
 * It clears the reference after retrieving the message to ensure each message is processed once.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerProcessorService {

  /** Service that handles telemetry data operations such as storing or updating sensor records. */
  private final TelemetryService telemetryService;

  /**
   * Thread-safe storage for the most recent {@link RaspberrySensorMessage}. Shared with other
   * components (e.g., RabbitMQ consumer) to allow centralized processing.
   */
  private final AtomicReference<RaspberrySensorMessage> sensorReferenceStorage;

  @Async("virtualThreadExecutor")
  @Scheduled(fixedRateString = "${app.schedule.fixedRate}")
  public void processLastMessage() {
    Optional.ofNullable(sensorReferenceStorage.getAndSet(null))
        .ifPresent(
            message -> {
              log.info("Processing received message: {}", message);
              telemetryService.findAndModify(message);
            });
  }
}
