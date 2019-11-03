package ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinimumQuotePriceAdjustmentTest {
  private static final Money MINIMUM = MoneyGenerator.createMoney();
  private static final Money PRICE_SMALLER_THAN_MINIMUM =
      MoneyGenerator.createMoneySmallerThan(MINIMUM);
  private static final Money PRICE_GREATER_THAN_MINIMUM =
      MoneyGenerator.createMoneyGreaterThan(MINIMUM);

  private MinimumQuotePriceAdjustment subject;

  @Before
  public void setUp() {
    subject = new MinimumQuotePriceAdjustment(MINIMUM);
  }

  @Test
  public void applyingAdjustment_withPriceSmallerThanMinimum_shouldReturnMinimumPrice() {
    Money adjustedPrice = subject.apply(PRICE_SMALLER_THAN_MINIMUM);

    assertEquals(MINIMUM, adjustedPrice);
  }

  @Test
  public void applyingAdjustment_withPriceGreaterThanMinimum_shouldReturnUnchangedPrice() {
    Money adjustedPrice = subject.apply(PRICE_GREATER_THAN_MINIMUM);

    assertEquals(PRICE_GREATER_THAN_MINIMUM, adjustedPrice);
  }
}
