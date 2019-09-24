package ca.ulaval.glo4003.shared.domain;

import java.time.Clock;

public interface ClockProvider {
  Clock getClock();
}
