package ca.ulaval.glo4003.coverage.domain.premium.adjustment;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.helper.MoneyGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NoPremiumAdjustmentTest {
  private static final Money PREMIUM = MoneyGenerator.createMoney();
  private NoPremiumAdjustment subject;

  @Before
  public void setUp() {
    subject = new NoPremiumAdjustment();
  }

  @Test
  public void applyingAdjustment_shouldReturnUnchangedPremium() {
    Money premiumAdjustment = subject.apply(PREMIUM);

    assertEquals(PREMIUM, premiumAdjustment);
  }
}
