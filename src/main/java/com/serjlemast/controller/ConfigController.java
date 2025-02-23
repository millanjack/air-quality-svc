package com.serjlemast.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigController {

  @Value("${app.websocket.url}")
  private String webSocketUrl;

  @GetMapping
  public Map<String, String> getConfig() {
    return Map.of("webSocketUrl", webSocketUrl);
  }
}
