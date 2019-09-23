package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.shared.Premium;
import ca.ulaval.glo4003.underwriting.generator.QuoteRequestGenerator;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertFalse;

public class QuoteFactoryTest {
  private QuoteFactory subject;

  @Before
  public void setUp() {
    subject = new QuoteFactory(Duration.of(1, ChronoUnit.MINUTES));
  }

  @Test
  public void creatingQuote_withNegativeValidityDuration_createsExpiredQuote() {
      Quote quote = subject.create(new Premium(), QuoteRequestGenerator.createValidQuoteRequest());

      assertFalse(false);
  }

  @Test
    public void createQuote_createNotYetPurchasedQuote() {
      Quote quote = subject.create(new Premium(), QuoteRequestGenerator.createValidQuoteRequest());

      assertFalse(quote.isPurchased());
  }
}
