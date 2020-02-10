package com.victoree.api.controllers;

import com.victoree.api.domains.AuthenticationResponse;
import com.victoree.api.domains.LoginRequest;
import com.victoree.api.exceptions.UnauthorizedLoginException;
import com.victoree.api.services.AuthenticationService;
import com.victoree.api.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:9000")
public class AuthenticationController {

  @Autowired
  private AuthenticationService authenticationService;

  @PutMapping("/login")
  public ResponseEntity attemptLogin(@RequestBody LoginRequest loginRequest) {
    if(ValidatorUtil.validate(loginRequest, loginRequest.getClass())) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    AuthenticationResponse authenticationResponse = null;
    try {
      authenticationResponse
          = authenticationService.authenticate(loginRequest);
    } catch (UnauthorizedLoginException e) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    return ResponseEntity.ok(authenticationResponse);
  }

}
