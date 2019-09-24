package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.shared.FixedClockProvider;
import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.domain.Date;
import ca.ulaval.glo4003.shared.domain.Premium;
import ca.ulaval.glo4003.generator.QuoteRequestGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuoteFactoryTest {
  private static final Duration VALIDITY_PERIOD = Duration.of(1, ChronoUnit.MINUTES);

  @Mock private QuoteValidityPeriodProvider quoteValidityPeriodProvider;

  private QuoteFactory subject;
  private ClockProvider clockProvider;

  @Before
  public void setUp() {
    when(quoteValidityPeriodProvider.getQuoteValidityPeriod()).thenReturn(VALIDITY_PERIOD);
    clockProvider = new FixedClockProvider();
    subject = new QuoteFactory(quoteValidityPeriodProvider, clockProvider);
  }

  @Test
  public void creatingQuote_shouldProperlyComputeExpirationDate() {
    Quote quote = subject.create(new Premium(), QuoteRequestGenerator.createValidQuoteRequest());

    Date expectedExpirationDate =
        Date.from(LocalDateTime.now(clockProvider.getClock()).plus(VALIDITY_PERIOD));
    assertEquals(expectedExpirationDate, quote.getExpirationDate());
  }

  @Test
  public void creatingQuote_shouldCreateNotYetPurchasedQuote() {
    Quote quote = subject.create(new Premium(), QuoteRequestGenerator.createValidQuoteRequest());

    assertFalse(quote.isPurchased());
  }
}
