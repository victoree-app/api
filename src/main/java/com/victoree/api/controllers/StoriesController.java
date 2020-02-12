package com.victoree.api.controllers;

import com.victoree.api.domains.Story;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import com.victoree.api.io.StorySaveRequest;
import com.victoree.api.services.AuthenticationService;
import com.victoree.api.services.StoriesService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:9000", maxAge = 3600, allowedHeaders = "*")
public class StoriesController extends AbstractRestController {

  @Autowired
  private StoriesService storiesService;

  @Autowired
  private AuthenticationService authenticationService;

  @GetMapping("/stories")
  public ResponseEntity getAllStories(@RequestHeader Map<String, String> headers,
      @RequestParam("page") int pageNum,
      @RequestParam("size") int pageSize,
      @RequestParam(value = "sort", defaultValue = "_id") String sortBy,
      @RequestParam(value = "order", defaultValue = "0") Integer order,
      @RequestParam(value = "linked", defaultValue = "false") Boolean linked)
      throws UnauthorizedRequestException {
    setHeaders(headers);
    Pageable pageable = PageRequest
        .of(pageNum, pageSize, order == 1 ? Direction.ASC : Direction.DESC, sortBy);
    Page<Story> stories = storiesService.getAll(getUsername(), linked, pageable);
    return ResponseEntity.ok(stories);
  }


  @PostMapping("/stories")
  public ResponseEntity saveStory(@RequestBody StorySaveRequest storySaveRequest,
      @RequestHeader Map<String, String> headers)
      throws UnauthorizedRequestException {
    setHeaders(headers);
    Story story = storiesService.save(storySaveRequest.getStory());
    return ResponseEntity.created(null).build();
  }

  @Override
  protected AuthenticationService getAuthenticationService() {
    return this.authenticationService;
  }
}
