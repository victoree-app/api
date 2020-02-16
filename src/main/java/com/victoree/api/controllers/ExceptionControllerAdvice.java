package com.victoree.api.controllers;

import com.victoree.api.exceptions.UnauthorizedLoginException;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {UnauthorizedRequestException.class, UnauthorizedLoginException.class})
  protected ResponseEntity handleUnathError(RuntimeException ex, WebRequest request) {
    String bodyOfResponse = ex.getMessage();
    return handleExceptionInternal(ex, bodyOfResponse,
        new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
  }
  //  to handle NullPointerException better.

}
