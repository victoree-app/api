package com.victoree.api.domains;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "story")
public class Story {

  private String id;
  private String title;
  private String description;
  private String assignee;
  private boolean orphan;
  private String sprint;
  private List<String> tasks;
  private String epic;
}
