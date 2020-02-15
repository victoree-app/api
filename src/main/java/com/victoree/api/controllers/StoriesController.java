package com.victoree.api.controllers;

import com.victoree.api.domains.Story;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import com.victoree.api.io.StoryResponse;
import com.victoree.api.io.StorySaveRequest;
import com.victoree.api.io.StoryUpdateRequest;
import com.victoree.api.services.AuthenticationService;
import com.victoree.api.services.StoriesService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
public class StoriesController extends AbstractRestController {

  @Autowired
  private StoriesService storiesService;

  @Autowired
  private AuthenticationService authenticationService;

  @GetMapping("/stories")
  public ResponseEntity getAllStoriesPaged(@RequestHeader Map<String, String> headers,
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

  @GetMapping("/stories/all")
  public ResponseEntity getAllStories(@RequestHeader Map<String, String> headers)
      throws UnauthorizedRequestException {
    setHeaders(headers);
    boolean linked = false;
    List<Story> stories = storiesService.getAll(getUsername(), linked);
    return ResponseEntity.ok(stories);
  }

  @GetMapping("/stories/id/{id}")
  public ResponseEntity getStory(@RequestHeader Map<String, String> headers,
      @PathVariable("id") String id) throws UnauthorizedRequestException {
    setHeaders(headers);
    Story story = storiesService.getOne(id);
    return ResponseEntity.ok(new StoryResponse(story));
  }

  @GetMapping("/stories/list")
  public ResponseEntity getStoriesFiltered(@RequestHeader Map<String, String> headers,
      @RequestParam("epicid") String epicid) throws UnauthorizedRequestException {
    setHeaders(headers);
    List<Story> stories = storiesService.getStoriesFiltered(epicid);
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

  @PutMapping("/stories")
  public ResponseEntity editStory(@RequestHeader Map<String, String> headers,
      @RequestBody StoryUpdateRequest storyUpdateRequest)
      throws UnauthorizedRequestException {
    setHeaders(headers);
    Story story = storiesService.getOne(storyUpdateRequest.getStory().getId());
    if (story == null) {
      return ResponseEntity.unprocessableEntity().build();
    }
    long count = storiesService.update(storyUpdateRequest.getStory());
    if (count != 0) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.unprocessableEntity().build();
  }

  @DeleteMapping("/stories")
  public ResponseEntity deleteStory(@RequestHeader Map<String, String> headers,
      @RequestParam("id") String id) throws UnauthorizedRequestException {
    setHeaders(headers);
    if (id == null) {
      return ResponseEntity.badRequest().build();
    }
    Story story = storiesService.getOne(id);
    if (story == null) {
      return ResponseEntity.unprocessableEntity().build();
    }
    long count = storiesService.delete(id);
    if (count > 0) {
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.unprocessableEntity().build();
  }

  @Override
  protected AuthenticationService getAuthenticationService() {
    return this.authenticationService;
  }
}
