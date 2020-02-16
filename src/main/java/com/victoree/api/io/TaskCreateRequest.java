package com.victoree.api.io;


import com.victoree.api.domains.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TaskCreateRequest {

  private Task task;
}
