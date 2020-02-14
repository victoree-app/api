package com.victoree.api.io;

import com.victoree.api.domains.Epic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EpicUpdateRequest {

  private Epic epic;
}
