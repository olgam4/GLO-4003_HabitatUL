package ca.ulaval.glo4003.gateway.presentation.insuring.policy.response;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalStatus;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("checkstyle:Indentation")
@JsonPropertyOrder({
  "policyRenewalId",
  "status",
  "coveragePeriod",
  "proposedTotalPremium",
  "proposedCoverageDetails",
  "proposedPremiumDetails"
})
public class PolicyRenewalResponse {
  private PolicyRenewalId policyRenewalId;
  private PolicyRenewalStatus status;
  private Period coveragePeriod;
  private Money proposedTotalPremium;
  private CoverageDetails proposedCoverageDetails;
  private PremiumDetails proposedPremiumDetails;

  public PolicyRenewalResponse(
      PolicyRenewalId policyRenewalId,
      PolicyRenewalStatus status,
      Period coveragePeriod,
      Money proposedTotalPremium,
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails) {
    this.policyRenewalId = policyRenewalId;
    this.status = status;
    this.coveragePeriod = coveragePeriod;
    this.proposedTotalPremium = proposedTotalPremium;
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

  public Money getProposedTotalPremium() {
    return proposedTotalPremium;
  }

  public CoverageDetails getProposedCoverageDetails() {
    return proposedCoverageDetails;
  }

  public PremiumDetails getProposedPremiumDetails() {
    return proposedPremiumDetails;
  }
}
