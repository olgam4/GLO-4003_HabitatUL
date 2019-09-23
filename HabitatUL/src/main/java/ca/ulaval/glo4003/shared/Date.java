package ca.ulaval.glo4003.shared;

import java.time.Duration;
import java.time.LocalDateTime;

public class Date extends ValueObject {
  private LocalDateTime value;

  public Date(LocalDateTime value) {
    this.value = value;
  }

  public static Date now() {
    return new Date(LocalDateTime.now());
  }

  public static Date nullDate() {
    return new Date(null);
  }

  public boolean isAfter(Date date) {
    if (value == null) return false;

    return value.isAfter(date.value);
  }

  public Date plus(Duration duration) {
    return new Date(value.plus(duration));
  }
}
