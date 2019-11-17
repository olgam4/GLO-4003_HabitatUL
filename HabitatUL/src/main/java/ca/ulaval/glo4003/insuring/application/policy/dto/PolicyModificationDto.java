package ca.ulaval.glo4003.insuring.application.policy.dto;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationStatus;
import ca.ulaval.glo4003.shared.application.DataTransferObject;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

public class PolicyModificationDto extends DataTransferObject {
  private final PolicyModificationId policyModificationId;
  private final DateTime expirationDate;
  private final PolicyModificationStatus status;
  private final Money premiumAdjustment;
  private final CoverageDetails proposedCoverageDetails;
  private final PremiumDetails proposedPremiumDetails;

  public PolicyModificationDto(
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
