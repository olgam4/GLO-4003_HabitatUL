package ca.ulaval.glo4003.insuring.domain.policy.modification;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.error.PolicyModificationNotFoundError;
import ca.ulaval.glo4003.insuring.domain.policy.modification.modifier.PolicyInformationModifier;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

import java.util.*;

import static ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationStatus.PENDING;

public class PolicyModificationsCoordinator {
  private Map<PolicyModificationId, PolicyModification> modifications;

  public PolicyModificationsCoordinator() {
    this(new HashMap<>());
  }

  public PolicyModificationsCoordinator(
      Map<PolicyModificationId, PolicyModification> modifications) {
    this.modifications = modifications;
  }

  public List<PolicyModification> getModifications() {
    return new ArrayList<>(modifications.values());
  }

  public PolicyModification getModification(PolicyModificationId policyModificationId) {
    return Optional.ofNullable(modifications.get(policyModificationId))
        .orElseThrow(() -> new PolicyModificationNotFoundError(policyModificationId));
  }

  public PolicyModification registerPolicyModification(
      PolicyInformationModifier policyInformationModifier,
      CoverageDetails proposedCoverageDetails,
      PremiumDetails currentPremiumDetails,
      PremiumDetails proposedPremiumDetails,
      PolicyModificationValidityPeriodProvider policyModificationValidityPeriodProvider,
      ClockProvider clockProvider) {
    PolicyModificationId policyModificationId = new PolicyModificationId();
    DateTime expirationDate =
        computeExpirationDate(policyModificationValidityPeriodProvider, clockProvider);
    Money premiumAdjustment =
        computePremiumAdjustment(currentPremiumDetails, proposedPremiumDetails);
    PolicyModification policyModification =
        new PolicyModification(
            policyModificationId,
            PENDING,
            expirationDate,
            premiumAdjustment,
            policyInformationModifier,
            proposedCoverageDetails,
            proposedPremiumDetails,
            clockProvider);
    modifications.put(policyModificationId, policyModification);
    return policyModification;
  }

  private DateTime computeExpirationDate(
      PolicyModificationValidityPeriodProvider policyModificationValidityPeriodProvider,
      ClockProvider clockProvider) {
    return DateTime.now(clockProvider.getClock())
        .plus(policyModificationValidityPeriodProvider.getPolicyModificationValidityPeriod());
  }

  private Money computePremiumAdjustment(
      PremiumDetails currentPremiumDetails, PremiumDetails updatedPremiumDetails) {
    Money currentTotalPremium = currentPremiumDetails.computeTotalPremium();
    Money updatedTotalPremium = updatedPremiumDetails.computeTotalPremium();
    return updatedTotalPremium.subtract(currentTotalPremium);
  }

  public PolicyModification retrieveConfirmedModification(
      PolicyModificationId policyModificationId) {
    updateModificationsStatus();
    PolicyModification policyModification = getModification(policyModificationId);
    confirmModification(policyModification);
    expirePendingModifications();
    return policyModification;
  }

  private void updateModificationsStatus() {
    modifications.values().stream().forEach(PolicyModification::updateStatus);
  }

  private void confirmModification(PolicyModification policyModification) {
    policyModification.confirm();
    modifications.put(policyModification.getPolicyModificationId(), policyModification);
  }

  private void expirePendingModifications() {
    modifications.values().stream().forEach(PolicyModification::expire);
  }
}
