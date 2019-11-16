package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.policy.error.ClaimOutsideCoveragePeriodError;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import ca.ulaval.glo4003.mediator.AggregateRoot;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import java.util.ArrayList;
import java.util.List;

public class Policy extends AggregateRoot {
  private final List<PolicyModification> modifications = new ArrayList<>();
  private final List<ClaimId> claims = new ArrayList<>();
  private PolicyId policyId;
  private String quoteKey;
  private PolicyView currentPolicyView;
  private ClockProvider clockProvider;

  public Policy(
      PolicyId policyId,
      String quoteKey,
      PolicyView currentPolicyView,
      ClockProvider clockProvider) {
    this.policyId = policyId;
    this.quoteKey = quoteKey;
    this.currentPolicyView = currentPolicyView;
    this.clockProvider = clockProvider;
  }

  public PolicyId getPolicyId() {
    return policyId;
  }

  public String getQuoteKey() {
    return quoteKey;
  }

  public List<ClaimId> getClaims() {
    return claims;
  }

  public Period getCoveragePeriod() {
    return currentPolicyView.getCoveragePeriod();
  }

  public PolicyInformation getPolicyInformation() {
    return currentPolicyView.getPolicyInformation();
  }

  public CoverageDetails getCurrentCoverageDetails() {
    return currentPolicyView.getCoverageDetails();
  }

  public PremiumDetails getCurrentPremiumDetails() {
    return currentPolicyView.getPremiumDetails();
  }

  public void issue() {
    registerEvent(new PolicyIssuedEvent(policyId, quoteKey));
  }

  public void openClaim(Claim claim) {
    checkIfClaimOutsideCoveragePeriod();
    claim.validate(getPolicyInformation(), getCurrentCoverageDetails());
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
