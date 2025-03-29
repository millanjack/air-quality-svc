package com.serjlemast.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StaticFileController {

  @GetMapping("/js")
  public ResponseEntity<Resource> getJsFile(@RequestParam String fileName) {
    Resource resource = new ClassPathResource("static/js/" + fileName);
    return ResponseEntity.ok().body(resource);
  }

  @GetMapping("/css")
  public ResponseEntity<Resource> getCssFile(@RequestParam String fileName) {
    Resource resource = new ClassPathResource("static/css/" + fileName);
    return ResponseEntity.ok().body(resource);
  }
}
