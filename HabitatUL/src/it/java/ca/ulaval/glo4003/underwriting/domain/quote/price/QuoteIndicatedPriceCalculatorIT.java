package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class QuoteIndicatedPriceCalculatorIT {
  private QuoteIndicatedPriceCalculator subject;
  private QuoteForm quoteForm;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void computingQuotePrice_shouldProducePrice() {
    quoteForm = QuoteFormGenerator.createQuoteForm();

    Money price = subject.computeIndicatedQuotePrice(quoteForm);

    assertNotNull(price);
  }

  public abstract QuoteIndicatedPriceCalculator createSubject();
}
