package ca.ulaval.glo4003.management.domain.user.token;

import java.time.Duration;

public interface TokenValidityPeriodProvider {
  Duration getTokenValidityPeriod();
}
