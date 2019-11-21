package ca.ulaval.glo4003.insuring.domain.policy.aggregate;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.helper.policy.PolicyBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyHistoricBuilder;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.error.InactivePolicyError;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyHistoric;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyView;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyCoveragePeriodLengthProvider;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewal;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalsCoordinator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyRenewalsCoordinator;
import static ca.ulaval.glo4003.helper.policy.PolicyViewGenerator.createPolicyView;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createJavaTimePeriod;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.getClockProvider;
import static ca.ulaval.glo4003.insuring.domain.policy.PolicyStatus.*;
import static ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalStatus.PENDING;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubmitCoverageRenewalTest {
  private static final ClockProvider CLOCK_PROVIDER = getClockProvider();
  private static final CoverageDetails PROPOSED_COVERAGE_DETAILS = createCoverageDetails();
  private static final PremiumDetails PROPOSED_PREMIUM_DETAILS = createPremiumDetails();
  private static final java.time.Period POLICY_COVERAGE_PERIOD_LENGTH = createJavaTimePeriod();
  private static final PolicyView CURRENT_POLICY_VIEW = createPolicyView();
  private static final PolicyHistoric POLICY_HISTORIC =
      PolicyHistoricBuilder.aPolicyHistoric().withPolicyView(CURRENT_POLICY_VIEW).build();

  @Mock private PolicyCoveragePeriodLengthProvider policyCoveragePeriodLengthProvider;

  private Policy subject;
  private PolicyRenewalsCoordinator policyRenewalsCoordinator;

  @Before
  public void setUp() {
    when(policyCoveragePeriodLengthProvider.getPolicyCoveragePeriod())
        .thenReturn(POLICY_COVERAGE_PERIOD_LENGTH);
    policyRenewalsCoordinator = createPolicyRenewalsCoordinator();
    subject =
        PolicyBuilder.aPolicy()
            .withStatus(ACTIVE)
            .withPolicyHistoric(POLICY_HISTORIC)
            .withPolicyRenewalsCoordinator(policyRenewalsCoordinator)
            .withClockProvider(CLOCK_PROVIDER)
            .build();
  }

  @Test
  public void submittingCoverageRenewal_shouldSetPolicyInRenewingMode() {
    subject.submitCoverageRenewal(
        PROPOSED_COVERAGE_DETAILS, PROPOSED_PREMIUM_DETAILS, policyCoveragePeriodLengthProvider);

    assertEquals(RENEWING, subject.getStatus());
  }

  @Test
  public void submittingCoverageRenewal_shouldComputeRenewalCoveragePeriod() {
    PolicyRenewal policyRenewal =
        subject.submitCoverageRenewal(
            PROPOSED_COVERAGE_DETAILS,
            PROPOSED_PREMIUM_DETAILS,
            policyCoveragePeriodLengthProvider);

    Date currentCoveragePeriodEndDate = CURRENT_POLICY_VIEW.getCoveragePeriod().getEndDate();
    Date expectedRenewalCoveragePeriodStartDate =
        currentCoveragePeriodEndDate.plus(java.time.Period.ofDays(1));
    Date expectedRenewalCoveragePeriodEndDate =
        expectedRenewalCoveragePeriodStartDate.plus(POLICY_COVERAGE_PERIOD_LENGTH);
    Period expectedRenewalCoveragePeriod =
        new Period(expectedRenewalCoveragePeriodStartDate, expectedRenewalCoveragePeriodEndDate);
    assertEquals(expectedRenewalCoveragePeriod, policyRenewal.getCoveragePeriod());
  }

  @Test
  public void submittingCoverageRenewal_shouldCreatePendingPolicyRenewal() {
    PolicyRenewal policyRenewal =
        subject.submitCoverageRenewal(
            PROPOSED_COVERAGE_DETAILS,
            PROPOSED_PREMIUM_DETAILS,
            policyCoveragePeriodLengthProvider);

    assertEquals(PENDING, policyRenewal.getStatus());
  }

  @Test
  public void submittingCoverageRenewal_shouldRegisterRenewal() {
    PolicyRenewal policyRenewal =
        subject.submitCoverageRenewal(
            PROPOSED_COVERAGE_DETAILS,
            PROPOSED_PREMIUM_DETAILS,
            policyCoveragePeriodLengthProvider);

    assertEquals(
        policyRenewal, policyRenewalsCoordinator.getRenewal(policyRenewal.getPolicyRenewalId()));
  }

  @Test(expected = InactivePolicyError.class)
  public void submittingCoverageRenewal_withInactivePolicy_shouldThrow() {
    subject = PolicyBuilder.aPolicy().withStatus(INACTIVE).build();

    subject.submitCoverageRenewal(
        PROPOSED_COVERAGE_DETAILS, PROPOSED_PREMIUM_DETAILS, policyCoveragePeriodLengthProvider);
  }
}
