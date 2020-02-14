package com.victoree.api.domains;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Epic {

  private String id;
  private String name;
  private LocalDate startdate;
  private String startsprint;
  private List<String> associatedstories;
  private Status status;
  private String projectid;


}
