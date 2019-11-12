package ca.ulaval.glo4003.calculator.domain.premium.adjustment;

import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaximumPremiumAdjustmentTest {
  private static final Money MAXIMUM_PREMIUM = MoneyGenerator.createMoney();
  private static final Money PREMIUM_SMALLER_THAN_MINIMUM =
      MoneyGenerator.createMoneySmallerThan(MAXIMUM_PREMIUM);
  private static final Money PREMIUM_GREATER_THAN_MINIMUM =
      MoneyGenerator.createMoneyGreaterThan(MAXIMUM_PREMIUM);

  private MaximumPremiumAdjustment subject;

  @Before
  public void setUp() {
    subject = new MaximumPremiumAdjustment(MAXIMUM_PREMIUM);
  }

  @Test
  public void applyingAdjustment_withPremiumSmallerThanMinimum_shouldReturnUnchangedPremium() {
    Money premiumAdjustment = subject.apply(PREMIUM_SMALLER_THAN_MINIMUM);

    assertEquals(PREMIUM_SMALLER_THAN_MINIMUM, premiumAdjustment);
  }

  @Test
  public void applyingAdjustment_withPremiumGreaterThanMinimum_shouldReturnMaximumPremium() {
    Money premiumAdjustment = subject.apply(PREMIUM_GREATER_THAN_MINIMUM);

    assertEquals(MAXIMUM_PREMIUM, premiumAdjustment);
  }
}
