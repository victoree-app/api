package com.victoree.api.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "userdetail")
public class UserDetail {
  private String username;
  private String permissionGroup;

}
