package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

public class PolicyView extends ValueObject {
  private final Period coveragePeriod;
  private final PolicyInformation policyInformation;
  private final CoverageDetails coverageDetails;
  private final PremiumDetails premiumDetails;

  public PolicyView(
      Period coveragePeriod,
      PolicyInformation policyInformation,
      CoverageDetails coverageDetails,
      PremiumDetails premiumDetails) {
    this.coveragePeriod = coveragePeriod;
    this.policyInformation = policyInformation;
    this.coverageDetails = coverageDetails;
    this.premiumDetails = premiumDetails;
  }

  public Period getCoveragePeriod() {
    return coveragePeriod;
  }

  public PolicyInformation getPolicyInformation() {
    return policyInformation;
  }

  public CoverageDetails getCoverageDetails() {
    return coverageDetails;
  }

  public PremiumDetails getPremiumDetails() {
    return premiumDetails;
  }
}
