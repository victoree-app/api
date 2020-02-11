package com.victoree.api.controllers;

import com.victoree.api.domains.User;
import com.victoree.api.domains.UserResponse;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import com.victoree.api.services.AuthenticationService;
import com.victoree.api.services.UserOpsService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:9000")
public class UserOpsController {

  // register
  // forgot password
  // update profile
  // delete user account
  @Autowired
  UserOpsService userOpsService;

  @Autowired
  private AuthenticationService authenticationService;


  @GetMapping("/users")
  public ResponseEntity getAllUsers(@RequestHeader Map<String, String> headers) {
    String sessionId = headers.getOrDefault("sessionid", null);
    if (sessionId == null) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    List<User> userList = new ArrayList<>();
    try {
      userList = userOpsService.getAllUsers(sessionId);
    } catch (UnauthorizedRequestException e) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    UserResponse userResponse = new UserResponse();
    userResponse.setUsernames(userList.stream()
        .map(user -> user.getUsername())
        .collect(Collectors.toList()));
    return ResponseEntity.ok(userResponse);

  }


  @GetMapping("/user")
  public ResponseEntity getUser(@RequestHeader Map<String, String> headers) {
    String sessionId = headers.getOrDefault("sessionid", null);
    if (sessionId == null) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    try {
      String username = userOpsService.getUserNameFromSessionId(sessionId);
      if (username == null) {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
      }
      return ResponseEntity.ok(new UserResponse(username));
    } catch (UnauthorizedRequestException e) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
  }
}
