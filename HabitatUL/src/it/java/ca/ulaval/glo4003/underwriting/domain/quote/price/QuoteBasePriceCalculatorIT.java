package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class QuoteBasePriceCalculatorIT {
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();

  private QuoteBasePriceCalculator subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void computingQuoteBasePrice_shouldReturnBasePrice() {
    Money price = subject.computeQuoteBasePrice(QUOTE_FORM);

    assertNotNull(price);
  }

  public abstract QuoteBasePriceCalculator createSubject();
}
