package ca.ulaval.glo4003.coverage.domain.premium.adjustment;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.helper.MoneyGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinimumPremiumAdjustmentTest {
  private static final Money MINIMUM_PREMIUM = MoneyGenerator.createMoney();
  private static final Money PREMIUM_SMALLER_THAN_MINIMUM =
      MoneyGenerator.createMoneySmallerThan(MINIMUM_PREMIUM);
  private static final Money PREMIUM_GREATER_THAN_MINIMUM =
      MoneyGenerator.createMoneyGreaterThan(MINIMUM_PREMIUM);

  private MinimumPremiumAdjustment subject;

  @Before
  public void setUp() {
    subject = new MinimumPremiumAdjustment(MINIMUM_PREMIUM);
  }

  @Test
  public void applyingAdjustment_withPremiumSmallerThanMinimum_shouldReturnMinimumPremium() {
    Money premiumAdjustment = subject.apply(PREMIUM_SMALLER_THAN_MINIMUM);

    assertEquals(MINIMUM_PREMIUM, premiumAdjustment);
  }

  @Test
  public void applyingAdjustment_withPremiumGreaterThanMinimum_shouldReturnUnchangedPremium() {
    Money premiumAdjustment = subject.apply(PREMIUM_GREATER_THAN_MINIMUM);

    assertEquals(PREMIUM_GREATER_THAN_MINIMUM, premiumAdjustment);
  }
}
