package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.policy.error.ClaimOutsideCoveragePeriodError;
import ca.ulaval.glo4003.insuring.domain.policy.error.InactivePolicyError;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyHistoric;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationValidityPeriodProvider;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationsCoordinator;
import ca.ulaval.glo4003.insuring.domain.policy.modification.modifier.InsureBicyclePolicyInformationModifier;
import ca.ulaval.glo4003.insuring.domain.policy.modification.modifier.NoImpactPolicyInformationModifier;
import ca.ulaval.glo4003.insuring.domain.policy.modification.modifier.PolicyInformationModifier;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyCoveragePeriodLengthProvider;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewal;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalsCoordinator;
import ca.ulaval.glo4003.mediator.AggregateRoot;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import org.glassfish.jersey.internal.util.Producer;

import java.util.ArrayList;
import java.util.List;

import static ca.ulaval.glo4003.insuring.domain.policy.PolicyStatus.*;

public class Policy extends AggregateRoot {
  private final List<ClaimId> claims = new ArrayList<>();
  private PolicyId policyId;
  private String quoteKey;
  private PolicyStatus status;
  private PolicyHistoric policyHistoric;
  private PolicyModificationsCoordinator policyModificationsCoordinator;
  private PolicyRenewalsCoordinator policyRenewalsCoordinator;
  private ClockProvider clockProvider;

  public Policy(
      PolicyId policyId,
      String quoteKey,
      PolicyStatus status,
      PolicyHistoric policyHistoric,
      PolicyModificationsCoordinator policyModificationsCoordinator,
      PolicyRenewalsCoordinator policyRenewalsCoordinator,
      ClockProvider clockProvider) {
    this.policyId = policyId;
    this.quoteKey = quoteKey;
    this.status = status;
    this.policyHistoric = policyHistoric;
    this.policyModificationsCoordinator = policyModificationsCoordinator;
    this.policyRenewalsCoordinator = policyRenewalsCoordinator;
    this.clockProvider = clockProvider;
  }

  public PolicyId getPolicyId() {
    return policyId;
  }

  public String getQuoteKey() {
    return quoteKey;
  }

  public PolicyStatus getStatus() {
    return status;
  }

  public List<ClaimId> getClaims() {
    return claims;
  }

  public Period getCoveragePeriod() {
    return policyHistoric.getCurrentCoveragePeriod();
  }

  public PolicyInformation getPolicyInformation() {
    return policyHistoric.getCurrentPolicyInformation();
  }

  public CoverageDetails getCoverageDetails() {
    return policyHistoric.getCurrentCoverageDetails();
  }

  public PremiumDetails getPremiumDetails() {
    return policyHistoric.getCurrentPremiumDetails();
  }

  public void issue() {
    registerEvent(new PolicyIssuedEvent(policyId, quoteKey));
  }

  public PolicyModification submitInsureBicycleModification(
      Bicycle bicycle,
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails,
      PolicyModificationValidityPeriodProvider policyModificationValidityPeriodProvider) {
    return updateStatus(
        () ->
            submitInsureBicycleModificationWithoutUpdate(
                bicycle,
                proposedCoverageDetails,
                proposedPremiumDetails,
                policyModificationValidityPeriodProvider));
  }

  private PolicyModification submitInsureBicycleModificationWithoutUpdate(
      Bicycle bicycle,
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails,
      PolicyModificationValidityPeriodProvider policyModificationValidityPeriodProvider) {
    checkIfInactivePolicy();
    PolicyInformationModifier policyInformationModifier =
        new InsureBicyclePolicyInformationModifier(bicycle);
    return policyModificationsCoordinator.registerPolicyModification(
        policyInformationModifier,
        proposedCoverageDetails,
        policyHistoric.getCurrentPremiumDetails(),
        proposedPremiumDetails,
        policyModificationValidityPeriodProvider,
        clockProvider);
  }

  public PolicyModification submitCoverageModification(
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails,
      PolicyModificationValidityPeriodProvider policyModificationValidityPeriodProvider) {
    return updateStatus(
        () ->
            submitCoverageModificationWithoutUpdate(
                proposedCoverageDetails,
                proposedPremiumDetails,
                policyModificationValidityPeriodProvider));
  }

