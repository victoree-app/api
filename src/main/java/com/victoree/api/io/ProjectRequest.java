package com.victoree.api.io;

import com.victoree.api.domains.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public abstract class ProjectRequest {

  private Project project;
}
