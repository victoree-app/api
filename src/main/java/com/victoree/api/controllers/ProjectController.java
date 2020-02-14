package com.victoree.api.controllers;

import com.victoree.api.domains.Project;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import com.victoree.api.io.ProjectResponse;
import com.victoree.api.io.ProjectSaveRequest;
import com.victoree.api.io.ProjectUpdateRequest;
import com.victoree.api.services.AuthenticationService;
import com.victoree.api.services.ProjectService;
import com.victoree.api.validator.ValidatorUtil;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class ProjectController extends AbstractRestController {

  @Autowired
  private ProjectService projectService;

  @Autowired
  private AuthenticationService authenticationService;

  @GetMapping("/projects")
  public ResponseEntity getAllPaged(@RequestParam("page") int pageNum,
      @RequestParam("size") int pageSize,
      @RequestParam(value = "sort", required = false) String sortBy,
      @RequestParam(value = "order", required = false) Integer order,
      @RequestHeader Map<String, String> headers) throws UnauthorizedRequestException {
    setHeaders(headers);
    Page<Project> projects = projectService.getAll(getUsername(), pageNum, pageSize, sortBy, order);
    if (projects == null || projects.getTotalElements() == 0) {
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    return ResponseEntity.ok(projects);
  }

  @GetMapping("/projects/all")
  public ResponseEntity getAll(@RequestHeader Map<String, String> headers)
      throws UnauthorizedRequestException {
    setHeaders(headers);
    List<Project> projects = projectService.getAll(getUsername());
    return ResponseEntity.ok(projects);
  }

  @GetMapping("/projects/id/{id}")
  public ResponseEntity getOne(@PathVariable("id") String id,
      @RequestHeader Map<String, String> headers) throws UnauthorizedRequestException {
    setHeaders(headers);
    Project project = projectService.getOne(getUsername(), id);
    if (project == null) {
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    return ResponseEntity.ok(new ProjectResponse(project));
  }

  @PostMapping("/projects")
  public ResponseEntity saveProject(@RequestHeader Map<String, String> headers,
      @RequestBody ProjectSaveRequest projectSaveRequest) throws UnauthorizedRequestException {
    setHeaders(headers);
    if (ValidatorUtil.validate(projectSaveRequest, projectSaveRequest.getClass())) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    if (!projectSaveRequest.getProject().getOwner().equals(getUsername())) {
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
    setHeaders(headers);
    if (ValidatorUtil.validate(projectUpdateRequest, projectUpdateRequest.getClass()) ||
        !projectUpdateRequest.getProject().getOwner().equalsIgnoreCase(getUsername())) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    projectService.update(getUsername(), projectUpdateRequest.getProject());
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/projects/id/{id}")
  public ResponseEntity deleteProject(@PathVariable("id") String id,
      @RequestHeader Map<String, String> headers) throws UnauthorizedRequestException {
    setHeaders(headers);
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    Project project = projectService.getOne(getUsername(), id);
    if (project == null) {
      return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
    }
    long deleteCount = projectService.delete(getUsername(), id);
    if (deleteCount > 0) {
      return ResponseEntity.ok().build();
    } else {
      return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  @Override
  protected AuthenticationService getAuthenticationService() {
    return this.authenticationService;
  }
}

