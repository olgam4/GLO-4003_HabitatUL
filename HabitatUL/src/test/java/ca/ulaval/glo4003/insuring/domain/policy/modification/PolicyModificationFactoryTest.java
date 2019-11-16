package ca.ulaval.glo4003.insuring.domain.policy.modification;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsBuilder;
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
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createMoney;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createDuration;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.getClockProvider;
import static ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationState.PENDING;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PolicyModificationFactoryTest {
  private static final ClockProvider CLOCK_PROVIDER = getClockProvider();
  private static final Duration VALIDITY_PERIOD = createDuration();
  private static final CoverageDetails UPDATED_COVERAGE_DETAILS = createCoverageDetails();
  private static final Money CURRENT_TOTAL_PREMIUM = createMoney();
  private static final PremiumDetails CURRENT_PREMIUM_DETAILS =
      PremiumDetailsBuilder.aPremiumDetails()
          .withBasicBlockPremiumDetail(CURRENT_TOTAL_PREMIUM)
          .build();
  private static final Money UPDATED_TOTAL_PREMIUM = createMoney();
  private static final PremiumDetails UPDATED_PREMIUM_DETAILS =
      PremiumDetailsBuilder.aPremiumDetails()
          .withBasicBlockPremiumDetail(UPDATED_TOTAL_PREMIUM)
          .build();

  @Mock private PolicyModificationValidityPeriodProvider policyModificationValidityPeriodProvider;
  @Mock private PolicyInformationModifier policyInformationModifier;

  private PolicyModificationFactory subject;

  @Before
  public void setUp() {
    when(policyModificationValidityPeriodProvider.getPolicyModificationValidityPeriod())
        .thenReturn(VALIDITY_PERIOD);
    subject =
        new PolicyModificationFactory(policyModificationValidityPeriodProvider, CLOCK_PROVIDER);
  }

  @Test
  public void creatingPolicyModification_shouldComputeExpirationDate() {
    PolicyModification policyModification =
        subject.create(
            UPDATED_COVERAGE_DETAILS,
            CURRENT_PREMIUM_DETAILS,
            UPDATED_PREMIUM_DETAILS,
            policyInformationModifier);

    DateTime expectedExpirationDate =
        DateTime.from(LocalDateTime.now(CLOCK_PROVIDER.getClock()).plus(VALIDITY_PERIOD));
    assertEquals(expectedExpirationDate, policyModification.getExpirationDate());
  }

  @Test
  public void creatingPolicyModification_shouldCreatePendingPolicyModification() {
    PolicyModification policyModification =
        subject.create(
            UPDATED_COVERAGE_DETAILS,
            CURRENT_PREMIUM_DETAILS,
            UPDATED_PREMIUM_DETAILS,
            policyInformationModifier);

    assertEquals(PENDING, policyModification.getState());
  }

  @Test
  public void creatingPolicyModification_shouldComputePremiumAdjustment() {
    PolicyModification policyModification =
        subject.create(
            UPDATED_COVERAGE_DETAILS,
            CURRENT_PREMIUM_DETAILS,
            UPDATED_PREMIUM_DETAILS,
            policyInformationModifier);

    Money expectedPremiumAdjustment = UPDATED_TOTAL_PREMIUM.subtract(CURRENT_TOTAL_PREMIUM);
    assertEquals(expectedPremiumAdjustment, policyModification.getPremiumAdjustment());
  }
}
