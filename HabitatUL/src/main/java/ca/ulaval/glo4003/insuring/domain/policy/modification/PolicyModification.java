package ca.ulaval.glo4003.insuring.domain.policy.modification;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyView;
import ca.ulaval.glo4003.insuring.domain.policy.modification.modifier.PolicyInformationModifier;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import static ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationStatus.*;

public class PolicyModification {
  private PolicyModificationId policyModificationId;
  private DateTime expirationDate;
  private PolicyModificationStatus status;
  private Money premiumAdjustment;
  private PolicyInformationModifier policyInformationModifier;
  private CoverageDetails proposedCoverageDetails;
  private PremiumDetails proposedPremiumDetails;
  private ClockProvider clockProvider;

  public PolicyModification(
      PolicyModificationId policyModificationId,
      DateTime expirationDate,
      PolicyModificationStatus status,
      Money premiumAdjustment,
      PolicyInformationModifier policyInformationModifier,
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails,
      ClockProvider clockProvider) {
    this.policyModificationId = policyModificationId;
    this.expirationDate = expirationDate;
    this.status = status;
    this.premiumAdjustment = premiumAdjustment;
    this.policyInformationModifier = policyInformationModifier;
    this.proposedCoverageDetails = proposedCoverageDetails;
    this.proposedPremiumDetails = proposedPremiumDetails;
    this.clockProvider = clockProvider;
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

  public PolicyView updatePolicyView(PolicyView currentPolicyView) {
    return new PolicyView(
        computeUpdatedCoveragePeriod(currentPolicyView),
        computeUpdatedPolicyInformation(currentPolicyView),
        proposedCoverageDetails,
        proposedPremiumDetails);
  }

  private Period computeUpdatedCoveragePeriod(PolicyView currentPolicyView) {
    Date updatedCoveragePeriodStartDate = Date.now(clockProvider.getClock());
    Date updatedCoveragePeriodEndDate = currentPolicyView.getCoveragePeriod().getEndDate();
    return new Period(updatedCoveragePeriodStartDate, updatedCoveragePeriodEndDate);
  }

  private PolicyInformation computeUpdatedPolicyInformation(PolicyView currentPolicyView) {
    PolicyInformation currentPolicyInformation = currentPolicyView.getPolicyInformation();
    return policyInformationModifier.modify(currentPolicyInformation);
  }

  public boolean isConfirmed() {
    return status.equals(CONFIRMED);
  }

  public boolean isPending() {
    return status.equals(PENDING);
  }

  public boolean isOutdated() {
    return DateTime.now(clockProvider.getClock()).isAfter(expirationDate);
  }

  public boolean isExpired() {
    return status.equals(EXPIRED);
  }

  public void expire() {
    status = EXPIRED;
  }

  public void confirm() {
    status = CONFIRMED;
  }
}
