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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:9000")
public class UserOpsController extends AbstractRestController {

  // register
  // forgot password
  // update profile
  // delete user account
  @Autowired
  private UserOpsService userOpsService;

  @Autowired
  private AuthenticationService authenticationService;


  @GetMapping("/users")
  public ResponseEntity getAllUsers(@RequestHeader Map<String, String> headers)
      throws UnauthorizedRequestException {
    setHeaders(headers);
    List<User> userList = new ArrayList<>();
    userList = userOpsService.getAllUsers(getSessionId());
    UserResponse userResponse = new UserResponse();
    userResponse.setUsernames(userList.stream()
        .map(user -> user.getUsername())
        .collect(Collectors.toList()));
    userResponse.setUsername(getUsername());
    return ResponseEntity.ok(userResponse);

  }


  @GetMapping("/user")
  public ResponseEntity getUser(@RequestHeader Map<String, String> headers)
      throws UnauthorizedRequestException {
    setHeaders(headers);
    return ResponseEntity.ok(new UserResponse(getUsername()));

  }

  @Override
  protected AuthenticationService getAuthenticationService() {
    return this.authenticationService;
  }
}
