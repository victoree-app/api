package com.victoree.api.controllers;

import com.victoree.api.exceptions.UnauthorizedRequestException;
import com.victoree.api.services.AuthenticationService;
import java.util.Map;

public abstract class AbstractRestController {

  private Map<String, String> headers;
  private String sessionId;
  private String username;


  protected String getUsername() {
    return username;
  }

  protected void setHeaders(Map<String, String> headers) throws UnauthorizedRequestException {
    this.headers = headers;
    if (sessionId == null || sessionId.isEmpty()) {
      sessionId = this.headers.getOrDefault("sessionid", null);
      if (sessionId == null) {
        throw new UnauthorizedRequestException("session id does not exist");
      }
    }

    if (username == null) {
      username = getAuthenticationService().getUserNameFromSessionId(sessionId);
      if (username == null) {
        throw new UnauthorizedRequestException("no username exists for session id");
      }
    }
  }

  protected String getSessionId() {
    return this.sessionId;
  }

  protected abstract AuthenticationService getAuthenticationService();

}
