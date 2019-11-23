package ca.ulaval.glo4003.insuring.infrastructure.policy;

import ca.ulaval.glo4003.insuring.application.policy.ClaimExpirationPeriodProvider;
import ca.ulaval.glo4003.insuring.application.policy.ClaimExpirationPeriodProviderIT;

public class ConfigBasedClaimExpirationPeriodProviderIT extends ClaimExpirationPeriodProviderIT {
  @Override
  public ClaimExpirationPeriodProvider createSubject() {
    return new ConfigBasedClaimExpirationPeriodProvider();
  }
}
