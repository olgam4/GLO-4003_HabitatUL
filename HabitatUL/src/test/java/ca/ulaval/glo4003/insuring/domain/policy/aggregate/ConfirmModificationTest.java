package ca.ulaval.glo4003.insuring.domain.policy.aggregate;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.helper.policy.PolicyBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyHistoricBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyModificationBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyViewBuilder;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.insuring.domain.policy.error.InactivePolicyError;
import ca.ulaval.glo4003.insuring.domain.policy.error.ModificationAlreadyConfirmedError;
import ca.ulaval.glo4003.insuring.domain.policy.error.ModificationExpiredError;
import ca.ulaval.glo4003.insuring.domain.policy.error.PolicyModificationNotFoundError;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyHistoric;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyView;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationsCoordinator;
import ca.ulaval.glo4003.insuring.domain.policy.modification.modifier.PolicyInformationModifier;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyModificationsCoordinator;
import static ca.ulaval.glo4003.helper.policy.PolicyInformationGenerator.createPolicyInformation;
import static ca.ulaval.glo4003.helper.policy.PolicyModificationGenerator.createPolicyModificationId;
import static ca.ulaval.glo4003.helper.policy.PolicyViewGenerator.createPreviousPolicyViews;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.*;
import static ca.ulaval.glo4003.insuring.domain.policy.PolicyStatus.ACTIVE;
import static ca.ulaval.glo4003.insuring.domain.policy.PolicyStatus.INACTIVE;
import static ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationStatus.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConfirmModificationTest {
  private static final ClockProvider CLOCK_PROVIDER = getClockProvider();
  private static final Date CONFIRMATION_EFFECTIVE_DATE = getNowDate(CLOCK_PROVIDER);;
  private static final Date CURRENT_COVERAGE_PERIOD_START_DATE =
      createDateBefore(CONFIRMATION_EFFECTIVE_DATE);
  private static final Date CURRENT_COVERAGE_PERIOD_END_DATE =
      createDateAfter(CONFIRMATION_EFFECTIVE_DATE);
  private static final Period CURRENT_COVERAGE_PERIOD =
      new Period(CURRENT_COVERAGE_PERIOD_START_DATE, CURRENT_COVERAGE_PERIOD_END_DATE);
  private static final PolicyView CURRENT_POLICY_VIEW =
      PolicyViewBuilder.aPolicyView().withCoveragePeriod(CURRENT_COVERAGE_PERIOD).build();
  private static final List<PolicyView> PREVIOUS_POLICY_VIEWS =
      createPreviousPolicyViews(CURRENT_COVERAGE_PERIOD);
  private static final PolicyModification FIRST_CONFIRMED_MODIFICATION =
      createConfirmedModification();
  private static final PolicyModification SECOND_CONFIRMED_MODIFICATION =
      createConfirmedModification();
  private static final PolicyModification FIRST_PENDING_MODIFICATION = createPendingModification();
  private static final PolicyModification SECOND_PENDING_MODIFICATION = createPendingModification();
  private static final PolicyModification FIRST_OUTDATED_MODIFICATION =
      createOutdatedModification();
  private static final PolicyModification SECOND_OUTDATED_MODIFICATION =
      createOutdatedModification();
  private static final PolicyModification FIRST_EXPIRED_MODIFICATION = createExpiredModification();
  private static final PolicyModification SECOND_EXPIRED_MODIFICATION = createExpiredModification();
  private static final PolicyModificationId POLICY_MODIFICATION_ID = createPolicyModificationId();
  private static final PolicyInformation UPDATED_POLICY_INFORMATION = createPolicyInformation();

  @Mock private PolicyInformationModifier policyInformationModifier;

  private Policy subject;
  private PolicyHistoric policyHistoric;
  private List<PolicyModification> policyModifications;
  private PolicyModificationsCoordinator policyModificationsCoordinator;
  private PolicyModification policyModification;

  private static PolicyModification createConfirmedModification() {
    return PolicyModificationBuilder.aPolicyModification().withStatus(CONFIRMED).build();
  }

  private static PolicyModification createPendingModification() {
    return PolicyModificationBuilder.aPolicyModification().withStatus(PENDING).build();
  }

  private static PolicyModification createOutdatedModification() {
    return PolicyModificationBuilder.aPolicyModification()
        .withStatus(PENDING)
        .withExpirationDate(createPastDateTime())
        .build();
  }

  private static PolicyModification createExpiredModification() {
    return PolicyModificationBuilder.aPolicyModification().withStatus(EXPIRED).build();
  }

  @Before
  public void setUp() {
    policyHistoric =
        PolicyHistoricBuilder.aPolicyHistoric()
            .withPolicyViews(PREVIOUS_POLICY_VIEWS)
            .withPolicyView(CURRENT_POLICY_VIEW)
            .build();
    when(policyInformationModifier.modify(any(PolicyInformation.class)))
        .thenReturn(UPDATED_POLICY_INFORMATION);
    policyModification =
        PolicyModificationBuilder.aPolicyModification()
            .withPolicyModificationId(POLICY_MODIFICATION_ID)
            .withStatus(PENDING)
            .withExpirationDate(createFutureDateTime())
            .withPolicyInformationModifier(policyInformationModifier)
            .build();
    policyModifications =
        Arrays.asList(
            policyModification,
            FIRST_CONFIRMED_MODIFICATION,
            SECOND_CONFIRMED_MODIFICATION,
            FIRST_PENDING_MODIFICATION,
            SECOND_PENDING_MODIFICATION,
            FIRST_OUTDATED_MODIFICATION,
            SECOND_OUTDATED_MODIFICATION,
            FIRST_EXPIRED_MODIFICATION,
            SECOND_EXPIRED_MODIFICATION);
    policyModificationsCoordinator = createPolicyModificationsCoordinator(policyModifications);
    subject =
        PolicyBuilder.aPolicy()
            .withStatus(ACTIVE)
            .withPolicyHistoric(policyHistoric)
            .withPolicyModificationsCoordinator(policyModificationsCoordinator)
            .withClockProvider(CLOCK_PROVIDER)
            .build();
  }

  @Test
  public void confirmingModification_shouldExpireOutdatedModifications() {
    subject.confirmModification(POLICY_MODIFICATION_ID);

    assertEquals(EXPIRED, FIRST_OUTDATED_MODIFICATION.getStatus());
    assertEquals(EXPIRED, SECOND_OUTDATED_MODIFICATION.getStatus());
  }

  @Test
  public void confirmingModification_shouldConfirmModification() {
    subject.confirmModification(POLICY_MODIFICATION_ID);

    assertEquals(CONFIRMED, policyModification.getStatus());
  }

  @Test
  public void confirmingModification_shouldExpirePendingModifications() {
    subject.confirmModification(POLICY_MODIFICATION_ID);

    assertEquals(EXPIRED, FIRST_PENDING_MODIFICATION.getStatus());
    assertEquals(EXPIRED, SECOND_PENDING_MODIFICATION.getStatus());
  }

  @Test
  public void confirmingModification_shouldNotAffectAlreadyConfirmedModifications() {
    subject.confirmModification(POLICY_MODIFICATION_ID);

    assertEquals(CONFIRMED, FIRST_CONFIRMED_MODIFICATION.getStatus());
    assertEquals(CONFIRMED, SECOND_CONFIRMED_MODIFICATION.getStatus());
  }

  @Test
  public void confirmingModification_shouldNotAffectAlreadyExpiredModifications() {
    subject.confirmModification(POLICY_MODIFICATION_ID);

    assertEquals(EXPIRED, FIRST_EXPIRED_MODIFICATION.getStatus());
    assertEquals(EXPIRED, SECOND_EXPIRED_MODIFICATION.getStatus());
  }

  @Test
  public void confirmingModification_shouldNotForgetModifications() {
    subject.confirmModification(POLICY_MODIFICATION_ID);

    assertTrue(policyModificationsCoordinator.getModifications().containsAll(policyModifications));
  }

  @Test
  public void confirmingModification_shouldNotForgetOtherPolicyViews() {
    subject.confirmModification(POLICY_MODIFICATION_ID);

    assertTrue(policyHistoric.getHistoric().containsAll(PREVIOUS_POLICY_VIEWS));
  }

  @Test
  public void confirmingModification_shouldForgetCurrentPolicyView() {
    subject.confirmModification(POLICY_MODIFICATION_ID);

    assertFalse(policyHistoric.getHistoric().contains(CURRENT_POLICY_VIEW));
  }

  @Test
  public void confirmingModification_shouldSplitCurrentCoveragePeriod() {
    subject.confirmModification(POLICY_MODIFICATION_ID);

    Period expectedCoveragePeriod =
        new Period(CONFIRMATION_EFFECTIVE_DATE, CURRENT_COVERAGE_PERIOD.getEndDate());
    Period coveragePeriod =
        policyHistoric.getViewOn(CONFIRMATION_EFFECTIVE_DATE).getCoveragePeriod();
    assertEquals(expectedCoveragePeriod, coveragePeriod);
  }

  @Test
  public void confirmingModification_shouldNotChangeCurrentPolicyInformation() {
    subject.confirmModification(POLICY_MODIFICATION_ID);

    PolicyInformation policyInformation =
        policyHistoric.getViewOn(CURRENT_COVERAGE_PERIOD_START_DATE).getPolicyInformation();
    assertEquals(CURRENT_POLICY_VIEW.getPolicyInformation(), policyInformation);
  }

  @Test
  public void confirmingModification_shouldNotChangeCurrentCoverageDetails() {
    subject.confirmModification(POLICY_MODIFICATION_ID);

    CoverageDetails coverageDetails =
        policyHistoric.getViewOn(CURRENT_COVERAGE_PERIOD_START_DATE).getCoverageDetails();
    assertEquals(CURRENT_POLICY_VIEW.getCoverageDetails(), coverageDetails);
  }

  @Test
  public void confirmingModification_shouldNotChangeCurrentPremiumDetails() {
    subject.confirmModification(POLICY_MODIFICATION_ID);

    PremiumDetails premiumDetails =
        policyHistoric.getViewOn(CURRENT_COVERAGE_PERIOD_START_DATE).getPremiumDetails();
    assertEquals(CURRENT_POLICY_VIEW.getPremiumDetails(), premiumDetails);
  }

  @Test
  public void confirmingModification_shouldApplyModificationUntilEndOfCurrentCoveragePeriod() {
    subject.confirmModification(POLICY_MODIFICATION_ID);

    Period expectedCoveragePeriod =
        new Period(CONFIRMATION_EFFECTIVE_DATE, CURRENT_COVERAGE_PERIOD_END_DATE);
    assertEquals(expectedCoveragePeriod, subject.getCoveragePeriod());
  }

  @Test
  public void confirmingModification_shouldUpdateCurrentPolicyInformation() {
    subject.confirmModification(POLICY_MODIFICATION_ID);

    assertEquals(UPDATED_POLICY_INFORMATION, subject.getPolicyInformation());
  }

  @Test
  public void confirmingModification_shouldUpdateCurrentCoverageDetails() {
    subject.confirmModification(POLICY_MODIFICATION_ID);

    assertEquals(policyModification.getProposedCoverageDetails(), subject.getCoverageDetails());
  }

  @Test
  public void confirmingModification_shouldUpdateCurrentPremiumDetails() {
    subject.confirmModification(POLICY_MODIFICATION_ID);

    assertEquals(policyModification.getProposedPremiumDetails(), subject.getPremiumDetails());
  }

  @Test(expected = InactivePolicyError.class)
  public void confirmingModification_withInactivePolicy_shouldThrow() {
    subject = PolicyBuilder.aPolicy().withStatus(INACTIVE).build();

    subject.confirmModification(POLICY_MODIFICATION_ID);
  }

  @Test(expected = PolicyModificationNotFoundError.class)
  public void confirmingModification_withNotExistingModification_shouldThrow() {
    subject.confirmModification(createPolicyModificationId());
  }

  @Test(expected = ModificationAlreadyConfirmedError.class)
  public void confirmingModification_withAlreadyConfirmedModification_shouldThrow() {
    subject.confirmModification(FIRST_CONFIRMED_MODIFICATION.getPolicyModificationId());
  }

  @Test(expected = ModificationExpiredError.class)
  public void confirmingModification_withExpiredModification_shouldThrow() {
    subject.confirmModification(FIRST_EXPIRED_MODIFICATION.getPolicyModificationId());
  }
}
