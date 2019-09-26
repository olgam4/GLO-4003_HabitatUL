package ca.ulaval.glo4003.underwriting.domain;

import ca.ulaval.glo4003.generator.QuoteRequestGenerator;
import ca.ulaval.glo4003.shared.domain.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class PremiumCalculatorIT {
  private PremiumCalculator subject;
  private QuoteRequest quoteRequest;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void computingQuotePremium_shouldProducePremium() {
    quoteRequest = QuoteRequestGenerator.createValidQuoteRequest();

    Premium premium = subject.computeQuotePremium(quoteRequest);

    assertNotNull(premium);
  }

  public abstract PremiumCalculator createSubject();
}
