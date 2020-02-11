package com.victoree.api.controllers;

import com.victoree.api.domains.Project;
import com.victoree.api.domains.ProjectResponse;
import com.victoree.api.domains.ProjectSaveRequest;
import com.victoree.api.domains.ProjectUpdateRequest;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import com.victoree.api.services.AuthenticationService;
import com.victoree.api.services.ProjectService;
import com.victoree.api.validator.ValidatorUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:9000", maxAge = 3600, allowedHeaders = "*")
public class ProjectController {

  @Autowired
  private ProjectService projectService;

  @Autowired
  private AuthenticationService authenticationService;

  @Value("${test.value}")
  private String value;

  @GetMapping("/projects")
  public ResponseEntity getAll(@RequestParam("page") int pageNum,
      @RequestParam("size") int pageSize,
      @RequestHeader Map<String, String> headers) throws UnauthorizedRequestException {
    String sessionId = headers.getOrDefault("sessionid", null);
    if (sessionId == null) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    String username = authenticationService.getUserNameFromSessionId(sessionId);
    if (username == null) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    Page<Project> projects = projectService.getAll(username, pageNum, pageSize);
    if (projects == null || projects.getTotalElements() == 0) {
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    return ResponseEntity.ok(projects);
  }

  @GetMapping("/projects/id/{id}")
  public ResponseEntity getOne(@PathVariable("id") String id,
      @RequestHeader Map<String, String> headers) throws UnauthorizedRequestException {
    String sessionId = headers.getOrDefault("sessionid", null);
    if (sessionId == null) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    String username = authenticationService.getUserNameFromSessionId(sessionId);
    if (username == null) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    Project project = projectService.getOne(username, id);
    if (project == null) {
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    return ResponseEntity.ok(new ProjectResponse(project));
  }

  @PostMapping("/projects")
  public ResponseEntity saveProject(@RequestHeader Map<String, String> headers,
      @RequestBody ProjectSaveRequest projectSaveRequest) throws UnauthorizedRequestException {
    if (ValidatorUtil.validate(projectSaveRequest, projectSaveRequest.getClass())) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    String sessionId = headers.getOrDefault("sessionid", null);
    if (sessionId == null) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    String username = authenticationService.getUserNameFromSessionId(sessionId);
    if (username == null) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    if (!projectSaveRequest.getProject().getOwner().equals(username)) {
      return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
    }
    Project savedProject = projectService.save(projectSaveRequest.getProject());
    if (savedProject == null) {
      return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
    } else {
      return new ResponseEntity(HttpStatus.CREATED);
    }
  }

  @PutMapping("/projects")
  public ResponseEntity updateProject(@RequestHeader Map<String, String> headers,
      @RequestBody ProjectUpdateRequest projectUpdateRequest) throws UnauthorizedRequestException {
    if (ValidatorUtil.validate(projectUpdateRequest, projectUpdateRequest.getClass())) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    String sessionId = headers.getOrDefault("sessionid", null);
    if (sessionId == null) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    String username = authenticationService.getUserNameFromSessionId(sessionId);
    if (username == null) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    if (!projectUpdateRequest.getProject().getOwner().equalsIgnoreCase(username)) {
      return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
    }
    projectService.update(username, projectUpdateRequest.getProject());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/projects/id/{id}")
  public ResponseEntity deleteProject(@PathVariable("id") String id,
      @RequestHeader Map<String, String> headers) throws UnauthorizedRequestException {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    String sessionId = headers.getOrDefault("sessionid", null);
    if (sessionId == null) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    String username = authenticationService.getUserNameFromSessionId(sessionId);
    if (username == null) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    Project project = projectService.getOne(username, id);
    if (project == null) {
      return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
    }
    long deleteCount = projectService.delete(username, id);
    if (deleteCount > 0) {
      return ResponseEntity.ok().build();
    } else {
      return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }
}

