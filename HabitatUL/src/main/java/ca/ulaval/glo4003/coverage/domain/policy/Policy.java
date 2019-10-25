package ca.ulaval.glo4003.coverage.domain.policy;

import ca.ulaval.glo4003.coverage.domain.claim.*;
import ca.ulaval.glo4003.coverage.domain.policy.exception.NotDeclaredBicycleError;
import ca.ulaval.glo4003.mediator.AggregateRoot;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import java.util.ArrayList;
import java.util.List;

public class Policy extends AggregateRoot {
  private PolicyId policyId;
  private String quoteKey;
  private Period coveragePeriod;
  private List<ClaimId> claims = new ArrayList<>();

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

  public void issue() {
    registerEvent(new PolicyIssuedEvent(policyId, quoteKey));
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
