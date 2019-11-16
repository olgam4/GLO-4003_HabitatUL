package ca.ulaval.glo4003.insuring.domain.policy.modification;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.modification.modifier.PolicyInformationModifier;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

public class PolicyModification {
  private PolicyModificationId policyModificationId;
  private DateTime expirationDate;
  private PolicyModificationState state;
  private Money premiumAdjustment;
  private PolicyInformationModifier policyInformationModifier;
  private CoverageDetails coverageDetails;
  private PremiumDetails premiumDetails;

  public PolicyModification(
      PolicyModificationId policyModificationId,
      DateTime expirationDate,
      PolicyModificationState state,
      Money premiumAdjustment,
      PolicyInformationModifier policyInformationModifier,
      CoverageDetails coverageDetails,
      PremiumDetails premiumDetails) {
    this.policyModificationId = policyModificationId;
    this.expirationDate = expirationDate;
    this.state = state;
    this.premiumAdjustment = premiumAdjustment;
    this.policyInformationModifier = policyInformationModifier;
    this.coverageDetails = coverageDetails;
    this.premiumDetails = premiumDetails;
  }

  public DateTime getExpirationDate() {
    return expirationDate;
  }

  public PolicyModificationState getState() {
    return state;
  }

  public Money getPremiumAdjustment() {
    return premiumAdjustment;
  }
}
