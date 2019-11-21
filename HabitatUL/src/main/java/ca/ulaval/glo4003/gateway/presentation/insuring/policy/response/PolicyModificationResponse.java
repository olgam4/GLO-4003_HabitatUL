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
  "status",
  "expirationDate",
  "proposedPremiumAdjustment",
  "proposedCoverageDetails",
  "proposedPremiumDetails"
})
public class PolicyModificationResponse {
  private PolicyModificationId policyModificationId;
  private PolicyModificationStatus status;
  private DateTime expirationDate;
  private Money proposedPremiumAdjustment;
  private CoverageDetails proposedCoverageDetails;
  private PremiumDetails proposedPremiumDetails;

  public PolicyModificationResponse(
      PolicyModificationId policyModificationId,
      PolicyModificationStatus status,
      DateTime expirationDate,
      Money proposedPremiumAdjustment,
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails) {
    this.policyModificationId = policyModificationId;
    this.status = status;
    this.expirationDate = expirationDate;
    this.proposedPremiumAdjustment = proposedPremiumAdjustment;
    this.proposedCoverageDetails = proposedCoverageDetails;
    this.proposedPremiumDetails = proposedPremiumDetails;
  }

  public PolicyModificationId getPolicyModificationId() {
    return policyModificationId;
  }

  public PolicyModificationStatus getStatus() {
    return status;
  }

  public DateTime getExpirationDate() {
    return expirationDate;
  }

  public Money getProposedPremiumAdjustment() {
    return proposedPremiumAdjustment;
  }

  public CoverageDetails getProposedCoverageDetails() {
    return proposedCoverageDetails;
  }

  public PremiumDetails getProposedPremiumDetails() {
    return proposedPremiumDetails;
  }
}
