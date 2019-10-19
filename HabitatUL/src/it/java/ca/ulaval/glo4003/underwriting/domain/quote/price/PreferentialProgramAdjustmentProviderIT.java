package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.generator.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MultiplicativeQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class PreferentialProgramAdjustmentProviderIT {
  private static final Money BASE_PRICE = MoneyGenerator.create();
  private static final String PREFERENTIAL_PROGRAM = "PREFERENTIAL_PROGRAM";
  private static final String REGULAR_PROGRAM = "REGULAR_PROGRAM";
  private static final QuotePriceAdjustment PROGRAM_ADJUSTMENT =
      new MultiplicativeQuotePriceAdjustment(-0.15);

  private PreferentialProgramAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingAdjustment_withPreferentialProgram_shouldProvideAdjustment() {
    QuotePriceAdjustment adjustment = subject.getAdjustment(PREFERENTIAL_PROGRAM);

    Money expected = PROGRAM_ADJUSTMENT.apply(BASE_PRICE);
    Money actual = adjustment.apply(BASE_PRICE);
    assertEquals(expected, actual);
  }

  @Test
  public void gettingAdjustment_withRegularProgram_shouldProvideNoAdjustment() {
    QuotePriceAdjustment adjustment = subject.getAdjustment(REGULAR_PROGRAM);

    Money expected = new Money(Amount.ZERO);
    Money actual = adjustment.apply(BASE_PRICE);
    assertEquals(expected, actual);
  }

  public abstract PreferentialProgramAdjustmentProvider createSubject();
}
