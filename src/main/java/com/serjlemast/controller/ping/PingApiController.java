package com.serjlemast.controller.ping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ping")
@RequiredArgsConstructor
public class PingApiController {

  @RequestMapping(method = RequestMethod.HEAD)
  public ResponseEntity<Void> pong() {
    log.debug("ping pong: HEAD");
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<Void> pongGet() {
    log.debug("ping pong: GET");
    return ResponseEntity.ok().build();
  }
}
