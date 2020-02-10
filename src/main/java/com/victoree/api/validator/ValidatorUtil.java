package com.victoree.api.validator;

import com.victoree.api.domains.LoginRequest;
import com.victoree.api.domains.ProjectSaveRequest;
import com.victoree.api.domains.ProjectUpdateRequest;
import java.util.Objects;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class ValidatorUtil {

  public static <T> boolean validate(T t,Class<? extends T> clazz) {
    return Objects.isNull(t);
  }
}
