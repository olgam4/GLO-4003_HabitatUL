package ca.ulaval.glo4003.coverage.domain.policy;

import ca.ulaval.glo4003.mediator.AggregateRoot;
import ca.ulaval.glo4003.shared.domain.ClockProvider;

public class Policy extends AggregateRoot {
  private PolicyId policyId;
  private QuoteId quoteId;
  private ClockProvider clockProvider;

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
}
