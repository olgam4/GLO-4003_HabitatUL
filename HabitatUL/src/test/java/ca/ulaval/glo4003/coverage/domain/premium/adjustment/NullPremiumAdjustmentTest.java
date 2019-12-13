package ca.ulaval.glo4003.coverage.domain.premium.adjustment;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.helper.MoneyGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NullPremiumAdjustmentTest {
  private static final Money PREMIUM = MoneyGenerator.createMoney();
  private NullPremiumAdjustment subject;

  @Before
  public void setUp() {
    subject = new NullPremiumAdjustment();
  }

  @Test
  public void applyingAdjustment_shouldReturnPremiumAdjustment() {
    Money premiumAdjustment = subject.apply(PREMIUM);

    assertEquals(Money.ZERO, premiumAdjustment);
  }
}
