package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyView;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyId;
import static ca.ulaval.glo4003.helper.policy.PolicyViewGenerator.createPolicyView;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.getClockProvider;

public class PolicyBuilder {
  private static final PolicyId DEFAULT_POLICY_ID = createPolicyId();
  private static final String DEFAULT_QUOTE_KEY = Faker.instance().internet().uuid();
  private static final PolicyView DEFAULT_POLICY_VIEW = createPolicyView();
  private static final ClockProvider DEFAULT_CLOCK_PROVIDER = getClockProvider();

  private PolicyId policyId = DEFAULT_POLICY_ID;
  private String quoteKey = DEFAULT_QUOTE_KEY;
  private PolicyView policyView = DEFAULT_POLICY_VIEW;
  private ClockProvider clockProvider = DEFAULT_CLOCK_PROVIDER;

  private PolicyBuilder() {}

  public static PolicyBuilder aPolicy() {
    return new PolicyBuilder();
  }

  public PolicyBuilder withId(PolicyId policyId) {
    this.policyId = policyId;
    return this;
  }

  public PolicyBuilder withPolicyView(PolicyView policyView) {
    this.policyView = policyView;
    return this;
  }

  public Policy build() {
    return new Policy(policyId, quoteKey, policyView, clockProvider);
  }
}
