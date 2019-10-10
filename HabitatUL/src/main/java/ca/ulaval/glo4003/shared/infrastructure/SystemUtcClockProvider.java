package ca.ulaval.glo4003.shared.infrastructure;

import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;

import java.time.Clock;

public class SystemUtcClockProvider implements ClockProvider {
  @Override
  public Clock getClock() {
    return Clock.systemUTC();
  }
}
