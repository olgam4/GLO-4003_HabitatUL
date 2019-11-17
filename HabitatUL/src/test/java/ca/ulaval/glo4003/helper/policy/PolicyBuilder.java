package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyHistoric;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationsCoordinator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyId;
import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyModificationsCoordinator;
import static ca.ulaval.glo4003.helper.policy.PolicyHistoricGenerator.createPolicyHistoric;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.getClockProvider;

public class PolicyBuilder {
  private static final PolicyId DEFAULT_POLICY_ID = createPolicyId();
  private static final String DEFAULT_QUOTE_KEY = Faker.instance().internet().uuid();
  private static final PolicyHistoric DEFAULT_POLICY_HISTORIC = createPolicyHistoric();
  private static final PolicyModificationsCoordinator DEFAULT_POLICY_MODIFICATIONS_COORDINATOR =
      createPolicyModificationsCoordinator();
  private static final ClockProvider DEFAULT_CLOCK_PROVIDER = getClockProvider();

  private PolicyId policyId = DEFAULT_POLICY_ID;
  private String quoteKey = DEFAULT_QUOTE_KEY;
  private PolicyHistoric policyHistoric = DEFAULT_POLICY_HISTORIC;
  private PolicyModificationsCoordinator policyModificationsCoordinator =
      DEFAULT_POLICY_MODIFICATIONS_COORDINATOR;
  private ClockProvider clockProvider = DEFAULT_CLOCK_PROVIDER;

  private PolicyBuilder() {}

  public static PolicyBuilder aPolicy() {
    return new PolicyBuilder();
  }

  public PolicyBuilder withId(PolicyId policyId) {
    this.policyId = policyId;
    return this;
  }

  public PolicyBuilder withPolicyHistoric(PolicyHistoric policyHistoric) {
    this.policyHistoric = policyHistoric;
    return this;
  }

  public PolicyBuilder withClockProvider(ClockProvider clockProvider) {
    this.clockProvider = clockProvider;
    return this;
  }

  public Policy build() {
    return new Policy(
        policyId, quoteKey, policyHistoric, policyModificationsCoordinator, clockProvider);
  }
}
