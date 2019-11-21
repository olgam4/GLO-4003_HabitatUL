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
  private final PolicyModificationStatus status;
  private final DateTime expirationDate;
  private final Money proposedPremiumAdjustment;
  private final CoverageDetails proposedCoverageDetails;
  private final PremiumDetails proposedPremiumDetails;

  public PolicyModificationDto(
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