  private PolicyModification submitCoverageModificationWithoutUpdate(
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails,
      PolicyModificationValidityPeriodProvider policyModificationValidityPeriodProvider) {
    checkIfInactivePolicy();
    PolicyInformationModifier policyInformationModifier = new NoImpactPolicyInformationModifier();
    return policyModificationsCoordinator.registerPolicyModification(
        policyInformationModifier,
        proposedCoverageDetails,
        policyHistoric.getCurrentPremiumDetails(),
        proposedPremiumDetails,
        policyModificationValidityPeriodProvider,
        clockProvider);
  }

  public void confirmModification(PolicyModificationId policyModificationId) {
    updateStatus(() -> confirmModificationWithoutUpdate(policyModificationId));
  }

  private PolicyModification confirmModificationWithoutUpdate(
      PolicyModificationId policyModificationId) {
    checkIfInactivePolicy();
    PolicyModification policyModification =
        policyModificationsCoordinator.retrieveConfirmedModification(policyModificationId);
    policyHistoric.updatePolicyHistory(policyModification);
    return policyModification;
  }

  public PolicyRenewal submitCoverageRenewal(
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails,
      PolicyCoveragePeriodLengthProvider policyCoveragePeriodLengthProvider) {
    return updateStatus(
        () ->
            submitCoverageRenewalWithoutUpdate(
                proposedCoverageDetails,
                proposedPremiumDetails,
                policyCoveragePeriodLengthProvider));
  }

  private PolicyRenewal submitCoverageRenewalWithoutUpdate(
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails,
      PolicyCoveragePeriodLengthProvider policyCoveragePeriodLengthProvider) {
    checkIfInactivePolicy();
    status = RENEWING;
    Date renewalEffectiveDate = policyHistoric.getCurrentCoveragePeriod().getEndDate();
    return policyRenewalsCoordinator.registerPolicyRenewal(
        renewalEffectiveDate,
        proposedCoverageDetails,
        proposedPremiumDetails,
        policyCoveragePeriodLengthProvider,
        clockProvider);
  }

  public void acceptRenewal(PolicyRenewalId policyRenewalId) {
    updateStatus(() -> acceptRenewalWithoutUpdate(policyRenewalId));
  }

  private PolicyRenewal acceptRenewalWithoutUpdate(PolicyRenewalId policyRenewalId) {
    checkIfInactivePolicy();
    return policyRenewalsCoordinator.retrieveAcceptedRenewal(policyRenewalId);
  }

  public void cancelRenewal(PolicyRenewalId policyRenewalId) {
    updateStatus(() -> cancelRenewalWithoutUpdate(policyRenewalId));
  }

  private PolicyRenewal cancelRenewalWithoutUpdate(PolicyRenewalId policyRenewalId) {
    checkIfInactivePolicy();
    PolicyRenewal policyRenewal = policyRenewalsCoordinator.cancelRenewal(policyRenewalId);
    status = ACTIVE;
    return policyRenewal;
  }

  public void confirmRenewal(PolicyRenewalId policyRenewalId) {
    updateStatus(() -> confirmRenewalWithoutUpdate(policyRenewalId));
  }

  private PolicyRenewal confirmRenewalWithoutUpdate(PolicyRenewalId policyRenewalId) {
    checkIfInactivePolicy();
    PolicyRenewal policyRenewal = policyRenewalsCoordinator.confirmRenewal(policyRenewalId);
    policyHistoric.updatePolicyHistory(policyRenewal);
    status = ACTIVE;
    return policyRenewal;
  }

  private void checkIfInactivePolicy() {
    if (status.equals(INACTIVE)) {
      throw new InactivePolicyError(policyId);
    }
  }

  public void openClaim(Claim claim) {
    checkIfClaimOutsideCoveragePeriod();
    claim.validate(getPolicyInformation(), getCoverageDetails());
    claims.add(claim.getClaimId());
    registerEvent(
        new ClaimOpenedEvent(policyId, claim.getClaimId(), Date.now(clockProvider.getClock())));
  }

  private void checkIfClaimOutsideCoveragePeriod() {
    // TODO: should ask for date of occurrence
    Date now = Date.now(clockProvider.getClock());
    if (!getCoveragePeriod().isWithin(now)) {
      throw new ClaimOutsideCoveragePeriodError();
    }
  }

  private <T> T updateStatus(Producer<T> call) {
    updateStatus();
    return call.call();
  }

  public void updateStatus() {
    if (isOutdated()) expire();
  }

  private boolean isOutdated() {
    return DateTime.now(clockProvider.getClock())
        .isAfter(policyHistoric.getCurrentCoveragePeriod().getEndDate().atStartOfDay());
  }

  private void expire() {
    if (status.equals(ACTIVE)) status = INACTIVE;
  }
}
