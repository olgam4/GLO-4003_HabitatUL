package ca.ulaval.glo4003.insuring.application.policy.claimexpiration;

import java.time.Duration;

public interface ClaimExpirationPeriodProvider {
  Duration getClaimExpirationPeriod();
}
