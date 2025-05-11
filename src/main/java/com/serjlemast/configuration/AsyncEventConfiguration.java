package com.serjlemast.configuration;

import java.util.concurrent.Executor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

/*
 * Async scheduling jobs
 * Link to info: <a href="https://habr.com/ru/articles/771112/">Async scheduling jobs</a>
 *
 * 1. Mark the main class with @EnableAsync
 * 2. Mark a scheduler job with @Async annotation
 * 3. Configure ThreadPoolTaskExecutor
 */
@EnableAsync
@Configuration
public class AsyncEventConfiguration implements AsyncConfigurer {

  @Override
  public Executor getAsyncExecutor() {
    var executor = new SimpleAsyncTaskExecutor();
    executor.setVirtualThreads(true);
    executor.setThreadNamePrefix("async-vth-");
    return executor;
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return new SimpleAsyncUncaughtExceptionHandler();
  }
}
