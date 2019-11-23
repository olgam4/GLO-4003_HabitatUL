package ca.ulaval.glo4003.insuring.application.policy;

import java.time.Duration;

public interface ClaimExpirationPeriodProvider {
  Duration getClaimExpirationPeriod();
}
