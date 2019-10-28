package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.helper.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.underwriting.infrastructure.quote.price.HardCodedQuoteBasePriceCalculator.PRICE;
import static org.junit.Assert.assertEquals;

public class HardCodedQuoteBasePriceCalculatorTest {
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();

  private HardCodedQuoteBasePriceCalculator subject;

  @Before
  public void setUp() {
    subject = new HardCodedQuoteBasePriceCalculator();
  }

  @Test
  public void computingQuoteBasePrice_shouldReturnProvidedPrice() {
    Money computedQuoteBasePrice = subject.computeQuoteBasePrice(QUOTE_FORM);

    assertEquals(PRICE, computedQuoteBasePrice);
  }
}
