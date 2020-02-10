package com.victoree.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.xsom.impl.UName;
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

  //create
  //update
  //delete
  //list
  //search

  @Autowired
  private ProjectService projectService;

  @Autowired
  private AuthenticationService authenticationService;

  @Value("${test.value}")
  private String value;

  @GetMapping("/projects")
  public ResponseEntity getAll(@RequestParam("page") int pageNum,
      @RequestParam("size") int pageSize,
      @RequestHeader Map<String, String> headers) {
    String sessionId = headers.getOrDefault("sessionid", null);
    if (sessionId == null) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    try {
      String userName = authenticationService.getUserNameFromSessionId(sessionId);
      if (userName == null) {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
      }
      Page<Project> projects = projectService.getAll(userName, pageNum, pageSize);
      if (projects == null || projects.getTotalElements() == 0) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
      }
      return ResponseEntity.ok(projects);
    } catch (UnauthorizedRequestException e) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
  }

  @GetMapping("/projects/id/{id}")
  public ResponseEntity getOne (@PathVariable("id") String id,
      @RequestHeader Map<String, String> headers) {
    String sessionId = headers.getOrDefault("sessionid", null);
    if (sessionId == null) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    try {
      String userName = authenticationService.getUserNameFromSessionId(sessionId);
      if (userName == null) {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
      }
      Project project = projectService.getOne(userName, id);
      if(project == null) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
      }
      System.out.println(new ObjectMapper().writeValueAsString(project));
      return ResponseEntity.ok(new ProjectResponse(project));
    } catch (UnauthorizedRequestException | JsonProcessingException e) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping("/projects")
  public ResponseEntity saveProject(@RequestHeader Map<String, String> headers,
      @RequestBody ProjectSaveRequest projectSaveRequest) {
    if(ValidatorUtil.validate(projectSaveRequest, projectSaveRequest.getClass())) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    String sessionId = headers.getOrDefault("sessionid", null);
    if (sessionId == null) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    try {
      String userName = authenticationService.getUserNameFromSessionId(sessionId);
      if (userName == null) {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
      }
      if (!projectSaveRequest.getProject().getOwner().equals(userName)) {
        return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
      }
      Project savedProject = projectService.save(projectSaveRequest.getProject());
      if (savedProject == null) {
        return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
      } else {
        return new ResponseEntity(HttpStatus.CREATED);
      }
    } catch (UnauthorizedRequestException e) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
  }

  @PutMapping("/projects")
  public ResponseEntity updateProject(@RequestHeader Map<String, String> headers,
      @RequestBody ProjectUpdateRequest projectUpdateRequest) {
    if(ValidatorUtil.validate(projectUpdateRequest, projectUpdateRequest.getClass())) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    String sessionId = headers.getOrDefault("sessionid", null);
    if (sessionId == null) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    try {
      String userName = authenticationService.getUserNameFromSessionId(sessionId);
      if (userName == null) {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
      }
      if (!projectUpdateRequest.getProject().getOwner().equalsIgnoreCase(userName)) {
        return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
      }
      projectService.update(userName, projectUpdateRequest.getProject());
      return ResponseEntity.ok().build();
    } catch (UnauthorizedRequestException e) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
  }

  @PostMapping("/projects/id/{id}")
  public ResponseEntity deleteProject(@PathVariable("id") String id, @RequestHeader Map<String,String> headers) {
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    String sessionId = headers.getOrDefault("sessionid", null);
    if (sessionId == null) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
    try {
      String userName = authenticationService.getUserNameFromSessionId(sessionId);
      if (userName == null) {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
      }
      Project project = projectService.getOne(userName,id);
      if(project == null) {
        return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
      }
      long deleteCount = projectService.delete(userName, id);
      if(deleteCount > 0) {
        return ResponseEntity.ok().build();
      } else {
        return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
      }
    }catch (UnauthorizedRequestException e) {
      return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
  }
}

