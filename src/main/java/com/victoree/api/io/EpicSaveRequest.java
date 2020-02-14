package com.victoree.api.io;

import com.victoree.api.domains.Epic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EpicSaveRequest {

  private Epic epic;
}
