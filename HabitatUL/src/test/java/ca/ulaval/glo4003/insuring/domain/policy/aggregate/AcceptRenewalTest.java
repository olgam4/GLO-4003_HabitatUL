package ca.ulaval.glo4003.insuring.domain.policy.aggregate;

import ca.ulaval.glo4003.helper.policy.PolicyBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyHistoricBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyRenewalBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyViewBuilder;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.error.*;
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
import static ca.ulaval.glo4003.insuring.domain.policy.PolicyStatus.ACTIVE;
import static ca.ulaval.glo4003.insuring.domain.policy.PolicyStatus.INACTIVE;
import static ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalStatus.*;
import static org.junit.Assert.assertEquals;

public class AcceptRenewalTest {
  private static final ClockProvider CLOCK_PROVIDER = getClockProvider();
  private static final Date ACCEPTATION_DATE = getNowDate(CLOCK_PROVIDER);
  private static final Date CURRENT_COVERAGE_PERIOD_START_DATE = createDateBefore(ACCEPTATION_DATE);
  private static final Date CURRENT_COVERAGE_PERIOD_END_DATE = createDateAfter(ACCEPTATION_DATE);
  private static final Period CURRENT_COVERAGE_PERIOD =
      new Period(CURRENT_COVERAGE_PERIOD_START_DATE, CURRENT_COVERAGE_PERIOD_END_DATE);
  private static final PolicyView CURRENT_POLICY_VIEW =
      PolicyViewBuilder.aPolicyView().withCoveragePeriod(CURRENT_COVERAGE_PERIOD).build();
  private static final List<PolicyView> PREVIOUS_POLICY_VIEWS =
      createPreviousPolicyViews(CURRENT_COVERAGE_PERIOD);
  private static final PolicyRenewal FIRST_PENDING_RENEWAL = createPendingRenewal();
  private static final PolicyRenewal SECOND_PENDING_RENEWAL = createPendingRenewal();
  private static final PolicyRenewal FIRST_OUTDATED_RENEWAL = createOutdatedRenewal();
  private static final PolicyRenewal SECOND_OUTDATED_RENEWAL = createOutdatedRenewal();
  private static final PolicyRenewal FIRST_CONFIRMED_RENEWAL = createConfirmedRenewal();
  private static final PolicyRenewal SECOND_CONFIRMED_RENEWAL = createConfirmedRenewal();
  private static final PolicyRenewal FIRST_CANCELED_RENEWAL = createCanceledRenewal();
  private static final PolicyRenewal SECOND_CANCELED_RENEWAL = createCanceledRenewal();
  private static final PolicyRenewal FIRST_EXPIRED_RENEWAL = createExpiredRenewal();
  private static final PolicyRenewal SECOND_EXPIRED_RENEWAL = createExpiredRenewal();
  private static final PolicyRenewalId POLICY_RENEWAL_ID = createPolicyRenewalId();

  private Policy subject;
  private PolicyHistoric policyHistoric;
  private List<PolicyRenewal> policyRenewals;
  private PolicyRenewalsCoordinator policyRenewalsCoordinator;
  private PolicyRenewal policyRenewal;

  private static PolicyRenewal createPendingRenewal() {
    return PolicyRenewalBuilder.aPolicyRenewal().withStatus(PENDING).build();
  }

  private static PolicyRenewal createOutdatedRenewal() {
    return PolicyRenewalBuilder.aPolicyRenewal()
        .withStatus(PENDING)
        .withCoveragePeriod(createPastPeriod())
        .build();
  }

  private static PolicyRenewal createConfirmedRenewal() {
    return PolicyRenewalBuilder.aPolicyRenewal().withStatus(CONFIRMED).build();
  }

  private static PolicyRenewal createCanceledRenewal() {
    return PolicyRenewalBuilder.aPolicyRenewal().withStatus(CANCELED).build();
  }

