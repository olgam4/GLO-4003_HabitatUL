package ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NullQuotePriceAdjustmentTest {
  private static final Money PRICE = MoneyGenerator.createMoney();
  private NullQuotePriceAdjustment subject;

  @Before
  public void setUp() {
    subject = new NullQuotePriceAdjustment();
  }

  @Test
  public void applyingAdjustment_shouldReturnAdjustmentAmount() {
    Money adjustmentAmount = subject.apply(PRICE);

    assertEquals(Money.ZERO, adjustmentAmount);
  }
}
