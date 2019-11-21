package ca.ulaval.glo4003.insuring.domain.policy.modification;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.insuring.domain.policy.error.ModificationAlreadyConfirmedError;
import ca.ulaval.glo4003.insuring.domain.policy.error.ModificationExpiredError;
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
  private PolicyModificationStatus status;
  private DateTime expirationDate;
  private Money proposedPremiumAdjustment;
  private PolicyInformationModifier policyInformationModifier;
  private CoverageDetails proposedCoverageDetails;
  private PremiumDetails proposedPremiumDetails;
  private ClockProvider clockProvider;

  public PolicyModification(
      PolicyModificationId policyModificationId,
      PolicyModificationStatus status,
      DateTime expirationDate,
      Money proposedPremiumAdjustment,
      PolicyInformationModifier policyInformationModifier,
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails,
      ClockProvider clockProvider) {
    this.policyModificationId = policyModificationId;
    this.status = status;
    this.expirationDate = expirationDate;
    this.proposedPremiumAdjustment = proposedPremiumAdjustment;
    this.policyInformationModifier = policyInformationModifier;
    this.proposedCoverageDetails = proposedCoverageDetails;
    this.proposedPremiumDetails = proposedPremiumDetails;
    this.clockProvider = clockProvider;
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

  public void updateStatus() {
    if (isOutdated()) expire();
  }

  private boolean isOutdated() {
    return DateTime.now(clockProvider.getClock()).isAfter(expirationDate);
  }

  public void confirm() {
    checkIfModificationIsExpired();
    checkIfModificationIsConfirmed();
    status = CONFIRMED;
  }

  private void checkIfModificationIsExpired() {
    if (status.equals(EXPIRED)) {
      throw new ModificationExpiredError(policyModificationId);
    }
  }

  private void checkIfModificationIsConfirmed() {
    if (status.equals(CONFIRMED)) {
      throw new ModificationAlreadyConfirmedError(policyModificationId);
    }
  }

  public void expire() {
    if (status.equals(PENDING)) status = EXPIRED;
  }
}
