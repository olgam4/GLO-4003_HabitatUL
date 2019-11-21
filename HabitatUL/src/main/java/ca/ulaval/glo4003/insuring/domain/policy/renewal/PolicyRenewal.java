package ca.ulaval.glo4003.insuring.domain.policy.renewal;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

public class PolicyRenewal {
  private PolicyRenewalId policyRenewalId;
  private PolicyRenewalStatus status;
  private Period coveragePeriod;
  private CoverageDetails proposedCoverageDetails;
  private PremiumDetails proposedPremiumDetails;
  private ClockProvider clockProvider;

  public PolicyRenewal(
      PolicyRenewalId policyRenewalId,
      PolicyRenewalStatus status,
      Period coveragePeriod,
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails,
      ClockProvider clockProvider) {
    this.policyRenewalId = policyRenewalId;
    this.status = status;
    this.coveragePeriod = coveragePeriod;
    this.proposedCoverageDetails = proposedCoverageDetails;
    this.proposedPremiumDetails = proposedPremiumDetails;
    this.clockProvider = clockProvider;
  }

  public PolicyRenewalId getPolicyRenewalId() {
    return policyRenewalId;
  }

  public PolicyRenewalStatus getStatus() {
    return status;
  }

  public Period getCoveragePeriod() {
    return coveragePeriod;
  }

  public CoverageDetails getProposedCoverageDetails() {
    return proposedCoverageDetails;
  }

  public PremiumDetails getProposedPremiumDetails() {
    return proposedPremiumDetails;
  }
}
