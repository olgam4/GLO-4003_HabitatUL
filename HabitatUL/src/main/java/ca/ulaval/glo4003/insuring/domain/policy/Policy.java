package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.policy.error.ClaimOutsideCoveragePeriodError;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyHistoric;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationValidityPeriodProvider;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationsCoordinator;
import ca.ulaval.glo4003.insuring.domain.policy.modification.modifier.InsureBicyclePolicyInformationModifier;
import ca.ulaval.glo4003.mediator.AggregateRoot;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import java.util.ArrayList;
import java.util.List;

public class Policy extends AggregateRoot {
  private final List<ClaimId> claims = new ArrayList<>();
  private PolicyId policyId;
  private String quoteKey;
  private PolicyStatus status;
  private PolicyHistoric policyHistoric;
  private PolicyModificationsCoordinator policyModificationsCoordinator;
  private ClockProvider clockProvider;

  public Policy(
      PolicyId policyId,
      String quoteKey,
      PolicyStatus status,
      PolicyHistoric policyHistoric,
      PolicyModificationsCoordinator policyModificationsCoordinator,
      ClockProvider clockProvider) {
    this.policyId = policyId;
    this.quoteKey = quoteKey;
    this.status = status;
    this.policyHistoric = policyHistoric;
    this.policyModificationsCoordinator = policyModificationsCoordinator;
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

  // TODO: remove
  public PolicyModification getModification(PolicyModificationId policyModificationId) {
    return policyModificationsCoordinator.getModification(policyModificationId);
  }

  public PolicyModification submitInsureBicycleModification(
      Bicycle bicycle,
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails,
      PolicyModificationValidityPeriodProvider policyModificationValidityPeriodProvider) {
    InsureBicyclePolicyInformationModifier insureBicyclePolicyInformationModifier =
        new InsureBicyclePolicyInformationModifier(bicycle);
    // TODO: check if policy is active
    // TODO: create policy status
    return policyModificationsCoordinator.registerPolicyModification(
        insureBicyclePolicyInformationModifier,
        proposedCoverageDetails,
        policyHistoric.getCurrentPremiumDetails(),
        proposedPremiumDetails,
        policyModificationValidityPeriodProvider,
        clockProvider);
  }

  public void confirmModification(PolicyModificationId policyModificationId) {
    // TODO: check if policy is active
    // TODO: create policy status
    PolicyModification policyModification =
        policyModificationsCoordinator.retrieveConfirmedModification(policyModificationId);
    policyHistoric.updatePolicyHistory(policyModification);
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
}
