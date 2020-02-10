package com.victoree.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestEndPointController {

  @GetMapping("/testsec")
  public ResponseEntity get() {
    return ResponseEntity.ok("works");
  }
}
