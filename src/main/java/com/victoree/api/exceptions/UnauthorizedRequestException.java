package com.victoree.api.exceptions;

public class UnauthorizedRequestException extends Throwable {

  public UnauthorizedRequestException(String message) {
    super(message);
  }

  public UnauthorizedRequestException() {
    super();
  }
}
