package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyView;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.policy.PolicyInformationGenerator.createPolicyInformation;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createPeriod;

public class PolicyViewBuilder {
  private static final Period DEFAULT_COVERAGE_PERIOD = createPeriod();
  private static final PolicyInformation DEFAULT_POLICY_INFORMATION = createPolicyInformation();
  private static final CoverageDetails DEFAULT_COVERAGE_DETAILS = createCoverageDetails();
  private static final PremiumDetails DEFAULT_PREMIUM_DETAILS = createPremiumDetails();

  private Period coveragePeriod = DEFAULT_COVERAGE_PERIOD;
  private PolicyInformation policyInformation = DEFAULT_POLICY_INFORMATION;
  private CoverageDetails coverageDetails = DEFAULT_COVERAGE_DETAILS;
  private PremiumDetails premiumDetails = DEFAULT_PREMIUM_DETAILS;

  private PolicyViewBuilder() {}

  public static PolicyViewBuilder aPolicyView() {
    return new PolicyViewBuilder();
  }

  public PolicyViewBuilder withCoveragePeriod(Period coveragePeriod) {
    this.coveragePeriod = coveragePeriod;
    return this;
  }

  public PolicyViewBuilder withPolicyInformation(PolicyInformation policyInformation) {
    this.policyInformation = policyInformation;
    return this;
  }

  public PolicyViewBuilder withCoverageDetails(CoverageDetails coverageDetails) {
    this.coverageDetails = coverageDetails;
    return this;
  }

  public PolicyView build() {
    return new PolicyView(coveragePeriod, policyInformation, coverageDetails, premiumDetails);
  }
}