  private static PolicyRenewal createExpiredRenewal() {
    return PolicyRenewalBuilder.aPolicyRenewal().withStatus(EXPIRED).build();
  }

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
            .withStatus(PENDING)
            .withCoveragePeriod(createFuturePeriod())
            .build();
    policyRenewals =
        Arrays.asList(
            policyRenewal,
            FIRST_PENDING_RENEWAL,
            SECOND_PENDING_RENEWAL,
            FIRST_OUTDATED_RENEWAL,
            SECOND_OUTDATED_RENEWAL,
            FIRST_CONFIRMED_RENEWAL,
            SECOND_CONFIRMED_RENEWAL,
            FIRST_CANCELED_RENEWAL,
            SECOND_CANCELED_RENEWAL,
            FIRST_EXPIRED_RENEWAL,
            SECOND_EXPIRED_RENEWAL);
    policyRenewalsCoordinator = createPolicyRenewalsCoordinator(policyRenewals);
    subject =
        PolicyBuilder.aPolicy()
            .withStatus(ACTIVE)
            .withPolicyHistoric(policyHistoric)
            .withPolicyRenewalsCoordinator(policyRenewalsCoordinator)
            .withClockProvider(CLOCK_PROVIDER)
            .build();
  }

  @Test
  public void acceptingRenewal_shouldExpireOutdatedRenewals() {
    subject.acceptRenewal(POLICY_RENEWAL_ID);

    assertEquals(EXPIRED, FIRST_OUTDATED_RENEWAL.getStatus());
    assertEquals(EXPIRED, SECOND_OUTDATED_RENEWAL.getStatus());
  }

  @Test
  public void acceptingRenewal_shouldAcceptRenewal() {
    subject.acceptRenewal(POLICY_RENEWAL_ID);

    assertEquals(ACCEPTED, policyRenewal.getStatus());
  }

  @Test
  public void acceptingRenewal_shouldExpirePendingRenewals() {
    subject.acceptRenewal(POLICY_RENEWAL_ID);

    assertEquals(EXPIRED, FIRST_PENDING_RENEWAL.getStatus());
    assertEquals(EXPIRED, SECOND_PENDING_RENEWAL.getStatus());
  }

  @Test
  public void acceptingRenewal_shouldNotAffectAlreadyConfirmedRenewals() {
    subject.acceptRenewal(POLICY_RENEWAL_ID);

    assertEquals(CONFIRMED, FIRST_CONFIRMED_RENEWAL.getStatus());
    assertEquals(CONFIRMED, SECOND_CONFIRMED_RENEWAL.getStatus());
  }

  @Test
  public void acceptingRenewal_shouldNotAffectAlreadyCanceledRenewals() {
    subject.acceptRenewal(POLICY_RENEWAL_ID);

    assertEquals(CANCELED, FIRST_CANCELED_RENEWAL.getStatus());
    assertEquals(CANCELED, SECOND_CANCELED_RENEWAL.getStatus());
  }

  @Test
  public void acceptingRenewal_shouldNotAffectAlreadyExpiredRenewals() {
    subject.acceptRenewal(POLICY_RENEWAL_ID);

    assertEquals(EXPIRED, FIRST_EXPIRED_RENEWAL.getStatus());
    assertEquals(EXPIRED, SECOND_EXPIRED_RENEWAL.getStatus());
  }

  @Test(expected = InactivePolicyError.class)
  public void acceptingRenewal_withInactivePolicy_shouldThrow() {
    subject = PolicyBuilder.aPolicy().withStatus(INACTIVE).build();

    subject.acceptRenewal(POLICY_RENEWAL_ID);
  }

  @Test(expected = AnotherRenewalAlreadyAcceptedError.class)
  public void acceptingRenewal_withAnotherRenewalAlreadyAccepted_shouldThrow() {
    subject.acceptRenewal(POLICY_RENEWAL_ID);

    subject.acceptRenewal(FIRST_PENDING_RENEWAL.getPolicyRenewalId());
  }

  @Test(expected = PolicyRenewalNotFoundError.class)
  public void acceptingRenewal_withNotExistingRenewal_shouldThrow() {
    subject.acceptRenewal(createPolicyRenewalId());
  }

  @Test(expected = RenewalAlreadyConfirmedError.class)
  public void acceptingRenewal_withAlreadyConfirmedRenewal_shouldThrow() {
    subject.acceptRenewal(FIRST_CONFIRMED_RENEWAL.getPolicyRenewalId());
  }

  @Test(expected = RenewalAlreadyCanceledError.class)
  public void acceptingRenewal_withAlreadyCanceledRenewal_shouldThrow() {
    subject.acceptRenewal(FIRST_CANCELED_RENEWAL.getPolicyRenewalId());
  }

  @Test(expected = RenewalExpiredError.class)
  public void acceptingRenewal_withExpiredRenewal_shouldThrow() {
    subject.acceptRenewal(FIRST_EXPIRED_RENEWAL.getPolicyRenewalId());
  }
}
