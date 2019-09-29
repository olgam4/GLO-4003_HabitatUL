package ca.ulaval.glo4003.shared.infrastructure;

import ca.ulaval.glo4003.shared.domain.ClockProvider;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class FixedClockProvider implements ClockProvider {
  private Instant instant;
  private ZoneId zoneId;

  public FixedClockProvider() {
    this(Instant.now(), ZoneOffset.UTC);
  }

  public FixedClockProvider(Instant instant, ZoneId zoneId) {
    this.instant = instant;
    this.zoneId = zoneId;
  }

  @Override
  public Clock getClock() {
    return Clock.fixed(instant, zoneId);
  }
}
