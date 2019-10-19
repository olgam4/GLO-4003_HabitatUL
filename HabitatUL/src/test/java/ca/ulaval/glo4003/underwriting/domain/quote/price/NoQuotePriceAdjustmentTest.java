package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.generator.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.NoQuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NoQuotePriceAdjustmentTest {
  private static final Money PRICE = MoneyGenerator.create();
  private NoQuotePriceAdjustment subject;

  @Before
  public void setUp() {
    subject = new NoQuotePriceAdjustment();
  }

  @Test
  public void applyingAdjustment_shouldReturnAdjustmentAmount() {
    Money adjustmentAmount = subject.apply(PRICE);

    assertEquals(new Money(Amount.ZERO), adjustmentAmount);
  }
}
