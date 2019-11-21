package ca.ulaval.glo4003.insuring.domain.policy.aggregate;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.helper.policy.PolicyBuilder;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.error.InactivePolicyError;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyCoveragePeriodProvider;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.getClockProvider;
import static ca.ulaval.glo4003.insuring.domain.policy.PolicyStatus.*;
import static org.junit.Assert.assertEquals;

public class SubmitCoverageRenewalTest {
  private static final ClockProvider CLOCK_PROVIDER = getClockProvider();
  private static final CoverageDetails PROPOSED_COVERAGE_DETAILS = createCoverageDetails();
  private static final PremiumDetails PROPOSED_PREMIUM_DETAILS = createPremiumDetails();

  @Mock private PolicyCoveragePeriodProvider policyCoveragePeriodProvider;

  private Policy subject;

  @Before
  public void setUp() {
    subject = PolicyBuilder.aPolicy().withStatus(ACTIVE).withClockProvider(CLOCK_PROVIDER).build();
  }

  @Test
  public void submittingCoverageRenewal_shouldSetPolicyInRenewingMode() {
    subject.submitCoverageRenewal(
        PROPOSED_COVERAGE_DETAILS, PROPOSED_PREMIUM_DETAILS, policyCoveragePeriodProvider);

    assertEquals(RENEWING, subject.getStatus());
  }

  @Test(expected = InactivePolicyError.class)
  public void submittingCoverageRenewal_withInactivePolicy_shouldThrow() {
    subject = PolicyBuilder.aPolicy().withStatus(INACTIVE).build();

    subject.submitCoverageRenewal(
        PROPOSED_COVERAGE_DETAILS, PROPOSED_PREMIUM_DETAILS, policyCoveragePeriodProvider);
  }
}
