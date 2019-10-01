package ca.ulaval.glo4003.shared.domain;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

  public long toUnixEpochTimestamp() {
    return value.toInstant(ZoneOffset.UTC).getEpochSecond();
  }

  public boolean isAfter(DateTime dateTime) {
    return value.isAfter(dateTime.value);
  }

  public DateTime plus(Duration duration) {
    return new DateTime(value.plus(duration));
  }
}
