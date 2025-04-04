package com.serjlemast.sceduler;

import com.serjlemast.event.PublisherEventService;
import com.serjlemast.message.RaspberrySensorMessage;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorMessageSchedulerService {

  private final AtomicReference<RaspberrySensorMessage> lastMessage;
  private final PublisherEventService publisherEventService;

  @Async
  @Scheduled(fixedRateString = "${app.schedule.fixedRate}")
  public void processLastMessage() {
    Optional.ofNullable(lastMessage.getAndSet(null))
        .ifPresent(
            message -> {
              log.info("Publishing last received message: {}", message);
              publisherEventService.publish(message);
            });
  }
}
