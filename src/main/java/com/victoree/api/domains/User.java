package com.victoree.api.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {
  private String name;
  private String username;
  private String status;
}
