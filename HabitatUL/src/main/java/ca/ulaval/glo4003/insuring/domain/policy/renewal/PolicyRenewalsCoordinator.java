package ca.ulaval.glo4003.insuring.domain.policy.renewal;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;

import java.util.HashMap;
import java.util.Map;

public class PolicyRenewalsCoordinator {
  private Map<PolicyRenewalId, PolicyRenewal> renewals;

  public PolicyRenewalsCoordinator() {
    this(new HashMap<>());
  }

  public PolicyRenewalsCoordinator(Map<PolicyRenewalId, PolicyRenewal> renewals) {
    this.renewals = renewals;
  }

  public PolicyRenewal registerPolicyRenewal(
      Date renewalEffectiveDate,
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails,
      PolicyCoveragePeriodProvider policyCoveragePeriodProvider,
      ClockProvider clockProvider) {
    return null;
  }
}
