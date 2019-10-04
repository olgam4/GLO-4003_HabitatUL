package ca.ulaval.glo4003.generator.policy;

import ca.ulaval.glo4003.coverage.application.policy.dto.QuotePurchasedDto;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyId;
import ca.ulaval.glo4003.coverage.domain.policy.QuoteId;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;

public class PolicyGenerator {
  private static final FixedClockProvider FIXED_CLOCK_PROVIDER = new FixedClockProvider();

  public static QuotePurchasedDto createQuotePurchasedDto() {
    return new QuotePurchasedDto(createQuoteId());
  }

  public static Policy createPolicy() {
    return new Policy(createPolicyId(), createQuoteId(), FIXED_CLOCK_PROVIDER);
  }

  private static PolicyId createPolicyId() {
    return new PolicyId();
  }

  private static QuoteId createQuoteId() {
    return new QuoteId();
  }
}
