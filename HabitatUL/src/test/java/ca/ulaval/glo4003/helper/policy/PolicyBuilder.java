package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyStatus;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyHistoric;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationsCoordinator;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalsCoordinator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.*;
import static ca.ulaval.glo4003.helper.policy.PolicyHistoricGenerator.createPolicyHistoric;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.getClockProvider;

public class PolicyBuilder {
  private final PolicyId DEFAULT_POLICY_ID = createPolicyId();
  private final String DEFAULT_QUOTE_KEY = Faker.instance().internet().uuid();
  private final PolicyStatus DEFAULT_STATUS = createPolicyStatus();
  private final PolicyHistoric DEFAULT_POLICY_HISTORIC = createPolicyHistoric();
  private final PolicyModificationsCoordinator DEFAULT_POLICY_MODIFICATIONS_COORDINATOR =
      createPolicyModificationsCoordinator();
  private final PolicyRenewalsCoordinator DEFAULT_POLICY_RENEWALS_COORDINATOR =
      createPolicyRenewalsCoordinator();
  private final ClockProvider DEFAULT_CLOCK_PROVIDER = getClockProvider();

  private PolicyId policyId = DEFAULT_POLICY_ID;
  private String quoteKey = DEFAULT_QUOTE_KEY;
  private PolicyStatus status = DEFAULT_STATUS;
  private PolicyHistoric policyHistoric = DEFAULT_POLICY_HISTORIC;
  private PolicyModificationsCoordinator policyModificationsCoordinator =
      DEFAULT_POLICY_MODIFICATIONS_COORDINATOR;
  private PolicyRenewalsCoordinator policyRenewalsCoordinator = DEFAULT_POLICY_RENEWALS_COORDINATOR;
  private ClockProvider clockProvider = DEFAULT_CLOCK_PROVIDER;

  private PolicyBuilder() {}

  public static PolicyBuilder aPolicy() {
    return new PolicyBuilder();
  }

  public PolicyBuilder withId(PolicyId policyId) {
    this.policyId = policyId;
    return this;
  }

  public PolicyBuilder withStatus(PolicyStatus status) {
    this.status = status;
    return this;
  }

  public PolicyBuilder withPolicyHistoric(PolicyHistoric policyHistoric) {
    this.policyHistoric = policyHistoric;
    return this;
  }

  public PolicyBuilder withPolicyModificationsCoordinator(
      PolicyModificationsCoordinator policyModificationCoordinator) {
    this.policyModificationsCoordinator = policyModificationCoordinator;
    return this;
  }

  public PolicyBuilder withPolicyRenewalsCoordinator(
      PolicyRenewalsCoordinator policyRenewalsCoordinator) {
    this.policyRenewalsCoordinator = policyRenewalsCoordinator;
    return this;
  }

  public PolicyBuilder withClockProvider(ClockProvider clockProvider) {
    this.clockProvider = clockProvider;
    return this;
  }

  public Policy build() {
    return new Policy(
        policyId,
        quoteKey,
        status,
        policyHistoric,
        policyModificationsCoordinator,
        policyRenewalsCoordinator,
        clockProvider);
  }
}
