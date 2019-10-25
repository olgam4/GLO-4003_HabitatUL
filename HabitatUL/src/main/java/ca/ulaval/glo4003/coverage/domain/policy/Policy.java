package ca.ulaval.glo4003.coverage.domain.policy;

import ca.ulaval.glo4003.coverage.domain.claim.Claim;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimId;
import ca.ulaval.glo4003.coverage.domain.claim.LossCategory;
import ca.ulaval.glo4003.coverage.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.coverage.domain.policy.error.NotDeclaredBicycleError;
import ca.ulaval.glo4003.mediator.AggregateRoot;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import java.util.ArrayList;
import java.util.List;

public class Policy extends AggregateRoot {
  private final List<ClaimId> claims = new ArrayList<>();
  private PolicyId policyId;
  private String quoteKey;
  private Period coveragePeriod;

  public Policy(PolicyId policyId, String quoteKey, Period coveragePeriod) {
    this.policyId = policyId;
    this.quoteKey = quoteKey;
    this.coveragePeriod = coveragePeriod;
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
    // TODO: register Event OpenedClaim
  }

  private void validateClaim(Claim claim) {
    checkIfLossDeclarationContainsNotDeclaredBicycle(claim.getLossDeclarations());
  }

  private void checkIfLossDeclarationContainsNotDeclaredBicycle(LossDeclarations lossDeclarations) {
    if (lossDeclarations.getLossDeclarations().containsKey(LossCategory.BICYCLE)) {
      throw new NotDeclaredBicycleError();
    }
  }
}
