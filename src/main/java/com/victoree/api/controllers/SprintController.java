package com.victoree.api.controllers;

import com.victoree.api.domains.Sprint;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import com.victoree.api.services.AuthenticationService;
import com.victoree.api.services.SprintService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sprints")
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
public class SprintController extends AbstractRestController {

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private SprintService sprintService;

  @GetMapping("/all")
  public ResponseEntity getAllSprints(@RequestHeader Map<String, String> headers)
      throws UnauthorizedRequestException {
    setHeaders(headers);
    List<Sprint> sprints = sprintService.getAll();
    return ResponseEntity.ok(sprints);
  }

  @GetMapping
  public ResponseEntity getSprintById(@RequestHeader Map<String, String> headers,
      @RequestParam("") String id)
      throws UnauthorizedRequestException {
    setHeaders(headers);
    List<Sprint> sprints = sprintService.getSprintById(id);
    return ResponseEntity.ok(sprints);
  }

  @Override
  protected AuthenticationService getAuthenticationService() {
    return authenticationService;
  }
}
