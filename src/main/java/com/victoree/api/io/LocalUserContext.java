package com.victoree.api.io;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalUserContext {

  private String username;
  private String authToken;
  private String permissionGroup;
  public LocalUserContext() {
    this.username = "guest";
    this.authToken = "guest";
    this.permissionGroup = "guest";
  }

}
