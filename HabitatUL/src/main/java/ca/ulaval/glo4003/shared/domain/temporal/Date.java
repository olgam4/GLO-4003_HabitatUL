package ca.ulaval.glo4003.shared.domain.temporal;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Period;

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

  public boolean isBefore(Date date) {
    return !isAfter(date);
  }

  public boolean isAfter(Date date) {
    return value.isAfter(date.value);
  }

  public Date plus(Period period) {
    return new Date(value.plus(period));
  }

  public Date minus(Period period) {
    return new Date(value.minus(period));
  }
}
