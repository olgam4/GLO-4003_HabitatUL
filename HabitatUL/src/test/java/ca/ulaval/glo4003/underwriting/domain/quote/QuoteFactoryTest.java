package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.domain.Date;
import ca.ulaval.glo4003.shared.FixedClockProvider;
import ca.ulaval.glo4003.shared.domain.Premium;
import ca.ulaval.glo4003.underwriting.generator.QuoteRequestGenerator;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class QuoteFactoryTest {
  private QuoteFactory subject;
  private Duration validityPeriod;
  private ClockProvider clockProvider;

  @Before
  public void setUp() {
    validityPeriod = Duration.of(1, ChronoUnit.MINUTES);
    clockProvider = new FixedClockProvider();
    subject = new QuoteFactory(validityPeriod, clockProvider);
  }

  @Test
  public void creatingQuote_shouldProperlyComputeExpirationDate() {
    Quote quote = subject.create(new Premium(), QuoteRequestGenerator.createValidQuoteRequest());

    Date expectedExpirationDate =
        Date.from(LocalDateTime.now(clockProvider.getClock()).plus(validityPeriod));
    assertEquals(expectedExpirationDate, quote.getExpirationDate());
  }

  @Test
  public void creatingQuote_shouldCreateNotYetPurchasedQuote() {
    Quote quote = subject.create(new Premium(), QuoteRequestGenerator.createValidQuoteRequest());

    assertFalse(quote.isPurchased());
  }
}
