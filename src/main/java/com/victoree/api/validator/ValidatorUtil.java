package com.victoree.api.validator;

import java.util.Objects;

public class ValidatorUtil {

  public static <T> boolean validate(T t, Class<? extends T> clazz) {
    return Objects.isNull(t);
  }
}
