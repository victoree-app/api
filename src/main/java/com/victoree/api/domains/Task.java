package com.victoree.api.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Task {

  private String id;
  private String storyid;
  private String title;
  private String description;
  private Status status;
}
