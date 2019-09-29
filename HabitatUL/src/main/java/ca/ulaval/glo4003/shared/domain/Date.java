package ca.ulaval.glo4003.shared.domain;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

public class Date extends ValueObject {
  private LocalDateTime value;

  private Date(LocalDateTime value) {
    this.value = value;
  }

  public static Date now(Clock clock) {
    return new Date(LocalDateTime.now(clock));
  }

  public static Date from(LocalDateTime value) {
    return new Date(value);
  }

  public LocalDateTime getValue() {
    return value;
  }

  public boolean isAfter(Date date) {
    return value.isAfter(date.value);
  }

  public Date plus(Duration duration) {
    return new Date(value.plus(duration));
  }
}
