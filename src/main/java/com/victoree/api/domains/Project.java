package com.victoree.api.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "document")
public class Project {
  private String id;
  private String owner;
  private String name;
  private String description;
  @JsonProperty("active")
  private boolean isActive;
}
