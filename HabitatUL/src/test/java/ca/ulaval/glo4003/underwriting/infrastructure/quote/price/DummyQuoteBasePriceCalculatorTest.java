package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DummyQuoteBasePriceCalculatorTest {
  private static final Money PRICE = MoneyGenerator.create();
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();

  private DummyQuoteBasePriceCalculator subject;

  @Before
  public void setUp() {
    subject = new DummyQuoteBasePriceCalculator(PRICE);
  }

  @Test
  public void computingQuoteBasePrice_shouldReturnProvidedPrice() {
    Money computedQuoteBasePrice = subject.computeQuoteBasePrice(QUOTE_FORM);

    assertEquals(PRICE, computedQuoteBasePrice);
  }
}
