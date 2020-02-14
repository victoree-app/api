package com.victoree.api.domains;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Sprint {

  private String id;
  private LocalDate startdate;
  private LocalDate enddate;
  private String sprinttheme;
  private List<String> stories;
  private String goal;
}

