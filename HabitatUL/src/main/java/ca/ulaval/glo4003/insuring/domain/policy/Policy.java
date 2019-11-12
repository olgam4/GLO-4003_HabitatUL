package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.LossCategory;
import ca.ulaval.glo4003.insuring.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.insuring.domain.policy.error.ClaimOutsideCoveragePeriodError;
import ca.ulaval.glo4003.insuring.domain.policy.error.LossDeclarationsExceedCoverageAmountError;
import ca.ulaval.glo4003.insuring.domain.policy.error.NotDeclaredBicycleError;
import ca.ulaval.glo4003.mediator.AggregateRoot;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import java.util.ArrayList;
import java.util.List;

public class Policy extends AggregateRoot {
  private final List<ClaimId> claims = new ArrayList<>();
  private PolicyId policyId;
  private String quoteKey;
  private Period coveragePeriod;
  private Amount coverageAmount;
  private ClockProvider clockProvider;

  public Policy(
      PolicyId policyId,
      String quoteKey,
      Period coveragePeriod,
      Amount coverageAmount,
      ClockProvider clockProvider) {
    this.policyId = policyId;
    this.quoteKey = quoteKey;
    this.coveragePeriod = coveragePeriod;
    this.coverageAmount = coverageAmount;
    this.clockProvider = clockProvider;
  }

  public PolicyId getPolicyId() {
    return policyId;
  }

  public String getQuoteKey() {
    return quoteKey;
  }

  public Period getCoveragePeriod() {
    return coveragePeriod;
  }

  public List<ClaimId> getClaims() {
    return claims;
  }

  public void issue() {
    registerEvent(new PolicyIssuedEvent(policyId, quoteKey));
  }

  public void openClaim(Claim claim) {
    validateClaim(claim);
    claims.add(claim.getClaimId());
    registerEvent(
        new ClaimOpenedEvent(policyId, claim.getClaimId(), Date.now(clockProvider.getClock())));
  }

  private void validateClaim(Claim claim) {
    checkIfClaimOutsideCoveragePeriod();
    checkIfLossDeclarationsContainsNotDeclaredBicycle(claim.getLossDeclarations());
    checkIfLossDeclarationsExceedCoverageAmount(claim.getLossDeclarations());
  }

  private void checkIfClaimOutsideCoveragePeriod() {
    Date now = Date.now(clockProvider.getClock());
    if (!coveragePeriod.isWithin(now)) {
      throw new ClaimOutsideCoveragePeriodError();
    }
  }

  private void checkIfLossDeclarationsExceedCoverageAmount(LossDeclarations lossDeclarations) {
    Amount totalLosses = lossDeclarations.computeTotalLosses();
    if (totalLosses.isGreaterThan(coverageAmount)) {
      throw new LossDeclarationsExceedCoverageAmountError();
    }
  }

  private void checkIfLossDeclarationsContainsNotDeclaredBicycle(
      LossDeclarations lossDeclarations) {
    if (lossDeclarations.getCollection().containsKey(LossCategory.BICYCLE)) {
      throw new NotDeclaredBicycleError();
    }
  }
}
