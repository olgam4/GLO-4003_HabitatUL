package ca.ulaval.glo4003.coverage.domain.policy;

import ca.ulaval.glo4003.coverage.domain.claim.*;
import ca.ulaval.glo4003.coverage.domain.policy.exception.NotDeclaredBicycleError;
import ca.ulaval.glo4003.mediator.AggregateRoot;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;

import java.util.ArrayList;
import java.util.List;

public class Policy extends AggregateRoot {
  private PolicyId policyId;
  private QuoteId quoteId;
  private ClockProvider clockProvider;
  private List<ClaimId> claims = new ArrayList<>();

  public Policy(PolicyId policyId, QuoteId quoteId, ClockProvider clockProvider) {
    this.policyId = policyId;
    this.quoteId = quoteId;
    this.clockProvider = clockProvider;
  }

  public PolicyId getPolicyId() {
    return policyId;
  }

  public QuoteId getQuoteId() {
    return quoteId;
  }

  public void issue() {
    registerEvent(new PolicyIssuedEvent(policyId, quoteId, clockProvider));
  }

  public Claim openClaim(
      SinisterType sinisterType, LossDeclarations lossDeclarations, ClaimFactory claimFactory) {
    checkIfLossDeclarationContainsNotDeclaredBicycle(lossDeclarations);
    Claim claim = claimFactory.createClaim(sinisterType, lossDeclarations);
    claims.add(claim.getClaimId());
    return claim;
  }

  private void checkIfLossDeclarationContainsNotDeclaredBicycle(LossDeclarations lossDeclarations) {
    if (lossDeclarations.getLossDeclarations().containsKey(LossCategory.BICYCLE)) {
      throw new NotDeclaredBicycleError();
    }
  }
}
