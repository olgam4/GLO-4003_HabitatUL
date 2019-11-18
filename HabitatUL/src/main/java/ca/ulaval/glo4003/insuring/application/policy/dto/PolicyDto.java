package ca.ulaval.glo4003.insuring.application.policy.dto;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.shared.application.DataTransferObject;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

public class PolicyDto extends DataTransferObject {
  private final PolicyId policyId;
  private final Period coveragePeriod;
  private final CoverageDetails coverageDetails;
  private final PremiumDetails premiumDetails;

  public PolicyDto(
      PolicyId policyId,
      Period coveragePeriod,
      CoverageDetails coverageDetails,
      PremiumDetails premiumDetails) {
    this.policyId = policyId;
    this.coveragePeriod = coveragePeriod;
    this.coverageDetails = coverageDetails;
    this.premiumDetails = premiumDetails;
  }

  public PolicyId getPolicyId() {
    return policyId;
  }

  public Period getCoveragePeriod() {
    return coveragePeriod;
  }

  public CoverageDetails getCoverageDetails() {
    return coverageDetails;
  }

  public PremiumDetails getPremiumDetails() {
    return premiumDetails;
  }
}
