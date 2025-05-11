package com.serjlemast.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ping")
public class PingApiController {

  @RequestMapping(method = RequestMethod.HEAD)
  public ResponseEntity<Void> pong() {
    log.debug("ping pong: HEAD");
    return ResponseEntity.ok().build();
  }
}
