package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyInformation;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PolicyFactoryTest {
  private static final ClockProvider CLOCK_PROVIDER = getClockProvider();
  private static final String QUOTE_KEY = Faker.instance().internet().uuid();
  private static final Period COVERAGE_PERIOD = createPeriod();
  private static final Date BEFORE_COVERAGE_PERIOD_START_DATE =
      createDateBefore(COVERAGE_PERIOD.getStartDate());
  private static final Date AFTER_COVERAGE_PERIOD_START_DATE =
      createDateAfter(COVERAGE_PERIOD.getStartDate());
  private static final PolicyInformation POLICY_INFORMATION = createPolicyInformation();
  private static final CoverageDetails COVERAGE_DETAILS = createCoverageDetails();
  private static final PremiumDetails PREMIUM_DETAILS = createPremiumDetails();

  private PolicyFactory subject;

  @Before
  public void setUp() {
    subject = new PolicyFactory(CLOCK_PROVIDER);
  }

  @Test
  public void
      creatingPolicy_withCoveragePeriodStartingAfterPurchaseDate_shouldNotAdjustCoveragePeriod() {
    Policy policy =
        subject.create(
            QUOTE_KEY,
            COVERAGE_PERIOD,
            BEFORE_COVERAGE_PERIOD_START_DATE,
            POLICY_INFORMATION,
            COVERAGE_DETAILS,
            PREMIUM_DETAILS);

    Period expectedPeriod =
        new Period(COVERAGE_PERIOD.getStartDate(), COVERAGE_PERIOD.getEndDate());
    assertEquals(expectedPeriod, policy.getCoveragePeriod());
  }

  @Test
  public void
      creatingPolicy_withCoveragePeriodStartingBeforePurchaseDate_shouldAdjustCoveragePeriod() {
    Policy policy =
        subject.create(
            QUOTE_KEY,
            COVERAGE_PERIOD,
            AFTER_COVERAGE_PERIOD_START_DATE,
            POLICY_INFORMATION,
            COVERAGE_DETAILS,
            PREMIUM_DETAILS);

    Period expectedPeriod =
        new Period(
            AFTER_COVERAGE_PERIOD_START_DATE,
            AFTER_COVERAGE_PERIOD_START_DATE.plus(
                java.time.Period.between(
                    COVERAGE_PERIOD.getStartDate().getValue(),
                    COVERAGE_PERIOD.getEndDate().getValue())));
    assertEquals(expectedPeriod, policy.getCoveragePeriod());
  }
}
