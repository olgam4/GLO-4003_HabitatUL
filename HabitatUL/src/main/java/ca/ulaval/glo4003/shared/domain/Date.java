package ca.ulaval.glo4003.shared.domain;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;

public class Date extends ValueObject {
  private LocalDate value;

  private Date(LocalDate value) {
    this.value = value;
  }

  public static Date now(Clock clock) {
    return new Date(LocalDate.now(clock));
  }

  public static Date from(LocalDate value) {
    return new Date(value);
  }

  public LocalDate getValue() {
    return value;
  }

  public boolean isAfter(Date dateTime) {
    return value.isAfter(dateTime.value);
  }

  public Date plus(Duration duration) {
    return new Date(value.plus(duration));
  }
}
