package ca.ulaval.glo4003.insuring.helper.policy;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyView;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import static ca.ulaval.glo4003.coverage.helper.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.coverage.helper.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.insuring.helper.policy.PolicyInformationGenerator.createPolicyInformation;
import static ca.ulaval.glo4003.insuring.helper.policy.PolicyViewGenerator.createCoveragePeriod;

public class PolicyViewBuilder {
  private final Period DEFAULT_COVERAGE_PERIOD = createCoveragePeriod();
  private final PolicyInformation DEFAULT_POLICY_INFORMATION = createPolicyInformation();
  private final CoverageDetails DEFAULT_COVERAGE_DETAILS = createCoverageDetails();
  private final PremiumDetails DEFAULT_PREMIUM_DETAILS = createPremiumDetails();

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

  public PolicyViewBuilder withPremiumDetails(PremiumDetails premiumDetails) {
    this.premiumDetails = premiumDetails;
    return this;
  }

  public PolicyView build() {
    return new PolicyView(coveragePeriod, policyInformation, coverageDetails, premiumDetails);
  }
}
