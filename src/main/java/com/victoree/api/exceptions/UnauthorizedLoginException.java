package com.victoree.api.exceptions;

public class UnauthorizedLoginException extends Throwable {

  public UnauthorizedLoginException(String msg) {
    super(msg);
  }
}
