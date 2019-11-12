package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.shared.domain.temporal.Date;

public class ClaimOpenedEvent extends Event {
  private final PolicyId policyId;
  private final ClaimId claimId;
  private final Date declarationDate;

  public ClaimOpenedEvent(PolicyId policyId, ClaimId claimId, Date declarationDate) {
    this.policyId = policyId;
    this.claimId = claimId;
    this.declarationDate = declarationDate;
  }

  public PolicyId getPolicyId() {
    return policyId;
  }

  public ClaimId getClaimId() {
    return claimId;
  }

  public Date getDeclarationDate() {
    return declarationDate;
  }
}
