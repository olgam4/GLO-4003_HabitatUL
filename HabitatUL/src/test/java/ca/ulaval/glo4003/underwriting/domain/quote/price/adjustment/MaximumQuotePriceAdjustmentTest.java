package ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaximumQuotePriceAdjustmentTest {
  private static final Money MAXIMUM = MoneyGenerator.createMoney();
  private static final Money PRICE_SMALLER_THAN_MINIMUM =
      MoneyGenerator.createMoneySmallerThan(MAXIMUM);
  private static final Money PRICE_GREATER_THAN_MINIMUM =
      MoneyGenerator.createMoneyGreaterThan(MAXIMUM);

  private MaximumQuotePriceAdjustment subject;

  @Before
  public void setUp() {
    subject = new MaximumQuotePriceAdjustment(MAXIMUM);
  }

  @Test
  public void applyingAdjustment_withPriceSmallerThanMinimum_shouldReturnUnchangedPrice() {
    Money adjustedPrice = subject.apply(PRICE_SMALLER_THAN_MINIMUM);

    assertEquals(PRICE_SMALLER_THAN_MINIMUM, adjustedPrice);
  }

  @Test
  public void applyingAdjustment_withPriceGreaterThanMinimum_shouldReturnMaximumPrice() {
    Money adjustedPrice = subject.apply(PRICE_GREATER_THAN_MINIMUM);

    assertEquals(MAXIMUM, adjustedPrice);
  }
}
