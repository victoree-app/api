package com.victoree.api.controllers;

import com.victoree.api.domains.Task;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import com.victoree.api.io.TaskCreateRequest;
import com.victoree.api.io.TaskUpdateRequest;
import com.victoree.api.services.AuthenticationService;
import com.victoree.api.services.TaskService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
public class TaskController extends AbstractRestController {

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private TaskService taskService;

  @GetMapping
  public ResponseEntity getAllTasksByStoryId(@RequestParam("storyid") String storyid,
      @RequestHeader Map<String, String> headers)
      throws UnauthorizedRequestException {
    setHeaders(headers);
    List<Task> tasks = taskService.getAllTasks(storyid);
    return ResponseEntity.ok(tasks);
  }

  @GetMapping("/id")
  public ResponseEntity getAllTasksById(@RequestParam("id") String id,
      @RequestHeader Map<String, String> headers) throws UnauthorizedRequestException {
    setHeaders(headers);
    List<Task> tasks = taskService.getAllTasksById(id);
    return ResponseEntity.ok(tasks);
  }

  @PutMapping
  public ResponseEntity updateTask(@RequestBody TaskUpdateRequest taskUpdateRequest,
      @RequestHeader Map<String, String> headers) throws UnauthorizedRequestException {
    setHeaders(headers);
    long updateCount = taskService.update(taskUpdateRequest.getTask());
    if (updateCount > 0) {
      return ResponseEntity.ok(updateCount);
    } else {
      return ResponseEntity.unprocessableEntity().build();
    }
  }


  @DeleteMapping
  public ResponseEntity deleteTaskById(@RequestParam("id") String taskId,
      @RequestHeader Map<String, String> headers)
      throws UnauthorizedRequestException {
    setHeaders(headers);
    long deleteCount = taskService.delete(taskId);
    if (deleteCount > 0) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.unprocessableEntity().build();
  }

  @PostMapping
  public ResponseEntity saveTask(@RequestHeader Map<String, String> headers,
      @RequestBody TaskCreateRequest taskCreateRequest) throws UnauthorizedRequestException {
    setHeaders(headers);
    Task taskSaved = taskService.create(taskCreateRequest.getTask());
    return ResponseEntity.created(null).build();
  }

  @Override
  protected AuthenticationService getAuthenticationService() {
    return this.authenticationService;
  }
}
