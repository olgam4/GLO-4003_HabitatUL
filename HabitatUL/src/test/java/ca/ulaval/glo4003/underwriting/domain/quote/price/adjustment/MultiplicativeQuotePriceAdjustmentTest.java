package ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment;

import ca.ulaval.glo4003.generator.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MultiplicativeQuotePriceAdjustmentTest {
  private static final Money PRICE = MoneyGenerator.create();
  private static final double FACTOR = 1.2;

  private MultiplicativeQuotePriceAdjustment subject;

  @Before
  public void setUp() {
    subject = new MultiplicativeQuotePriceAdjustment(FACTOR);
  }

  @Test
  public void applyingAdjustment_shouldReturnAdjustmentAmount() {
    Money adjustmentAmount = subject.apply(PRICE);

    Money expectedAmount = PRICE.multiply(FACTOR);
    assertEquals(expectedAmount, adjustmentAmount);
  }
}
