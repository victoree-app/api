package com.victoree.api.controllers;

import com.victoree.api.domains.Epic;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import com.victoree.api.io.EpicSaveRequest;
import com.victoree.api.io.EpicUpdateRequest;
import com.victoree.api.services.AuthenticationService;
import com.victoree.api.services.EpicService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@CrossOrigin(origins = "*")
public class EpicController extends AbstractRestController {

  @Autowired
  private AuthenticationService authenticationService;

  @Autowired
  private EpicService epicService;

  @Override
  protected AuthenticationService getAuthenticationService() {
    return this.authenticationService;
  }

  @GetMapping("/epics")
  public ResponseEntity getAllEpics(@RequestParam("project") String projectId,
      @RequestParam("page") Integer page,
      @RequestParam("size") Integer size, @RequestHeader Map<String, String> headers)
      throws UnauthorizedRequestException {
    setHeaders(headers);
    Page<Epic> epics = epicService.getAllEpics(projectId, PageRequest.of(page, size));
    return ResponseEntity.ok(epics);
  }

  @PostMapping("/epics")
  public ResponseEntity saveEpic(@RequestHeader Map<String, String> headers,
      @RequestBody EpicSaveRequest epicSaveRequest) throws UnauthorizedRequestException {
    setHeaders(headers);
    Epic epicCreated = epicService.saveEpic(epicSaveRequest.getEpic());
    return ResponseEntity.ok(epicCreated);
  }

  @GetMapping("/epics/id/{id}")
  public ResponseEntity getEpicById(@RequestHeader Map<String, String> headers,
      @PathVariable("id") String id) throws UnauthorizedRequestException {
    setHeaders(headers);
    Epic epicRetrieved = epicService.findOne(id);
    return ResponseEntity.ok(epicRetrieved);
  }

  @PutMapping("/epics")
  public ResponseEntity updateEpic(@RequestHeader Map<String, String> headers,
      @RequestBody EpicUpdateRequest epicUpdateRequest)
      throws UnauthorizedRequestException {
    setHeaders(headers);
    long updateCount = epicService.update(epicUpdateRequest.getEpic());
    if (updateCount > 0) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.unprocessableEntity().build();
  }

  @DeleteMapping("/epics")
  public ResponseEntity deleteEpic(@RequestHeader Map<String, String> headers,
      @RequestParam("id") String id)
      throws UnauthorizedRequestException {
    setHeaders(headers);
    long deleteCount = epicService.delete(id);
    if (deleteCount > 0) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.unprocessableEntity().build();
  }


}
