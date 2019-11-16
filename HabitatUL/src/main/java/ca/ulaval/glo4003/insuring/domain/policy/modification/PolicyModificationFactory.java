package ca.ulaval.glo4003.insuring.domain.policy.modification;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.modification.modifier.PolicyInformationModifier;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

import static ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationState.PENDING;

public class PolicyModificationFactory {
  private PolicyModificationValidityPeriodProvider policyModificationValidityPeriodProvider;
  private ClockProvider clockProvider;

  public PolicyModificationFactory(
      PolicyModificationValidityPeriodProvider policyModificationValidityPeriodProvider,
      ClockProvider clockProvider) {
    this.policyModificationValidityPeriodProvider = policyModificationValidityPeriodProvider;
    this.clockProvider = clockProvider;
  }

  public PolicyModification create(
      CoverageDetails updatedCoverageDetails,
      PremiumDetails currentPremiumDetails,
      PremiumDetails updatedPremiumDetails,
      PolicyInformationModifier policyInformationModifier) {
    PolicyModificationId policyModificationId = new PolicyModificationId();
    DateTime expirationDate = computeExpirationDate();
    Money premiumAdjustment =
        computePremiumAdjustment(currentPremiumDetails, updatedPremiumDetails);
    return new PolicyModification(
        policyModificationId,
        expirationDate,
        PENDING,
        premiumAdjustment,
        policyInformationModifier,
        updatedCoverageDetails,
        updatedPremiumDetails);
  }

  private DateTime computeExpirationDate() {
    return DateTime.now(clockProvider.getClock())
        .plus(policyModificationValidityPeriodProvider.getPolicyModificationValidityPeriod());
  }

  private Money computePremiumAdjustment(
      PremiumDetails currentPremiumDetails, PremiumDetails updatedPremiumDetails) {
    Money currentTotalPremium = currentPremiumDetails.computeTotalPremium();
    Money updatedTotalPremium = updatedPremiumDetails.computeTotalPremium();
    return updatedTotalPremium.subtract(currentTotalPremium);
  }
}
