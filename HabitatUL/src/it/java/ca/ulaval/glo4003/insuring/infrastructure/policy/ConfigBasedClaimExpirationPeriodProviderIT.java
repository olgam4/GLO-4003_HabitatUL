package ca.ulaval.glo4003.insuring.infrastructure.policy;

import ca.ulaval.glo4003.insuring.application.policy.ClaimExpirationPeriodProviderIT;
import ca.ulaval.glo4003.insuring.application.policy.claimexpiration.ClaimExpirationPeriodProvider;
import ca.ulaval.glo4003.insuring.infrastructure.policy.claimexpiration.ConfigBasedClaimExpirationPeriodProvider;

public class ConfigBasedClaimExpirationPeriodProviderIT extends ClaimExpirationPeriodProviderIT {
  @Override
  public ClaimExpirationPeriodProvider createSubject() {
    return new ConfigBasedClaimExpirationPeriodProvider();
  }
}
