package ca.ulaval.glo4003.gateway.presentation.insuring.policy.response;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationStatus;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("checkstyle:Indentation")
@JsonPropertyOrder({
  "policyModificationId",
  "expirationDate",
  "status",
  "premiumAdjustment",
  "proposedCoverageDetails",
  "proposedPremiumDetails"
})
public class PolicyModificationResponse {
  private PolicyModificationId policyModificationId;
  private DateTime expirationDate;
  private PolicyModificationStatus status;
  private Money premiumAdjustment;
  private CoverageDetails proposedCoverageDetails;
  private PremiumDetails proposedPremiumDetails;

  public PolicyModificationResponse(
      PolicyModificationId policyModificationId,
      DateTime expirationDate,
      PolicyModificationStatus status,
      Money premiumAdjustment,
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails) {
    this.policyModificationId = policyModificationId;
    this.expirationDate = expirationDate;
    this.status = status;
    this.premiumAdjustment = premiumAdjustment;
    this.proposedCoverageDetails = proposedCoverageDetails;
    this.proposedPremiumDetails = proposedPremiumDetails;
  }

  public PolicyModificationId getPolicyModificationId() {
    return policyModificationId;
  }

  public DateTime getExpirationDate() {
    return expirationDate;
  }

  public PolicyModificationStatus getStatus() {
    return status;
  }

  public Money getPremiumAdjustment() {
    return premiumAdjustment;
  }

  public CoverageDetails getProposedCoverageDetails() {
    return proposedCoverageDetails;
  }

  public PremiumDetails getProposedPremiumDetails() {
    return proposedPremiumDetails;
  }
}
