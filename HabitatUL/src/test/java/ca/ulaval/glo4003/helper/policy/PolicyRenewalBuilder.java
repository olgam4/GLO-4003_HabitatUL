package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewal;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalStatus;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.policy.PolicyRenewalGenerator.createPolicyRenewalId;
import static ca.ulaval.glo4003.helper.policy.PolicyRenewalGenerator.createPolicyRenewalStatus;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createPeriod;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.getClockProvider;

public class PolicyRenewalBuilder {
  private final PolicyRenewalId DEFAULT_POLICY_RENEWAL_ID = createPolicyRenewalId();
  private final PolicyRenewalStatus DEFAULT_STATUS = createPolicyRenewalStatus();
  private final Period DEFAULT_COVERAGE_PERIOD = createPeriod();
  private final CoverageDetails DEFAULT_COVERAGE_DETAILS = createCoverageDetails();
  private final PremiumDetails DEFAULT_PREMIUM_DETAILS = createPremiumDetails();
  private final ClockProvider DEFAULT_CLOCK_PROVIDER = getClockProvider();

  private PolicyRenewalId policyRenewalId = DEFAULT_POLICY_RENEWAL_ID;
  private PolicyRenewalStatus status = DEFAULT_STATUS;
  private Period coveragePeriod = DEFAULT_COVERAGE_PERIOD;
  private CoverageDetails proposedCoverageDetails = DEFAULT_COVERAGE_DETAILS;
  private PremiumDetails proposedPremiumDetails = DEFAULT_PREMIUM_DETAILS;
  private ClockProvider clockProvider = DEFAULT_CLOCK_PROVIDER;

  private PolicyRenewalBuilder() {}

  public static PolicyRenewalBuilder aPolicyRenewal() {
    return new PolicyRenewalBuilder();
  }

  public PolicyRenewalBuilder withPolicyRenewalId(PolicyRenewalId policyRenewalId) {
    this.policyRenewalId = policyRenewalId;
    return this;
  }

  public PolicyRenewalBuilder withStatus(PolicyRenewalStatus status) {
    this.status = status;
    return this;
  }

  public PolicyRenewalBuilder withCoveragePeriod(Period coveragePeriod) {
    this.coveragePeriod = coveragePeriod;
    return this;
  }

  public PolicyRenewal build() {
    return new PolicyRenewal(
        policyRenewalId,
        status,
        coveragePeriod,
        proposedCoverageDetails,
        proposedPremiumDetails,
        clockProvider);
  }
}
