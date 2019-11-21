package ca.ulaval.glo4003.insuring.domain.policy.aggregate;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyHistoricBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyViewBuilder;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.error.InactivePolicyError;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyHistoric;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyView;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationValidityPeriodProvider;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationsCoordinator;
import ca.ulaval.glo4003.insuring.domain.policy.modification.modifier.InsureBicyclePolicyInformationModifier;
import ca.ulaval.glo4003.insuring.domain.policy.modification.modifier.PolicyInformationModifier;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDateTime;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.BicycleGenerator.createBicycle;
import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyModificationsCoordinator;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createMoney;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createDuration;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.getClockProvider;
import static ca.ulaval.glo4003.insuring.domain.policy.PolicyStatus.ACTIVE;
import static ca.ulaval.glo4003.insuring.domain.policy.PolicyStatus.INACTIVE;
import static ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationStatus.PENDING;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubmitInsureBicycleModificationTest {
  private static final ClockProvider CLOCK_PROVIDER = getClockProvider();
  private static final Money CURRENT_TOTAL_PREMIUM = createMoney();
  private static final PremiumDetails CURRENT_PREMIUM_DETAILS =
      PremiumDetailsBuilder.aPremiumDetails()
          .withBasicBlockPremiumDetail(CURRENT_TOTAL_PREMIUM)
          .build();
  private static final PolicyView CURRENT_POLICY_VIEW =
      PolicyViewBuilder.aPolicyView().withPremiumDetails(CURRENT_PREMIUM_DETAILS).build();
  private static final PolicyHistoric POLICY_HISTORIC =
      PolicyHistoricBuilder.aPolicyHistoric().withPolicyView(CURRENT_POLICY_VIEW).build();
  private static final Duration VALIDITY_PERIOD = createDuration();
  private static final CoverageDetails PROPOSED_COVERAGE_DETAILS = createCoverageDetails();
  private static final Money UPDATED_TOTAL_PREMIUM = createMoney();
  private static final PremiumDetails PROPOSED_PREMIUM_DETAILS =
      PremiumDetailsBuilder.aPremiumDetails()
          .withBasicBlockPremiumDetail(UPDATED_TOTAL_PREMIUM)
          .build();
  private static final Bicycle BICYCLE = createBicycle();

  @Mock private PolicyModificationValidityPeriodProvider policyModificationValidityPeriodProvider;

  private Policy subject;
  private PolicyModificationsCoordinator policyModificationsCoordinator;

  @Before
  public void setUp() {
    when(policyModificationValidityPeriodProvider.getPolicyModificationValidityPeriod())
        .thenReturn(VALIDITY_PERIOD);
    policyModificationsCoordinator = createPolicyModificationsCoordinator();
    subject =
        PolicyBuilder.aPolicy()
            .withStatus(ACTIVE)
            .withPolicyHistoric(POLICY_HISTORIC)
            .withPolicyModificationsCoordinator(policyModificationsCoordinator)
            .withClockProvider(CLOCK_PROVIDER)
            .build();
  }

  @Test
  public void submittingInsureBicycleModification_shouldComputeExpirationDate() {
    PolicyModification policyModification =
        subject.submitInsureBicycleModification(
            BICYCLE,
            PROPOSED_COVERAGE_DETAILS,
            PROPOSED_PREMIUM_DETAILS,
            policyModificationValidityPeriodProvider);

    DateTime expectedExpirationDate =
        DateTime.from(LocalDateTime.now(CLOCK_PROVIDER.getClock()).plus(VALIDITY_PERIOD));
    assertEquals(expectedExpirationDate, policyModification.getExpirationDate());
  }

  @Test
  public void submittingInsureBicycleModification_shouldCreatePendingPolicyModification() {
    PolicyModification policyModification =
        subject.submitInsureBicycleModification(
            BICYCLE,
            PROPOSED_COVERAGE_DETAILS,
            PROPOSED_PREMIUM_DETAILS,
            policyModificationValidityPeriodProvider);

    assertEquals(PENDING, policyModification.getStatus());
  }

  @Test
  public void submittingInsureBicycleModification_shouldComputePremiumAdjustment() {
    PolicyModification policyModification =
        subject.submitInsureBicycleModification(
            BICYCLE,
            PROPOSED_COVERAGE_DETAILS,
            PROPOSED_PREMIUM_DETAILS,
            policyModificationValidityPeriodProvider);

    Money expectedPremiumAdjustment = UPDATED_TOTAL_PREMIUM.subtract(CURRENT_TOTAL_PREMIUM);
    assertEquals(expectedPremiumAdjustment, policyModification.getProposedPremiumAdjustment());
  }

  @Test
  public void
      submittingInsureBicycleModification_shouldCreateCorrespondingPolicyInformationModifier() {
    PolicyModification policyModification =
        subject.submitInsureBicycleModification(
            BICYCLE,
            PROPOSED_COVERAGE_DETAILS,
            PROPOSED_PREMIUM_DETAILS,
            policyModificationValidityPeriodProvider);

    PolicyInformationModifier expectedPolicyInformationModifier =
        new InsureBicyclePolicyInformationModifier(BICYCLE);
    assertEquals(
        expectedPolicyInformationModifier, policyModification.getPolicyInformationModifier());
  }

  @Test
  public void submittingInsureBicycleModification_shouldRegisterModification() {
    PolicyModification policyModification =
        subject.submitInsureBicycleModification(
            BICYCLE,
            PROPOSED_COVERAGE_DETAILS,
            PROPOSED_PREMIUM_DETAILS,
            policyModificationValidityPeriodProvider);

    assertEquals(
        policyModification,
        policyModificationsCoordinator.getModification(
            policyModification.getPolicyModificationId()));
  }

  @Test(expected = InactivePolicyError.class)
  public void submittingInsureBicycleModification_withInactivePolicy_shouldThrow() {
    subject = PolicyBuilder.aPolicy().withStatus(INACTIVE).build();

    subject.submitInsureBicycleModification(
        BICYCLE,
        PROPOSED_COVERAGE_DETAILS,
        PROPOSED_PREMIUM_DETAILS,
        policyModificationValidityPeriodProvider);
  }
}
