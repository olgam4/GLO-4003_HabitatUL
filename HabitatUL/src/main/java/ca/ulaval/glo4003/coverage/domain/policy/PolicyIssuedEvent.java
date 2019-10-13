package ca.ulaval.glo4003.coverage.domain.policy;

import ca.ulaval.glo4003.mediator.Event;

public class PolicyIssuedEvent extends Event {
  private PolicyId policyId;
  private String quoteKey;

  public PolicyIssuedEvent(PolicyId policyId, String quoteKey) {
    this.policyId = policyId;
    this.quoteKey = quoteKey;
  }

  public PolicyId getPolicyId() {
    return policyId;
  }

  public String getQuoteKey() {
    return quoteKey;
  }
}
