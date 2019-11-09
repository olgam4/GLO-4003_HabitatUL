package ca.ulaval.glo4003.shared.infrastructure.temporal;

import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;

import java.time.Clock;

public class SystemDefaultZoneClockProvider implements ClockProvider {
  @Override
  public Clock getClock() {
    return Clock.systemDefaultZone();
  }
}
