package ca.ulaval.glo4003.underwriting.domain.premium;

import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class PremiumCalculatorIT {
  private PremiumCalculator subject;
  private QuoteForm quoteForm;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void computingQuotePremium_shouldProducePremium() {
    quoteForm = QuoteFormGenerator.createValidQuoteForm();

    Premium premium = subject.computeQuotePremium(quoteForm);

    assertNotNull(premium);
  }

  public abstract PremiumCalculator createSubject();
}
