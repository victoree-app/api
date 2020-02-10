package com.victoree.api.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class LocalUserContext {

  public LocalUserContext() {
    this.username = "guest";
    this.authToken = "guest";
    this.permissionGroup = "guest";
  }
  private String username;
  private String authToken;
  private String permissionGroup;

}
