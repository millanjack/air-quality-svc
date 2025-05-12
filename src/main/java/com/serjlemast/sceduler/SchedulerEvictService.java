package com.serjlemast.sceduler;

import com.serjlemast.message.RaspberrySensorMessage;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerEvictService {

  /*
   * Thread-safe reference to store the latest received sensor message.
   */
  private final AtomicReference<RaspberrySensorMessage> wsReferenceStorage;

  @Async("virtualThreadExecutor")
  @Scheduled(cron = "${app.schedule.eviction.cron}")
  public void ttlEvict() {
    wsReferenceStorage.set(null);
  }
}
