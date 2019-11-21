package ca.ulaval.glo4003.insuring.application.policy.dto;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalStatus;
import ca.ulaval.glo4003.shared.application.DataTransferObject;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

public class PolicyRenewalDto extends DataTransferObject {
  private final PolicyRenewalId policyRenewalId;
  private final PolicyRenewalStatus status;
  private final Period coveragePeriod;
  private final CoverageDetails proposedCoverageDetails;
  private final PremiumDetails proposedPremiumDetails;

  public PolicyRenewalDto(
      PolicyRenewalId policyRenewalId,
      PolicyRenewalStatus status,
      Period coveragePeriod,
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails) {
    this.policyRenewalId = policyRenewalId;
    this.coveragePeriod = coveragePeriod;
    this.status = status;
    this.proposedCoverageDetails = proposedCoverageDetails;
    this.proposedPremiumDetails = proposedPremiumDetails;
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
