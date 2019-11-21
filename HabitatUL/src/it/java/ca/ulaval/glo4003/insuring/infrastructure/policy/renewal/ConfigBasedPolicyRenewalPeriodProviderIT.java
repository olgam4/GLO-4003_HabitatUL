package ca.ulaval.glo4003.insuring.infrastructure.policy.renewal;

import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalPeriodProvider;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalPeriodProviderIT;

public class ConfigBasedPolicyRenewalPeriodProviderIT extends PolicyRenewalPeriodProviderIT {
  @Override
  public PolicyRenewalPeriodProvider createSubject() {
    return new ConfigBasedPolicyRenewalPeriodProvider();
  }
}
