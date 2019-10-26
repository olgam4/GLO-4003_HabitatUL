package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyId;
import ca.ulaval.glo4003.helper.TemporalGenerator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyId;

public class PolicyBuilder {
  private static final PolicyId DEFAULT_POLICY_ID = createPolicyId();
  private static final String DEFAULT_QUOTE_KEY = Faker.instance().internet().uuid();
  private static final Period DEFAULT_COVERAGE_PERIOD = TemporalGenerator.createPeriod();
  private static final ClockProvider DEFAULT_CLOCK_PROVIDER = TemporalGenerator.getClockProvider();

  private PolicyId policyId = DEFAULT_POLICY_ID;
  private String quoteKey = DEFAULT_QUOTE_KEY;
  private Period coveragePeriod = DEFAULT_COVERAGE_PERIOD;
  private ClockProvider clockProvider = DEFAULT_CLOCK_PROVIDER;

  private PolicyBuilder() {}

  public static PolicyBuilder aPolicy() {
    return new PolicyBuilder();
  }

  public PolicyBuilder withCoveragePeriod(Period coveragePeriod) {
    this.coveragePeriod = coveragePeriod;
    return this;
  }

  public Policy build() {
    return new Policy(policyId, quoteKey, coveragePeriod, clockProvider);
  }
}
