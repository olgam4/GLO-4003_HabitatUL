package ca.ulaval.glo4003.insuring.domain.policy.modification;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.modification.modifier.PolicyInformationModifier;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

public class PolicyModification {
  private PolicyModificationId policyModificationId;
  private DateTime expirationDate;
  private PolicyModificationStatus status;
  private Money premiumAdjustment;
  private PolicyInformationModifier policyInformationModifier;
  private CoverageDetails proposedCoverageDetails;
  private PremiumDetails proposedPremiumDetails;

  public PolicyModification(
      PolicyModificationId policyModificationId,
      DateTime expirationDate,
      PolicyModificationStatus status,
      Money premiumAdjustment,
      PolicyInformationModifier policyInformationModifier,
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails) {
    this.policyModificationId = policyModificationId;
    this.expirationDate = expirationDate;
    this.status = status;
    this.premiumAdjustment = premiumAdjustment;
    this.policyInformationModifier = policyInformationModifier;
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

  public PolicyInformationModifier getPolicyInformationModifier() {
    return policyInformationModifier;
  }

  public CoverageDetails getProposedCoverageDetails() {
    return proposedCoverageDetails;
  }

  public PremiumDetails getProposedPremiumDetails() {
    return proposedPremiumDetails;
  }
}
