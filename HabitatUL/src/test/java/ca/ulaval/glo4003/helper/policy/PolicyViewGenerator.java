package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.insuring.domain.policy.PolicyView;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.policy.PolicyInformationGenerator.createPolicyInformation;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createPeriod;

public class PolicyViewGenerator {
  private PolicyViewGenerator() {}

  public static PolicyView createPolicyView() {
    return new PolicyView(
        createPeriod(), createPolicyInformation(), createCoverageDetails(), createPremiumDetails());
  }
}
