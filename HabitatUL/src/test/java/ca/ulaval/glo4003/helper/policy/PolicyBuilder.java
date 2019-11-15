package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyId;
import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyInformation;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createPeriod;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.getClockProvider;

public class PolicyBuilder {
  private static final PolicyId DEFAULT_POLICY_ID = createPolicyId();
  private static final String DEFAULT_QUOTE_KEY = Faker.instance().internet().uuid();
  private static final Period DEFAULT_COVERAGE_PERIOD = createPeriod();
  private static final PolicyInformation DEFAULT_POLICY_INFORMATION = createPolicyInformation();
  private static final CoverageDetails DEFAULT_COVERAGE_DETAILS = createCoverageDetails();
  private static final PremiumDetails DEFAULT_PREMIUM_DETAILS = createPremiumDetails();
  private static final ClockProvider DEFAULT_CLOCK_PROVIDER = getClockProvider();

  private PolicyId policyId = DEFAULT_POLICY_ID;
  private String quoteKey = DEFAULT_QUOTE_KEY;
  private Period coveragePeriod = DEFAULT_COVERAGE_PERIOD;
  private PolicyInformation policyInformation = DEFAULT_POLICY_INFORMATION;
  private CoverageDetails coverageDetails = DEFAULT_COVERAGE_DETAILS;
  private PremiumDetails premiumDetails = DEFAULT_PREMIUM_DETAILS;
  private ClockProvider clockProvider = DEFAULT_CLOCK_PROVIDER;

  private PolicyBuilder() {}

  public static PolicyBuilder aPolicy() {
    return new PolicyBuilder();
  }

  public PolicyBuilder withCoveragePeriod(Period coveragePeriod) {
    this.coveragePeriod = coveragePeriod;
    return this;
  }

  public PolicyBuilder withPolicyInformation(PolicyInformation policyInformation) {
    this.policyInformation = policyInformation;
    return this;
  }

  public PolicyBuilder withCoverageDetails(CoverageDetails coverageDetails) {
    this.coverageDetails = coverageDetails;
    return this;
  }

  public Policy build() {
    return new Policy(
        policyId,
        quoteKey,
        coveragePeriod,
        policyInformation,
        coverageDetails,
        premiumDetails,
        clockProvider);
  }
}
