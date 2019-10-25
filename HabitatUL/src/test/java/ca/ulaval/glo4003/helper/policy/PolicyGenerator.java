package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.coverage.application.policy.event.PolicyCreationRequestedEvent;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyId;
import ca.ulaval.glo4003.helper.TemporalGenerator;
import com.github.javafaker.Faker;

public class PolicyGenerator {
  public static PolicyCreationRequestedEvent createPolicyCreationRequestedEvent() {
    return new PolicyCreationRequestedEvent(
        Faker.instance().internet().uuid(),
        TemporalGenerator.createPeriod(),
        TemporalGenerator.createDate());
  }

  public static Policy createPolicy() {
    return new Policy(
        createPolicyId(), Faker.instance().internet().uuid(), TemporalGenerator.createPeriod());
  }

  public static PolicyId createPolicyId() {
    return new PolicyId();
  }
}
