package ca.ulaval.glo4003.generator.policy;

import ca.ulaval.glo4003.coverage.application.policy.event.PolicyCreationRequestedEvent;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyId;
import ca.ulaval.glo4003.coverage.domain.policy.QuoteId;

public class PolicyGenerator {
  public static PolicyCreationRequestedEvent createQuotePurchasedDto() {
    return new PolicyCreationRequestedEvent(createQuoteId());
  }

  public static Policy createPolicy() {
    return new Policy(createPolicyId(), createQuoteId());
  }

  private static PolicyId createPolicyId() {
    return new PolicyId();
  }

  private static QuoteId createQuoteId() {
    return new QuoteId();
  }
}
