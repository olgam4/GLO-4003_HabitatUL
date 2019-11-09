package ca.ulaval.glo4003.shared.domain.temporal;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.time.Clock;

public class Year extends ValueObject {
  private final java.time.Year value;

  private Year(java.time.Year value) {
    this.value = value;
  }

  public static Year now(Clock clock) {
    return new Year(java.time.Year.now(clock));
  }

  public static Year from(java.time.Year value) {
    return new Year(value);
  }

  public java.time.Year getValue() {
    return value;
  }
}
