package ca.ulaval.glo4003.coverage.domain.policy;

import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;

public class PolicyFactory {
  private ClockProvider clockProvider;

  public PolicyFactory(ClockProvider clockProvider) {
    this.clockProvider = clockProvider;
  }

  public Policy create(QuoteId quoteId) {
    PolicyId policyId = new PolicyId();
    return new Policy(policyId, quoteId, clockProvider);
  }
}
