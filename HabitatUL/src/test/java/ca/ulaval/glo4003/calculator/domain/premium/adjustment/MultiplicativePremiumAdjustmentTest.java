package ca.ulaval.glo4003.calculator.domain.premium.adjustment;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MultiplicativePremiumAdjustmentTest {
  private static final Money PREMIUM = MoneyGenerator.createMoney();
  private static final double FACTOR = 1.2;

  private MultiplicativePremiumAdjustment subject;

  @Before
  public void setUp() {
    subject = new MultiplicativePremiumAdjustment(FACTOR);
  }

  @Test
  public void applyingAdjustment_shouldReturnPremiumAdjustment() {
    Money premiumAdjustment = subject.apply(PREMIUM);

    Money expectedPremiumAdjustment = PREMIUM.multiply(FACTOR);
    assertEquals(expectedPremiumAdjustment, premiumAdjustment);
  }
}
