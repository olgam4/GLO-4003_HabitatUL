package ca.ulaval.glo4003.generator.policy;

import ca.ulaval.glo4003.coverage.application.policy.event.PolicyCreationRequestedEvent;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyId;
import com.github.javafaker.Faker;

public class PolicyGenerator {
  public static PolicyCreationRequestedEvent createQuotePurchasedDto() {
    return new PolicyCreationRequestedEvent(createQuoteKey());
  }

  public static Policy createPolicy() {
    return new Policy(createPolicyId(), createQuoteKey());
  }

  private static PolicyId createPolicyId() {
    return new PolicyId();
  }

  private static String createQuoteKey() {
    return Faker.instance().internet().uuid();
  }
}
