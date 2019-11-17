package ca.ulaval.glo4003.insuring.infrastructure.policy.modification;

import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationValidityPeriodProvider;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationValidityPeriodProviderIT;

public class ConfigBasedPolicyModificationValidityPeriodProviderIT
    extends PolicyModificationValidityPeriodProviderIT {
  @Override
  public PolicyModificationValidityPeriodProvider createSubject() {
    return new ConfigBasedPolicyModificationValidityPeriodProvider();
  }
}
