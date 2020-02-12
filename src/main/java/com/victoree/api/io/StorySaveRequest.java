package com.victoree.api.io;

import com.victoree.api.domains.Story;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StorySaveRequest {

  private Story story;
}
