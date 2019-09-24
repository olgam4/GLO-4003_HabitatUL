package ca.ulaval.glo4003.shared;

import java.time.Clock;

public interface ClockProvider {
  Clock getClock();
}
