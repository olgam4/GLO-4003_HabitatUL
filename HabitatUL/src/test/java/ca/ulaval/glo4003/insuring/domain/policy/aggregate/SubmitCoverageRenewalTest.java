package ca.ulaval.glo4003.insuring.domain.policy.aggregate;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.helper.policy.PolicyBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyHistoricBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyRenewalBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyViewBuilder;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.error.AnotherRenewalAlreadyAcceptedError;
import ca.ulaval.glo4003.insuring.domain.policy.error.CannotTriggerRenewalBeforeRenewalPeriodError;
import ca.ulaval.glo4003.insuring.domain.policy.error.InactivePolicyError;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyHistoric;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyView;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyCoveragePeriodProvider;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewal;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalPeriodProvider;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalsCoordinator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyRenewalsCoordinator;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.*;
import static ca.ulaval.glo4003.insuring.domain.policy.PolicyStatus.*;
import static ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalStatus.ACCEPTED;
import static ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalStatus.PENDING;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubmitCoverageRenewalTest {
  private static final ClockProvider CLOCK_PROVIDER = getClockProvider();
  private static final CoverageDetails PROPOSED_COVERAGE_DETAILS = createCoverageDetails();
  private static final PremiumDetails PROPOSED_PREMIUM_DETAILS = createPremiumDetails();
  private static final Period CURRENT_COVERAGE_PERIOD = createCurrentPeriod();
  private static final java.time.Period POLICY_RENEWAL_PERIOD =
      java.time.Period.ofDays(
          (int) (numberDaysBetween(getNowDate(), CURRENT_COVERAGE_PERIOD.getEndDate()) + 1));
  private static final java.time.Period POLICY_COVERAGE_PERIOD = createJavaTimePeriod();
  private static final PolicyView CURRENT_POLICY_VIEW =
      PolicyViewBuilder.aPolicyView().withCoveragePeriod(CURRENT_COVERAGE_PERIOD).build();
  private static final PolicyHistoric POLICY_HISTORIC =
      PolicyHistoricBuilder.aPolicyHistoric().withPolicyView(CURRENT_POLICY_VIEW).build();

  @Mock private PolicyRenewalPeriodProvider policyRenewalPeriodProvider;
  @Mock private PolicyCoveragePeriodProvider policyCoveragePeriodProvider;

  private Policy subject;
  private PolicyRenewalsCoordinator policyRenewalsCoordinator;

  @Before
  public void setUp() {
    when(policyRenewalPeriodProvider.getPolicyRenewalPeriod()).thenReturn(POLICY_RENEWAL_PERIOD);
    when(policyCoveragePeriodProvider.getPolicyCoveragePeriod()).thenReturn(POLICY_COVERAGE_PERIOD);
    policyRenewalsCoordinator = createPolicyRenewalsCoordinator(new ArrayList<>());
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
        PROPOSED_COVERAGE_DETAILS,
        PROPOSED_PREMIUM_DETAILS,
        policyRenewalPeriodProvider,
        policyCoveragePeriodProvider);

    assertEquals(RENEWING, subject.getStatus());
  }

  @Test
  public void submittingCoverageRenewal_shouldComputeRenewalCoveragePeriod() {
    PolicyRenewal policyRenewal =
        subject.submitCoverageRenewal(
            PROPOSED_COVERAGE_DETAILS,
            PROPOSED_PREMIUM_DETAILS,
            policyRenewalPeriodProvider,
            policyCoveragePeriodProvider);

    Date currentCoveragePeriodEndDate = CURRENT_POLICY_VIEW.getCoveragePeriod().getEndDate();
    Date expectedRenewalCoveragePeriodStartDate =
        currentCoveragePeriodEndDate.plus(java.time.Period.ofDays(1));
    Date expectedRenewalCoveragePeriodEndDate =
        currentCoveragePeriodEndDate.plus(POLICY_COVERAGE_PERIOD);
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
            policyRenewalPeriodProvider,
            policyCoveragePeriodProvider);

    assertEquals(PENDING, policyRenewal.getStatus());
  }

  @Test
  public void submittingCoverageRenewal_shouldRegisterRenewal() {
    PolicyRenewal policyRenewal =
        subject.submitCoverageRenewal(
            PROPOSED_COVERAGE_DETAILS,
            PROPOSED_PREMIUM_DETAILS,
            policyRenewalPeriodProvider,
            policyCoveragePeriodProvider);

    assertEquals(
        policyRenewal, policyRenewalsCoordinator.getRenewal(policyRenewal.getPolicyRenewalId()));
  }

  @Test(expected = InactivePolicyError.class)
  public void submittingCoverageRenewal_withInactivePolicy_shouldThrow() {
    subject = PolicyBuilder.aPolicy().withStatus(INACTIVE).build();

    subject.submitCoverageRenewal(
        PROPOSED_COVERAGE_DETAILS,
        PROPOSED_PREMIUM_DETAILS,
        policyRenewalPeriodProvider,
        policyCoveragePeriodProvider);
  }

  @Test(expected = CannotTriggerRenewalBeforeRenewalPeriodError.class)
  public void submittingCoverageRenewal_withTriggerDateBeforeRenewalPeriod_shouldThrow() {
    java.time.Period policyRenewalPeriod =
        java.time.Period.ofDays(
            (int) (numberDaysBetween(getNowDate(), CURRENT_COVERAGE_PERIOD.getEndDate()) - 1));
    when(policyRenewalPeriodProvider.getPolicyRenewalPeriod()).thenReturn(policyRenewalPeriod);

    subject.submitCoverageRenewal(
        PROPOSED_COVERAGE_DETAILS,
        PROPOSED_PREMIUM_DETAILS,
        policyRenewalPeriodProvider,
        policyCoveragePeriodProvider);
  }

  @Test(expected = AnotherRenewalAlreadyAcceptedError.class)
  public void submittingCoverageRenewal_withAnotherRenewalAlreadyAccepted_shouldThrow() {
    policyRenewalsCoordinator =
        createPolicyRenewalsCoordinator(
            Arrays.asList(PolicyRenewalBuilder.aPolicyRenewal().withStatus(ACCEPTED).build()));
    subject =
        PolicyBuilder.aPolicy()
            .withStatus(ACTIVE)
            .withPolicyHistoric(POLICY_HISTORIC)
            .withPolicyRenewalsCoordinator(policyRenewalsCoordinator)
            .withClockProvider(CLOCK_PROVIDER)
            .build();

    subject.submitCoverageRenewal(
        PROPOSED_COVERAGE_DETAILS,
        PROPOSED_PREMIUM_DETAILS,
        policyRenewalPeriodProvider,
        policyCoveragePeriodProvider);
  }
}
