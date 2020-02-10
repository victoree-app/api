package com.victoree.api.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public abstract class ProjectRequest {
  private Project project;
}
