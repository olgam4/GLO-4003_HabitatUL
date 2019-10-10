package ca.ulaval.glo4003.underwriting.domain;

import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.underwriting.domain.price.Price;
import ca.ulaval.glo4003.underwriting.domain.price.QuotePriceCalculator;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class QuotePriceCalculatorIT {
  private QuotePriceCalculator subject;
  private QuoteForm quoteForm;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void computingQuotePrice_shouldProducePrice() {
    quoteForm = QuoteFormGenerator.createQuoteForm();

    Price price = subject.computeQuotePrice(quoteForm);

    assertNotNull(price);
  }

  public abstract QuotePriceCalculator createSubject();
}
