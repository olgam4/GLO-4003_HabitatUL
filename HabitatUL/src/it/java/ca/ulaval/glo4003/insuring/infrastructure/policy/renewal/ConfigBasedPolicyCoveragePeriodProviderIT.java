package ca.ulaval.glo4003.insuring.infrastructure.policy.renewal;

import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyCoveragePeriodProvider;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyCoveragePeriodProviderIT;

public class ConfigBasedPolicyCoveragePeriodProviderIT extends PolicyCoveragePeriodProviderIT {
  @Override
  public PolicyCoveragePeriodProvider createSubject() {
    return new ConfigBasedPolicyCoveragePeriodProvider();
  }
}
