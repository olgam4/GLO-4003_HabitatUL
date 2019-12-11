package ca.ulaval.glo4003.insuring.domain.policy.aggregate;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.helper.policy.PolicyBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyHistoricBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyRenewalBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyViewBuilder;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.insuring.domain.policy.error.InactivePolicyError;
import ca.ulaval.glo4003.insuring.domain.policy.error.PolicyRenewalNotFoundError;
import ca.ulaval.glo4003.insuring.domain.policy.error.RenewalNotYetAcceptedError;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyHistoric;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyView;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewal;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalsCoordinator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyRenewalsCoordinator;
import static ca.ulaval.glo4003.helper.policy.PolicyRenewalGenerator.createPolicyRenewalId;
import static ca.ulaval.glo4003.helper.policy.PolicyViewGenerator.createPreviousPolicyViews;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.*;
import static ca.ulaval.glo4003.insuring.domain.policy.PolicyStatus.*;
import static ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfirmRenewalTest {
  private static final ClockProvider CLOCK_PROVIDER = getClockProvider();
  private static final Date CONFIRMATION_DATE = getNowDate();
  private static final Date LAST_COVERAGE_PERIOD_END_DATE =
      CONFIRMATION_DATE.minus(java.time.Period.ofDays(1));
  private static final Period LAST_COVERAGE_PERIOD =
      new Period(createDateBefore(LAST_COVERAGE_PERIOD_END_DATE), LAST_COVERAGE_PERIOD_END_DATE);
  private static final PolicyView CURRENT_POLICY_VIEW =
      PolicyViewBuilder.aPolicyView().withCoveragePeriod(LAST_COVERAGE_PERIOD).build();
  private static final List<PolicyView> PREVIOUS_POLICY_VIEWS =
      createPreviousPolicyViews(LAST_COVERAGE_PERIOD);
  private static final PolicyRenewal PREVIOUS_POLICY_RENEWAL =
      PolicyRenewalBuilder.aPolicyRenewal().withStatus(CONFIRMED).build();
  private static final PolicyRenewalId POLICY_RENEWAL_ID = createPolicyRenewalId();
  private static final Date LAST_COVERAGE_PERIOD_DATE = createDateBetween(LAST_COVERAGE_PERIOD);

  private Policy subject;
  private PolicyHistoric policyHistoric;
  private PolicyRenewal policyRenewal;
  private PolicyRenewalsCoordinator policyRenewalsCoordinator;

  @Before
  public void setUp() {
    policyHistoric =
        PolicyHistoricBuilder.aPolicyHistoric()
            .withPolicyViews(PREVIOUS_POLICY_VIEWS)
            .withPolicyView(CURRENT_POLICY_VIEW)
            .build();
    policyRenewal =
        PolicyRenewalBuilder.aPolicyRenewal()
            .withPolicyRenewalId(POLICY_RENEWAL_ID)
            .withStatus(ACCEPTED)
            .withCoveragePeriod(new Period(getNowDate(), createFutureDate()))
            .build();
    policyRenewalsCoordinator =
        createPolicyRenewalsCoordinator(Arrays.asList(PREVIOUS_POLICY_RENEWAL, policyRenewal));
    subject =
        PolicyBuilder.aPolicy()
            .withStatus(RENEWING)
            .withPolicyHistoric(policyHistoric)
            .withPolicyRenewalsCoordinator(policyRenewalsCoordinator)
            .withClockProvider(CLOCK_PROVIDER)
            .build();
  }

  @Test
  public void confirmingRenewal_shouldSetPolicyActive() {
    subject.confirmRenewal(POLICY_RENEWAL_ID);

    assertEquals(ACTIVE, subject.getStatus());
  }

  @Test
  public void confirmingRenewal_shouldConfirmRenewal() {
    subject.confirmRenewal(POLICY_RENEWAL_ID);

    assertEquals(CONFIRMED, policyRenewal.getStatus());
  }

  @Test
  public void confirmingRenewal_shouldNotForgetRenewals() {
    subject.confirmRenewal(POLICY_RENEWAL_ID);

    assertTrue(policyRenewalsCoordinator.getRenewals().contains(PREVIOUS_POLICY_RENEWAL));
    assertTrue(policyRenewalsCoordinator.getRenewals().contains(policyRenewal));
  }

  @Test
  public void confirmingRenewal_shouldNotForgetPolicyViews() {
    subject.confirmRenewal(POLICY_RENEWAL_ID);

    assertTrue(policyHistoric.getHistoric().containsAll(PREVIOUS_POLICY_VIEWS));
    assertTrue(policyHistoric.getHistoric().contains(CURRENT_POLICY_VIEW));
  }

  @Test
  public void confirmingRenewal_shouldNotChangePreviousCoveragePeriod() {
    subject.confirmRenewal(POLICY_RENEWAL_ID);

    Period coveragePeriod = policyHistoric.getViewOn(LAST_COVERAGE_PERIOD_DATE).getCoveragePeriod();
    assertEquals(CURRENT_POLICY_VIEW.getCoveragePeriod(), coveragePeriod);
  }

  @Test
  public void confirmingRenewal_shouldNotChangePreviousPolicyInformation() {
    subject.confirmRenewal(POLICY_RENEWAL_ID);

    PolicyInformation policyInformation =
        policyHistoric.getViewOn(LAST_COVERAGE_PERIOD_DATE).getPolicyInformation();
    assertEquals(CURRENT_POLICY_VIEW.getPolicyInformation(), policyInformation);
  }

  @Test
  public void confirmingRenewal_shouldNotChangePreviousCoverageDetails() {
    subject.confirmRenewal(POLICY_RENEWAL_ID);

    CoverageDetails coverageDetails =
        policyHistoric.getViewOn(LAST_COVERAGE_PERIOD_DATE).getCoverageDetails();
    assertEquals(CURRENT_POLICY_VIEW.getCoverageDetails(), coverageDetails);
  }

  @Test
  public void confirmingRenewal_shouldNotChangePreviousPremiumDetails() {
    subject.confirmRenewal(POLICY_RENEWAL_ID);

    PremiumDetails premiumDetails =
        policyHistoric.getViewOn(LAST_COVERAGE_PERIOD_DATE).getPremiumDetails();
    assertEquals(CURRENT_POLICY_VIEW.getPremiumDetails(), premiumDetails);
  }

  @Test
  public void confirmingRenewal_shouldUpdateCurrentCoveragePeriod() {
    subject.confirmRenewal(POLICY_RENEWAL_ID);

    assertEquals(policyRenewal.getCoveragePeriod(), subject.getCoveragePeriod());
  }

  @Test
  public void confirmingRenewal_shouldNotChangeCurrentPolicyInformation() {
    subject.confirmRenewal(POLICY_RENEWAL_ID);

    assertEquals(CURRENT_POLICY_VIEW.getPolicyInformation(), subject.getPolicyInformation());
  }

  @Test
  public void confirmingRenewal_shouldUpdateCurrentCoverageDetails() {
    subject.confirmRenewal(POLICY_RENEWAL_ID);

    assertEquals(policyRenewal.getProposedCoverageDetails(), subject.getCoverageDetails());
  }

  @Test
  public void confirmingRenewal_shouldUpdateCurrentPremiumDetails() {
    subject.confirmRenewal(POLICY_RENEWAL_ID);

    assertEquals(policyRenewal.getProposedPremiumDetails(), subject.getPremiumDetails());
  }

  @Test(expected = InactivePolicyError.class)
  public void confirmingRenewal_withInactivePolicy_shouldThrow() {
    subject = PolicyBuilder.aPolicy().withStatus(INACTIVE).build();

    subject.confirmRenewal(POLICY_RENEWAL_ID);
  }

  @Test(expected = PolicyRenewalNotFoundError.class)
  public void confirmingRenewal_withNotExistingRenewal_shouldThrow() {
    subject.confirmRenewal(createPolicyRenewalId());
  }

  @Test(expected = RenewalNotYetAcceptedError.class)
  public void confirmingRenewal_withNotYetAcceptedRenewal_shouldThrow() {
    PolicyView policyView =
        PolicyViewBuilder.aPolicyView().withCoveragePeriod(createCurrentPeriod()).build();
    policyHistoric = PolicyHistoricBuilder.aPolicyHistoric().withPolicyView(policyView).build();
    policyRenewal =
        PolicyRenewalBuilder.aPolicyRenewal()
            .withPolicyRenewalId(POLICY_RENEWAL_ID)
            .withStatus(PENDING)
            .withCoveragePeriod(createCurrentPeriod())
            .build();
    policyRenewalsCoordinator = createPolicyRenewalsCoordinator(Arrays.asList(policyRenewal));
    subject =
        PolicyBuilder.aPolicy()
            .withStatus(ACTIVE)
            .withPolicyHistoric(policyHistoric)
            .withPolicyRenewalsCoordinator(policyRenewalsCoordinator)
            .withClockProvider(CLOCK_PROVIDER)
            .build();

    subject.cancelRenewal(POLICY_RENEWAL_ID);
  }
}
