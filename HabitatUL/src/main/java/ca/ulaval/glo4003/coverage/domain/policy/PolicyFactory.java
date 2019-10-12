package ca.ulaval.glo4003.coverage.domain.policy;

public class PolicyFactory {
  public Policy create(QuoteId quoteId) {
    PolicyId policyId = new PolicyId();
    return new Policy(policyId, quoteId);
  }
}
