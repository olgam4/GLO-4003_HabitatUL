package ca.ulaval.glo4003.coverage.domain.policy;

import ca.ulaval.glo4003.mediator.Event;

public class PolicyIssuedEvent extends Event {
  private PolicyId policyId;
  private QuoteId quoteId;

  public PolicyIssuedEvent(PolicyId policyId, QuoteId quoteId) {
    this.policyId = policyId;
    this.quoteId = quoteId;
  }

  public PolicyId getPolicyId() {
    return policyId;
  }

  public QuoteId getQuoteId() {
    return quoteId;
  }
}
