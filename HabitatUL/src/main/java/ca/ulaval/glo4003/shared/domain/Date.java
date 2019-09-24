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

  public static Date nullDate() {
    return new Date(null);
  }

  public boolean isAfter(Date date) {
    if (value == null || date.value == null) return false;

    return value.isAfter(date.value);
  }

  public Date plus(Duration duration) {
    return new Date(value.plus(duration));
  }
}
