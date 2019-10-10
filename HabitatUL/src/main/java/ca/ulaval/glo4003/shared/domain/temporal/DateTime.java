package ca.ulaval.glo4003.shared.domain.temporal;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

public class DateTime extends ValueObject {
  private LocalDateTime value;

  private DateTime(LocalDateTime value) {
    this.value = value;
  }

  public static DateTime now(Clock clock) {
    return new DateTime(LocalDateTime.now(clock));
  }

  public static DateTime from(LocalDateTime value) {
    return new DateTime(value);
  }

  public LocalDateTime getValue() {
    return value;
  }

  public boolean isAfter(DateTime dateTime) {
    return value.isAfter(dateTime.value);
  }

  public DateTime plus(Duration duration) {
    return new DateTime(value.plus(duration));
  }
}
