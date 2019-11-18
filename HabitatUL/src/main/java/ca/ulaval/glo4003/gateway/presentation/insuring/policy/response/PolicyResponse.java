package ca.ulaval.glo4003.gateway.presentation.insuring.policy.response;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("checkstyle:Indentation")
@JsonPropertyOrder({
  "policyId",
  "coveragePeriod",
  "totalPremium",
  "coverageDetails",
  "premiumDetails"
})
public class PolicyResponse {
  private PolicyId policyId;
  private Period coveragePeriod;
  private Money totalPremium;
  private CoverageDetails coverageDetails;
  private PremiumDetails premiumDetails;

  public PolicyResponse(
      PolicyId policyId,
      Period coveragePeriod,
      Money totalPremium,
      CoverageDetails coverageDetails,
      PremiumDetails premiumDetails) {
    this.policyId = policyId;
    this.coveragePeriod = coveragePeriod;
    this.totalPremium = totalPremium;
    this.coverageDetails = coverageDetails;
    this.premiumDetails = premiumDetails;
  }

  public PolicyId getPolicyId() {
    return policyId;
  }

  public Period getCoveragePeriod() {
    return coveragePeriod;
  }

  public Money getTotalPremium() {
    return totalPremium;
  }

  public CoverageDetails getCoverageDetails() {
    return coverageDetails;
  }

  public PremiumDetails getPremiumDetails() {
    return premiumDetails;
  }
}
